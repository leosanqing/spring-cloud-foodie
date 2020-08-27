package com.leosanqing.test.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.leosanqing.item.ItemApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(classes = ItemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
                        .port(10001)
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

}