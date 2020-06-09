package com.leosanqing.springcloud.demo.rules;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author: rtliu
 * @Date: 2020/6/2 下午4:21
 * @Package: com.leosanqing.springcloud.demo.rules
 * @Description: 自定义负载均衡规则
 * @Version: 1.0
 */
@NoArgsConstructor
public class MyRule extends AbstractLoadBalancerRule implements IRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    /**
     * 选择一个服务器节点返回
     *
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        String uri = request.getServletPath() + "?" + request.getQueryString();


        return route(uri.hashCode(), getLoadBalancer().getAllServers());
    }


    /**
     * 根据 uri 计算 hashcode,
     *
     * @param hashId
     * @param addressList
     * @return
     */
    public Server route(int hashId, List<Server> addressList) {
        TreeMap<Long, Server> map = new TreeMap<>();

        // 防御性编程
        if (CollectionUtils.isEmpty(addressList)) {
            return null;
        }

        addressList.forEach(e -> {
            for (int i = 0; i < 8; i++) {
                // 虚化若干个服务器节点到环上
                Long hash = hash(e.getId() + i);
                map.put(hash, e);
            }
        });


        Long hash = hash(String.valueOf(hashId));
        SortedMap<Long, Server> last = map.tailMap(hash);

        // 当这个大于 map中的任意一个节点，就取第一个，这样就变成首尾相接的环
        if (last.isEmpty()) {
            return map.firstEntry().getValue();
        }

        return last.get(last.firstKey());
    }

    /**
     * 计算 hash值
     *
     * @param key
     * @return
     */
    private Long hash(String key) {
        MessageDigest md5;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);

        md5.update(bytes);
        byte[] digest = md5.digest();

        long hashcode = ((long) (digest[2]) & 0xFF << 16)
                | ((long) (digest[1]) & 0xFF << 8)
                | ((long) (digest[0]));
        return hashcode & 0xffffffffL;
    }
}
