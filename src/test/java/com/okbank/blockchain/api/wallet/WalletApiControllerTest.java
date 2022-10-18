package com.okbank.blockchain.api.wallet;

import com.okbank.blockchain.api.ApiAbstractTest;
import com.okbank.blockchain.domain.user.User;
import com.okbank.blockchain.domain.user.UserMock;
import com.okbank.blockchain.domain.user.UserRepository;
import com.okbank.blockchain.domain.wallet.Wallet;
import com.okbank.blockchain.domain.wallet.WalletMock;
import com.okbank.blockchain.domain.wallet.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static com.okbank.blockchain.common.constants.Constants.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WalletApiControllerTest extends ApiAbstractTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("월렛 목록 조회 API")
    void findWallets() throws Exception {
        this.mockMvc.perform(get(API_URI_PREFIX + "/wallets")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("월렛 목록 조회 API RestDocs")
    void findWalletsRestDoc() throws Exception {
        // given
        String userName = "kim";
        String walletName = "my wallet";
        User owner = UserMock.buildUser(userName);
        Wallet wallet = WalletMock.buildWallet(owner, walletName);

        userRepository.save(owner);
        walletRepository.save(wallet);

        this.mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get(API_URI_PREFIX + "/wallets")
                                .header(HEADER_AUTHORIZATION, TOKEN_PREFIX + "TODO getToken()")
                                .queryParam("pageNum", "1")
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
                                        parameterWithName("pageNum").description("페이지 번호")
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