package com.okbank.blockchain.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.okbank.blockchain.common.constants.Constants.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = URI_SCHEME, uriHost = URI_HOST, uriPort = URI_PORT)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class ApiAbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeAll
    void init() {
        // TODO MockUser 저장 등
        System.out.println("mockMvc.toString() = " + mockMvc.toString());
    }

}
