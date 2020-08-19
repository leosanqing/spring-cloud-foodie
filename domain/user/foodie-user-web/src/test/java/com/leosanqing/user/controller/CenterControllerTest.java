package com.leosanqing.user.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.collect.Lists;
import com.leosanqing.user.BaseTest;
import com.leosanqing.user.controller.center.CenterController;
import com.leosanqing.user.service.UserService;
import com.leosanqing.user.service.center.CenterUserService;
import com.leosanqing.user.service.impl.center.CenterUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

class CenterControllerTest extends BaseTest {

    @Autowired
    UserService userService;

    @Autowired
    CenterUserServiceImpl centerUserService;
//    @Autowired
//    ChecksService checksService;
//    @Autowired
//    ChecksRecordService checksRecordService;
//    @Autowired
//    DealService dealService;
//
//    @Autowired
//    WalletCreditService walletCreditService;
//
//    @Override
//    @BeforeEach
//    public void setUp() throws Exception {
//        super.setUp();
//        setAuthentication(
//                Company.builder()
//                        .id(1)
//                        .build()
//        );
//
//        WireMock.stubFor(
//                WireMock
//                        .post(
//                                WireMock
//                                        .urlPathEqualTo("/cc_manager/fabric/invoke")
//                        )
//                        .willReturn(
//                                WireMock
//                                        .okJson(
//                                                objectMapper
//                                                        .writeValueAsString(
//                                                                ChainCodeService.Result.builder()
//                                                                        .code(0)
//                                                                        .build()
//                                                        )
//                                        )
//                        )
//
//        );
//    }


    @Test
    void queryUserInfo() {

    }

