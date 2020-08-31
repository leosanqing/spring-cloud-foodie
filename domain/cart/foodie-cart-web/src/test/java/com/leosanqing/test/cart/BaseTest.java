package com.leosanqing.test.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.leosanqing.cart.CartApplication;
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


@SpringBootTest(classes = CartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseTest {

    public static WireMockServer wireMockServer;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    public ObjectMapper objectMapper;

    public MockMvc mockMvc;

    public static final String ITEM_ID = "bingan-1001";
    public static final String ITEM_IMG_URL = "http://122.152.205.72:88/foodie/cake-1001/img1.png";
    public static final String ITEM_NAME = "【天天吃货】彩虹马卡龙 下午茶 美眉最爱";

    public static final String USER_ID = "19120779W7TK6800";

    public static final String SHOP_CART = "shopcart";


    @BeforeAll
    public static void setup() throws Exception {
        wireMockServer = new WireMockServer(
                options()
                        .port(10004)
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