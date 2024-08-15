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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTestIntegration {

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
    public void test_getAllCommentsForSite() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/comments/site/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_addCommentForSite() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/comments/site/1")
                        .param("comment", "Valid comment")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/site/1"));
    }
}