    @Test
    void addCheck() throws Exception {


        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/center/userInfo")
                                .param("userId", "19120779W7TK6800")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

//    @Test
//    void auditCheck() throws Exception {
//        ChecksController.AuditCheckReq req = ChecksController.AuditCheckReq.builder()
//                .approvalOrReject(Boolean.TRUE)
//                .build();
//
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .patch("/companys/{companyId}/deals/checks/{checkBatch}", 1, 2)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(req))
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        Checks checks = checksService.getById(2);
//        Assert.assertTrue(checks.getStatus() == APPROVAL);
//
//        ChecksRecord checksRecord = checksRecordService.lambdaQuery()
//                .eq(ChecksRecord::getCheckBatch, checks.getBatch())
//                .eq(ChecksRecord::getAction, APPROVAL)
//                .one();
//        Assert.assertNotNull(checksRecord);
//    }
//
//    @Test
//    void confirmCheck() throws Exception {
//        WalletCredit oldWalletCredit = walletCreditService.getById(2);
//        Assert.assertTrue(oldWalletCredit.getCreditQuota().compareTo(oldWalletCredit.getCreditBalance()) > 0);
//
//        ChecksController.AuditCheckReq req = ChecksController.AuditCheckReq.builder()
//                .confirmOrDeny(Boolean.TRUE)
//                .build();
//
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .patch("/stores/{storeId}/deals/checks/{checkBatch}", 1, 3)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(req))
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        Checks checks = checksService.getById(3);
//        Assert.assertTrue(checks.getStatus() == CONFIRM);
//
//        WalletCredit nowWalletCredit = walletCreditService.getById(2);
//        Assert.assertTrue(nowWalletCredit.getCreditQuota().compareTo(nowWalletCredit.getCreditBalance()) == 0);
//
//        ChecksRecord checksRecord = checksRecordService.lambdaQuery()
//                .eq(ChecksRecord::getCheckBatch, checks.getBatch())
//                .eq(ChecksRecord::getAction, CONFIRM)
//                .one();
//        Assert.assertNotNull(checksRecord);
//        Assert.assertTrue(checksRecord.getOperator().equals(1));
//    }
//
//    @Test
//    void listCompanyCheck() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .get("/companys/{companyId}/checks", 1)
//                                .param("companyId", "1")
//                                .param(PARAM_PAGE_NO, "1")
//                                .param(PARAM_PAGE_SIZE, "5")
//                                .accept(MediaType.APPLICATION_JSON_UTF8)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(
//                        MockMvcResultMatchers
//                                .jsonPath("$.data.total")
//                                .isNumber()
//                )
//                .andReturn();
//
//        JsonNode jsonNode = objectMapper.readTree(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
//        List<ChecksMapper.CheckDTO> checkDTOS = objectMapper
//                .readValue(
//                        jsonNode.get("data").get("records").toString(),
//                        new TypeReference<List<ChecksMapper.CheckDTO>>() {
//                        }
//                );
//
//        Assertions.assertTrue(checkDTOS.size() > 0);
//        Assertions.assertEquals("17360126771", checkDTOS.get(1).getApprovalPhone());
//
//    }
//
//    @Test
//    void listCompanyCheck_withStatues() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .get("/companys/{companyId}/checks", 1)
//                                .param("companyId", "1")
//                                .param("statues", REJECT.name())
//                                .param("statues", APPROVAL.name())
//                                .param(PARAM_PAGE_NO, "1")
//                                .param(PARAM_PAGE_SIZE, "5")
//                                .accept(MediaType.APPLICATION_JSON_UTF8)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        JsonNode jsonNode = objectMapper.readTree(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
//        List<ChecksMapper.CheckDTO> checkDTOS = objectMapper
//                .readValue(
//                        jsonNode.get("data").get("records").toString(),
//                        new TypeReference<List<ChecksMapper.CheckDTO>>() {
//                        }
//                );
//
//        Assertions.assertFalse(
//                checkDTOS.stream()
//                        .filter(checkDTO -> checkDTO.getStatus() == APPLY)
//                        .findAny()
//                        .isPresent()
//        );
//
//    }
//
//    @Test
//    void listCompanyCheck_export_withStatues() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .get("/company/{companyId}/deal/checks/export", 1)
//                                .param("companyId", "1")
//                                .param("statues", REJECT.name())
//                                .param("statues", APPROVAL.name())
//                                .param(PARAM_PAGE_NO, "1")
//                                .param(PARAM_PAGE_SIZE, "5")
//                                .accept(MediaType.APPLICATION_JSON_UTF8)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//    }
//
//    @Test
//    void listCompanyCheck_withCompanyName() throws Exception {
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .get("/companys/{companyId}/checks", 1)
//                                .param("companyId", "1")
//                                .param("storeName", "abcdef")
//                                .param(PARAM_PAGE_NO, "1")
//                                .param(PARAM_PAGE_SIZE, "5")
//                                .accept(MediaType.APPLICATION_JSON_UTF8)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(
//                        MockMvcResultMatchers
//                                .jsonPath("$.data.total")
//                                .value(0)
//                );
//    }
//
//    @Test
//    void listStoreCheck() throws Exception {
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .get("/stores/{storeId}/checks", 1)
//                                .param(PARAM_PAGE_NO, "1")
//                                .param(PARAM_PAGE_SIZE, "5")
//                                .param("approvalTimeBegin", "2020-06-07 00:00:00")
//                                .param("approvalTimeEnd", "2020-07-08 00:00:00")
//                                .accept(MediaType.APPLICATION_JSON_UTF8)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(
//                        MockMvcResultMatchers
//                                .jsonPath("$.data.total")
//                                .value(Matchers.greaterThan(0))
//                );
//
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .get("/stores/{storeId}/checks", 1)
//                                .param("companyName", "abcdef")
//                                .param(PARAM_PAGE_NO, "1")
//                                .param(PARAM_PAGE_SIZE, "5")
//                                .accept(MediaType.APPLICATION_JSON_UTF8)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(
//                        MockMvcResultMatchers
//                                .jsonPath("$.data.total")
//                                .value(0)
//                );
//    }
//
//    @Test
//    void getCheckDetail() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(
//                        MockMvcRequestBuilders
//                                .get("/checks/{checkId}", 3)
//                                .accept(MediaType.APPLICATION_JSON_UTF8)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(
//                        MockMvcResultMatchers
//                                .jsonPath("$.data.status")
//                                .value(APPROVAL.name())
//                )
//                .andReturn();
//
//        ChecksVO checksVO = objectMapper
//                .readValue(
//                        mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
//                        new TypeReference<ResBody<ChecksVO>>() {
//                        }
//                )
//                .getData();
//
//        Assertions.assertFalse(
//                checksVO
//                        .getChecksRecords()
//                        .stream()
//                        .filter(checkRecordDTO -> StringUtils.isBlank(checkRecordDTO.getPhone()))
//                        .findAny()
//                        .isPresent()
//        );
//
//        Assertions.assertTrue(
//                checksVO.getInfos()
//                        .stream()
//                        .filter(infoRecord -> APPROVAL == infoRecord.getStatus())
//                        .findFirst()
//                        .get()
//                        .getInfo()
//                        .equals("approval")
//        );
//        Assertions.assertEquals(
//                checksVO.getAttachmentVos().size(), 3
//        );
//    }
}

