package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    private EmailServiceImpl emailService;

    @Mock
    private EmailRepository mockEmailRepository;

    @Mock
    private SiteRepository mockSiteRepository;

    private static Email expectedEmail;
    private static Site expectedSite;

    private static final Long expectedId = 12L;
    private static final String expectedAddress = "https:mock.site21";
    private static final String expectedDomainName = "mock";

    @BeforeEach
    void setUp(){
        emailService = new EmailServiceImpl(mockEmailRepository, mockSiteRepository);
        expectedEmail = new Email(expectedAddress, "Test Email Expected");
        expectedEmail.setId(expectedId);

        expectedSite = new Site(expectedAddress, expectedDomainName);
    }


    @Test
    void testGetEmailById(){
        when(mockEmailRepository.findById(expectedId)).thenReturn(Optional.of(expectedEmail));

        Email actualEmail = emailService.getEmailById(expectedId);

        Assertions.assertEquals(expectedEmail.getId(), actualEmail.getId(),"Get Email by id doesnt return the right email.");
    }


    @Test
    void testGetEmailByIdNull(){
        Email actualEmail = emailService.getEmailById(0L);

        Assertions.assertEquals(null, actualEmail);
    }

    @Test
    void testCreateEmail(){

        when(mockEmailRepository.save(expectedEmail)).thenReturn(expectedEmail);

        Email actualEmail = emailService.createEmail(expectedEmail);

        Assertions.assertEquals(expectedEmail.getId(), actualEmail.getId());
    }

    @Test
    void testCreateEmailWithSiteId(){

        when(mockEmailRepository.save(expectedEmail)).thenReturn(expectedEmail);
        when(mockSiteRepository.findById(expectedId)).thenReturn(Optional.of(expectedSite));

        Email actualEmail = emailService.createEmail(12L, expectedEmail);

        Assertions.assertEquals(expectedEmail.getId(), actualEmail.getId());
    }


    @Test
    void testAddSiteToEmail(){

    }

}
