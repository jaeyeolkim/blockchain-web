package com.okbank.blockchain.api.wallet;

import com.okbank.blockchain.api.ApiAbstractTest;
import com.okbank.blockchain.common.util.LocalDateUtil;
import com.okbank.blockchain.domain.user.User;
import com.okbank.blockchain.domain.user.UserMock;
import com.okbank.blockchain.domain.user.UserRepository;
import com.okbank.blockchain.domain.wallet.Wallet;
import com.okbank.blockchain.domain.wallet.WalletMock;
import com.okbank.blockchain.domain.wallet.WalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static com.okbank.blockchain.common.constants.Constants.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WalletApiControllerTest extends ApiAbstractTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanup() {
        walletRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    @DisplayName("월렛 목록 페이징 조회")
    void findWallets() throws Exception {
        String userName = "kim";
        User owner = UserMock.buildUser(userName);
        userRepository.save(owner);

        String walletName = "my wallet";
        int walletMaxSize = 20;
        for (int i = 0; i < walletMaxSize; i++) {
            Wallet wallet = WalletMock.buildWallet(owner, walletName + (i+1));
            walletRepository.save(wallet);
        }

        this.mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get(API_URI_PREFIX + "/wallets")
                                .header(HEADER_AUTHORIZATION, TOKEN_PREFIX + "TODO getToken()")
                                .queryParam("pageNum", "1")
                                .queryParam("pageSize", "10")
                                .queryParam("fromDate", LocalDateUtil.nowMinusDaysOfPattern(1L))
                                .queryParam("toDate", LocalDateUtil.nowPlusDaysOfPattern(1L))
                                .queryParam("ownerName", userName)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("{class-name}/{method-name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName(HEADER_AUTHORIZATION).description(TOKEN_PREFIX + "ACCESS-TOKEN")
                                ),
                                requestParameters(
                                        parameterWithName("pageNum").description("페이지 번호"),
                                        parameterWithName("pageSize").description("페이지당 데이터 건수"),
                                        parameterWithName("fromDate").description("조회 시작일"),
                                        parameterWithName("toDate").description("조회 종료일"),
                                        parameterWithName("ownerName").description("소유자명")
                                ),
                                responseFields(
                                        fieldWithPath("code").description("처리 결과"),
                                        fieldWithPath("message").description("처리 결과 메시지"),
                                        fieldWithPath("pageNum").description("페이지 번호"),
                                        fieldWithPath("pageSize").description("페이지 사이즈"),
                                        fieldWithPath("totalCount").description("데이터 총 건수"),
                                        fieldWithPath("payload.[].walletUid").description("월렛 UID"),
                                        fieldWithPath("payload.[].walletName").description("월렛명"),
                                        fieldWithPath("payload.[].ownerName").description("사용자명"),
                                        fieldWithPath("payload.[].modifiedDate").description("최종수정일")
                                )
                        )
                );
    }

    @Test
    @DisplayName("월렛 목록 페이징 조회 Exception")
    void findWalletsException() throws Exception {
        String userName = "kim";

        this.mockMvc.perform(
                get(API_URI_PREFIX + "/wallets")
                        .queryParam("pageNum", "1")
                        .queryParam("pageSize", "10")
                        .queryParam("fromDate", "")
                        .queryParam("toDate", LocalDateUtil.nowPlusDaysOfPattern(1L))
                        .queryParam("ownerName", userName)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.validation[0].field").value("fromDate"))
                .andExpect(jsonPath("$.validation[0].message").value("조회 시작일은 필수입니다."));
    }

    @Test
    @DisplayName("나의 월렛 목록 조회")
    void findMyWallets() throws Exception {
        // given
        String userName = "kim";
        String walletName = "my wallet";
        User owner = UserMock.buildUser(userName);
        Wallet wallet = WalletMock.buildWallet(owner, walletName);

        userRepository.save(owner);
        walletRepository.save(wallet);

        this.mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get(API_URI_PREFIX + "/my/wallets/{userUid}", owner.getUserUid())
                                .header(HEADER_AUTHORIZATION, TOKEN_PREFIX + "TODO getToken()")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("{class-name}/{method-name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName(HEADER_AUTHORIZATION).description(TOKEN_PREFIX + "ACCESS-TOKEN")
                                ),
                                pathParameters(
                                        parameterWithName("userUid").description("사용자 UID")
                                ),
                                responseFields(
                                        fieldWithPath("code").description("처리 결과"),
                                        fieldWithPath("message").description("처리 결과 메시지"),
                                        fieldWithPath("payload.[].walletUid").description("월렛 UID"),
                                        fieldWithPath("payload.[].walletName").description("월렛명"),
                                        fieldWithPath("payload.[].ownerName").description("사용자명"),
                                        fieldWithPath("payload.[].modifiedDate").description("최종수정일")
                                )
                        )
                );
    }
}