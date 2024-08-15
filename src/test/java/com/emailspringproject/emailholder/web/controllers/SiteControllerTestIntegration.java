package com.emailspringproject.emailholder.web.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SiteControllerTestIntegration {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void setup() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("test");

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void test_getAllSites() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sites/all")
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    public void test_getAllSitesForUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sites/user")
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    public void test_getCreateSitePage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sites/create")
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    public void test_createSite() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/sites/create")
                        .param("address" , "https:/testingintegrationtest.com")
                        .param("domainName", "TestingIntegratioTest")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void test_createSite_blank_site_address() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/sites/create")
                        .param("address" , "")
                        .param("domainName", "")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_createSite_not_valid_site() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/sites/create")
                        .param("address" , "what is this/testingintegrationtest.com")
                        .param("domainName", "TestingIntegratioTest")
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    public void test_getSiteByEmail() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sites/email/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
