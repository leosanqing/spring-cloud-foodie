package com.leosanqing.index;

import com.cetcxl.xlpay.admin.service.UserDetailServiceImpl;
import com.cetcxl.xlpay.common.entity.model.Company;
import com.cetcxl.xlpay.common.entity.model.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(classes = IndexApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseTest {
    public static final String S_ID_1 = "1";
    public static final String S_TEST = "test";
    public static final String S_TEMP = "temp";
    public static final String S_VERIFY_CODE = "123456";
    public static final String S_CETCXL = "cetcxl";
    public static final String S_SOCIAL_CREDIT_CODE = "1234567890ABCDEFGH";
    public static final String S_SHOP = "shop";
    public static final String S_PHONE = "19999999999";

    public static WireMockServer wireMockServer;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    public ObjectMapper objectMapper;

    public MockMvc mockMvc;

    @BeforeAll
    public static void setup() throws Exception {
        wireMockServer = new WireMockServer(
                options()
                        .port(8089)
        );
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
    }

    @AfterAll
    public static void after() {
        wireMockServer.stop();
    }

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    @AfterEach
    public void tearDown() {
    }

    public void setAuthentication(Company company) {
        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                new UserDetailServiceImpl.UserInfo(
                                        S_ID_1,
                                        S_TEMP,
                                        AuthorityUtils.createAuthorityList("All"),
                                        company
                                ),
                                null,
                                AuthorityUtils.createAuthorityList("All")));
    }

    public void setAuthentication(Store store) {
        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                new UserDetailServiceImpl.UserInfo(
                                        S_ID_1,
                                        S_TEMP,
                                        AuthorityUtils.createAuthorityList("All"),
                                        store
                                ),
                                null,
                                AuthorityUtils.createAuthorityList("All")));
    }
}