package com.emailspringproject.emailholder.web.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTestIntegration {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_getAllEmailsOfUser() {
//        mockMvc.perform(
//                        delete("/offer/{uuid}", offerEntity.getUuid())
//                                .with(csrf())
//                ).andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrlPattern("**/users/login"));
    }

}
