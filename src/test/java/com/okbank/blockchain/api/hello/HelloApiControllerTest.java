package com.okbank.blockchain.api.hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class HelloApiControllerTest {

    @Autowired
    private MockMvc mvc; // 웹 API를 테스트할 때 사용

    @Test
    @DisplayName("hello가_리턴된다")
    @WithMockUser(roles = "USER")
    public void hello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // /hello 주소로 http get 요청을 한다
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증(200, 404, 500등의 상태)
                .andExpect(content().string(hello)); // 응답 본문의 내용을 검증("hello"인지)
    }

    @Test
    @DisplayName("helloDto가_리턴된다")
    @WithMockUser(roles = "USER")
    public void helloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount))); // jsonPath: JSON 응답값을 필드별로 검증할 수 있는 메소드

    }

}