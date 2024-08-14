package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.domain.dtos.UserUpdateUsernameDTO;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.UserRepository;
import com.emailspringproject.emailholder.services.UserService;
import com.emailspringproject.emailholder.utilities.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.emailspringproject.emailholder.constants.Errors.USER_ERR;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private ValidationUtils validationUtils;
    private PasswordEncoder encoder;

    private final UserDetailsService emailHolderDetailsService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           ValidationUtils validationUtils,
                           PasswordEncoder encoder,
                           UserDetailsService emailHolderDetailsService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.encoder = encoder;
        this.emailHolderDetailsService = emailHolderDetailsService;
    }

    @Override
    public Authentication login(String username) {
        UserDetails userDetails = emailHolderDetailsService.loadUserByUsername(username);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    @Override
    public List<String> registerUser(UserRegisterDTO userDTO) {

        List<String> errors = new ArrayList<>();
        userDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userDTO.setLastChangedAt(Timestamp.valueOf(LocalDateTime.now()));

        boolean isValidDTO = validationUtils.isValid(userDTO);
        Optional<User> foundUserByUsername = userRepository.findFirstByUsername(userDTO.getUsername());
        Optional<User> foundUserByMainEmail = userRepository.findFirstByMainEmail(userDTO.getMainEmail());

        if (!isValidDTO) {
            errors.add(USER_ERR.toString());
        }

        if (foundUserByUsername.isPresent()) {
            errors.add(String.format("%s already exist", userDTO.getUsername()));
        }

        if (foundUserByMainEmail.isPresent()) {
            errors.add(String.format("%s already exist", userDTO.getMainEmail()));
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        String rawPassword = userDTO.getPassword();
        userDTO.setPassword(encoder.encode(rawPassword));
        User user = modelMapper.map(userDTO, User.class);

        userRepository.save(user);

//      Successful Register and need a login
        login(user.getUsername());

        return errors;
    }

    @Override
    public List<String> updateUserUsername(UserUpdateUsernameDTO userUpdateUsernameDTO, UserDetails userDetails) {

        List<String> errors = new ArrayList<>();
        Optional<User> firstByUsername = userRepository.findFirstByUsername(userDetails.getUsername());

        User user = null;
        if (firstByUsername.isPresent()) {
            user = firstByUsername.get();
        } else {
            return errors;
        }

        if (!user.getUsername().equals(userUpdateUsernameDTO.getCurrentUsername())) {
            errors.add("Current Username is incorrect");
            return errors;
        }

        boolean isValidDTO = validationUtils.isValid(userUpdateUsernameDTO);
        if (!isValidDTO) {
            errors.add(USER_ERR.toString());
        }

        Optional<User> foundUserByUsername = userRepository.findFirstByUsername(userUpdateUsernameDTO.getUsernameNew());

        if (foundUserByUsername.isPresent()) {
            errors.add("This Username already exist");
            return errors;
        }

        String encodedPass = user.getPassword();
        String rawPass = userUpdateUsernameDTO.getPassword();
        boolean isCorrect = false;
        isCorrect = encodedPass != null && encoder.matches(rawPass, encodedPass);

        if (!isCorrect) {
            errors.add("Incorrect password");
            return errors;
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        user.setLastChangedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setUsername(userUpdateUsernameDTO.getUsernameNew());

        userRepository.save(user);

//      Change is Successful and need a login
        login(user.getUsername());
        return errors;
    }

    @Override
    public User getCurrentUser(UserDetails userDetails) {
        Optional<User> optUser = userRepository.findFirstByUsername(userDetails.getUsername());
        User user = optUser.get();
        return user;
    }

}
