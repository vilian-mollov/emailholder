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
    void testGetEmailById() {
//        TODO
//        when(mockEmailRepository.findById(expectedId)).thenReturn(Optional.of(expectedEmail));
//
//        EmailDTO emailDTO = emailService.getEmailById(expectedId);
//
//        Assertions.assertEquals(expectedEmail.getId(), emailDTO.getId(), "Get Email by id doesnt return the right email.");
    }


    @Test
    void testGetEmailByIdNull() {
        EmailDTO emailDTO = emailService.getEmailById(0L);

        Assertions.assertNull(emailDTO);
    }

    @Test
    void testCreateEmailInvalidDTO() {
//        when(mockValidationUtils.isValid(emailDTO)).thenReturn(false);

//        Email actualEmail = emailService.createEmail(emailDTO);

//        Assertions.assertNull(actualEmail);
    }

    @Test
    void testCreateEmail() {
//        when(mockValidationUtils.isValid(emailDTO)).thenReturn(true);
//        when(mockEmailRepository.save(expectedEmail)).thenReturn(expectedEmail);
//        when(mockUserService.getCurrentUser()).thenReturn(expectedUser);
//        when(mockModelMapper.map(emailDTO, Email.class)).thenReturn(expectedEmail);
//
//        Email actualEmail = emailService.createEmail(emailDTO);
//
//        Assertions.assertEquals(expectedEmail.getId(), actualEmail.getId());
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
    void testAddSiteToEmailNotPresented() {
//        TODO
//        when(mockEmailRepository.findById(expectedId)).thenReturn(Optional.empty());
//        when(mockSiteRepository.findById(expectedId)).thenReturn(Optional.empty());
//
//        SiteExportDTO siteExportDTO = new SiteExportDTO();
//        siteExportDTO.setId(expectedId);
//
//        ResponseEntity<Email> actualResponseEntity = emailService.addSiteToEmail(expectedId, siteExportDTO);
//        ResponseEntity<Email> expectedResponseEntity = ResponseEntity.notFound().build();
//        Assertions.assertEquals(expectedResponseEntity.toString(), actualResponseEntity.toString());
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
//        TODO
//        when(mockUserService.getCurrentUser()).thenReturn(expectedUser);
//        when(mockEmailRepository.findAllByUser(expectedUser)).thenReturn(List.of(expectedEmail));
//
//        List<EmailDTO> allEmailDTOsByUser = emailService.getAllEmailsByUser();
//        List<Email> expectedEmailsOfUser = List.of(expectedEmail);
//
//        Assertions.assertEquals(expectedEmailsOfUser.get(0).getEmailAddress(), allEmailDTOsByUser.get(0).getEmailAddress());
    }


    @Test
    void testDeleteEmail() {
//        TODO
//        emailService.deleteEmail(expectedId);
    }


    @Test
    void testUpdateEmail() {
//        emailService.updateEmail(expectedEmail);
    }


}
