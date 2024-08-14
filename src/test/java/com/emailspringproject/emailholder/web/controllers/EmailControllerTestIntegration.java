package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTestIntegration {


    @Autowired
    private MockMvc mockMvc;

    @Value("${PASSWORD}")
    private String pass;



    @BeforeEach
    public void setup() throws Exception {


    }

    @Test
    public void test_getAllEmailsOfUser() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("username","test")
                .param("password", pass));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/emails")).
                andExpect(status().isOk());

    }

}
