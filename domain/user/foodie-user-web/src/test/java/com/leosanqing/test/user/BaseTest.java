package com.leosanqing.test.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.leosanqing.user.UserApplication;
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


@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseTest {

    public static WireMockServer wireMockServer;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    public ObjectMapper objectMapper;

    public MockMvc mockMvc;

    public final static String USER_ID = "19120779W7TK6800";
    public final static String USER_NAME = "leosanqing";
    public final static String PASSWORD = "123456";


    @BeforeAll
    public static void setup() throws Exception {
        wireMockServer = new WireMockServer(
                options()
                        .port(10002)
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