package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.EmailDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.UserService;
import com.emailspringproject.emailholder.utilities.ValidationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    private EmailServiceImpl emailService;

    @Mock
    private EmailRepository mockEmailRepository;

    @Mock
    private SiteRepository mockSiteRepository;

    @Mock
    private UserService mockUserService;
    @Mock
    private ValidationUtils mockValidationUtils;
    @Mock
    private ModelMapper mockModelMapper;

    private static Email expectedEmail;
    private static Site expectedSite;
    private static User expectedUser;
    private static EmailDTO emailDTO;

    private static final Long expectedId = 12L;
    private static final String expectedAddress = "https:mock.site21";
    private static final String expectedEmailAddress = "mock@mocking.com";
    private static final String expectedDomainName = "mock";
    private static final String expectedUserName = "Tester";
    private static final String expectedDescription = "Using this mock";

    @BeforeEach
    void setUp() {
//      User
        expectedUser = new User();
        expectedUser.setUsername(expectedUserName);
        expectedUser.setId(expectedId);
        expectedUser.addEmail(expectedEmail);

//      EmailDTO
        emailDTO = new EmailDTO();
        emailDTO.setEmailAddress(expectedEmailAddress);
        emailDTO.setDescription(expectedDescription);

//      Site
        expectedSite = new Site(expectedAddress, expectedDomainName, expectedUser, new HashSet<>(), new HashSet<>());
        expectedSite.setId(expectedId);

//      Email
        emailService = new EmailServiceImpl(mockEmailRepository, mockSiteRepository, mockUserService, mockValidationUtils, mockModelMapper);
        expectedEmail = new Email(expectedAddress, "Test Email Expected");
        expectedEmail.setId(expectedId);
        expectedEmail.addSite(expectedSite);
    }


    @Test
    void testGetEmailByIdNull() {
        EmailDTO emailDTO = emailService.getEmailById(0L);

        Assertions.assertNull(emailDTO);
    }


    @Test
    void testAddSiteToEmail() {
        when(mockEmailRepository.findById(expectedId)).thenReturn(Optional.of(expectedEmail));
        when(mockSiteRepository.findById(expectedId)).thenReturn(Optional.of(expectedSite));
        when(mockEmailRepository.save(expectedEmail)).thenReturn(expectedEmail);

        SiteExportDTO siteExportDTO = new SiteExportDTO();
        siteExportDTO.setId(expectedId);

        ResponseEntity<Email> actualResponseEntity = emailService.addSiteToEmail(expectedId, siteExportDTO);
        ResponseEntity<Email> expectedResponseEntity = ResponseEntity.ok(expectedEmail);
        Assertions.assertEquals(expectedResponseEntity.toString(), actualResponseEntity.toString());
    }


    @Test
    void testRemoveSiteFromEmail() {
        when(mockEmailRepository.findById(expectedId)).thenReturn(Optional.of(expectedEmail));
        when(mockSiteRepository.findById(expectedId)).thenReturn(Optional.of(expectedSite));
        when(mockEmailRepository.save(expectedEmail)).thenReturn(expectedEmail);


        ResponseEntity<Email> actualResponseEntity = emailService.removeSiteFromEmail(expectedId, expectedId);
        ResponseEntity<Email> expectedResponseEntity = ResponseEntity.ok(expectedEmail);

        Assertions.assertEquals(expectedResponseEntity.toString(), actualResponseEntity.toString());
    }


    @Test
    void testRemoveSiteFromEmailNotPresented() {
        when(mockEmailRepository.findById(expectedId)).thenReturn(Optional.empty());
        when(mockSiteRepository.findById(expectedId)).thenReturn(Optional.empty());


        ResponseEntity<Email> actualResponseEntity = emailService.removeSiteFromEmail(expectedId, expectedId);
        ResponseEntity<Email> expectedResponseEntity = ResponseEntity.notFound().build();

        Assertions.assertEquals(expectedResponseEntity.toString(), actualResponseEntity.toString());
    }

    @Test
    void testGetAllEmailsByUser() {
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
        List<EmailDTO> emailsDTOs = emailService.getAllEmailsByUser(userDetails);
    }

    @Test
    void testCreateEmail() {
    }

    @Test
    void testDeleteEmail() {

    }


    @Test
    void testUpdateEmail() {

    }


}
