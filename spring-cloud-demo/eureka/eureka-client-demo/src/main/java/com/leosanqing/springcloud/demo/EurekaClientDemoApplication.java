package com.leosanqing.springcloud.demo;

import com.leosanqing.springcloud.demo.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.*;

/**
 * @Author: rtliu
 * @Date: 2020/5/15 上午10:17
 * @Package: com.leosanqing.springcloud.demo
 * @Description: Eureka-Client
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientDemoApplication {

    @Data
    @AllArgsConstructor
    public static class User {
        private int no;
        private String name;
    }

    public static void main(String[] args) {


        test();
        TreeMap<User, User> treeMap = new TreeMap<>(Comparator.comparingInt(User::getNo));


        User user1 = new User(1, "1");
        User user2 = new User(2, "2");
        User user22 = new User(2, "22");
        User user3 = new User(3, "3");
        User user4 = new User(4, "4");
        User user5 = new User(5, "5");
        User user6 = new User(6, "6");
        User user7 = new User(7, "7");
        User user8 = new User(8, "8");
        Map<User, User> map = new HashMap<>();
        map.put(user1, user1);

        TreeMap<User, User> map1 = new TreeMap<>(Comparator.comparingInt(User::getNo));
        map1.putAll(map);
        System.out.println(map1);
        treeMap.put(user2, user2);
        treeMap.put(user22, user22);
        treeMap.put(user3, user3);
        treeMap.put(user4, user4);
        treeMap.put(user5, user5);
        treeMap.put(user6, user6);
        treeMap.put(user7, user7);
        treeMap.put(user8, user8);
        treeMap.put(user1, user1);

        System.out.println(treeMap.descendingMap());
        System.out.println(treeMap.firstEntry());
        System.out.println(treeMap.higherEntry(user1));
        System.out.println(treeMap.higherEntry(user2));
        System.out.println(treeMap.floorEntry(user3));
        System.out.println(treeMap.floorEntry(user3));


        new SpringApplicationBuilder(EurekaClientDemoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }


    private static void test() {
        Map<Integer, String> treeMap = new TreeMap<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int nextInt = random.nextInt(2000);
            if (treeMap.containsKey(nextInt)) {
                continue;
            }
            treeMap.put(nextInt, String.valueOf(nextInt));
        }

        for (int i = 0; i < 100; i++) {
            int nextInt = random.nextInt(100);

            treeMap.remove(nextInt);
        }
    }

}
