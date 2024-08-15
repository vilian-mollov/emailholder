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
public class EmailControllerTestIntegration {


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
    public void test_getAllEmailsOfUser() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/emails").with(csrf())).
                andExpect(status().isOk());
    }

    @Test
    public void test_getCreateEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/emails/create")
                        .param("emailAddress", "testEmail@testting.com")
                        .param("description", "For testing")
                        .with(csrf())).
                andExpect(status().isOk());
    }


    @Test
    public void test_createEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/emails/create")
                        .param("emailAddress", "testEmail@testting.com")
                        .param("description", "For testing")
                        .with(csrf())).
                andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/emails"));
    }

    @Test
    public void test_createEmail_not_valid_address() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/emails/create")
                        .param("emailAddress", "notValid")
                        .param("description", "For testing")
                        .with(csrf())).
                andExpect(status().isOk());
    }


    @Test
    public void test_createEmail_already_existing_address() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/emails/create")
                        .param("emailAddress", "immortals@gmail.com")
                        .param("description", "For testing")
                        .with(csrf())).
                andExpect(status().isOk());
    }


    @Test
    public void test_getAddSiteToEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/emails/add/site/1")
                        .param("address", "https:/test.com")
                        .param("domainName", "Test")
                        .with(csrf())).
                andExpect(status().isOk());
    }

    @Test
    public void test_addSiteToEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/emails/add/sites/3")
                        .param("id", "4")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/emails"));
    }

    @Test
    public void test_add_already_assigned_SiteToEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/emails/add/sites/1")
                        .param("id", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/emails"));
    }


    @Test
    public void test_removeSiteFromEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/emails/1/sites/3")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sites/email/1"));
    }

    @Test
    public void test_deleteEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/emails/delete/3")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/emails"));
    }


    @Test
    public void test_editEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/emails/edit/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    public void test_updateEmail() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/emails/update/1")
                        .param("emailAddress","newemailaddress@gmail.com")
                        .param("description","Test change of email address correct")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/emails"));
    }


    @Test
    public void test_updateEmail_empty_email() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/emails/update/1")
                        .param("emailAddress","NotValid")
                        .param("description","empty email")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateEmail_already_existing_email() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/emails/update/1")
                        .param("emailAddress","alokard@abv.bg")
                        .param("description","empty email")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}
