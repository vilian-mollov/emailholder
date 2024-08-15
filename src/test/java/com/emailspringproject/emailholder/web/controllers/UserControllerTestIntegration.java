package com.emailspringproject.emailholder.web.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestIntegration {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${PASSWORD}")
    private String pass;

    @BeforeEach
    public void setup() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("test");

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }


    @Test
    public void test_loginPage() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/login")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_login_onFailure() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/login-error")
                        .param("username", "test")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getRegister() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/register")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_registerUser() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("username", "testIntegration_69")
                        .param("mainEmail", "testIntegration69@testing.com")
                        .param("password", pass)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void test_registerUser_wrong_values_DTO() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("username", "")
                        .param("mainEmail", "thisisnotemailtesting.com")
                        .param("password", "")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_registerUser_already_existing_username() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("username", "test")
                        .param("mainEmail", "testIntegration008@testing.com")
                        .param("password", pass)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_registerUser_already_existing_email() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("username", "testIntegration_6996")
                        .param("mainEmail", "immortals@gmail.com")
                        .param("password", pass)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getProfile() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/profile")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getUpdateUser() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/update/username")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateUserUsername() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/update/username")
                        .param("currentUsername", "test")
                        .param("usernameNew", "Richard_008")
                        .param("password", pass)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profile"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/update/username")
                        .param("currentUsername", "Richard_008")
                        .param("usernameNew", "test")
                        .param("password", pass)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profile"));
    }

    @Test
    public void test_updateUserUsername_not_valid_DTO() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/update/username")
                        .param("currentUsername", "")
                        .param("usernameNew", "")
                        .param("password", "")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateUserUsername_already_existing_user() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/update/username")
                        .param("currentUsername", "test")
                        .param("usernameNew", "lethimcook_24")
                        .param("password", pass)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateUserUsername_trying_to_change_other_username() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/update/username")
                        .param("currentUsername", "daniel2489")
                        .param("usernameNew", "danie_008")
                        .param("password", pass)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
