package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.*;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.UserRepository;
import com.emailspringproject.emailholder.services.UserService;
import com.emailspringproject.emailholder.utilities.CurrentUser;
import com.emailspringproject.emailholder.utilities.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.emailspringproject.emailholder.constants.Errors.USER_ERR;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private ValidationUtils validationUtils;
    private PasswordEncoder encoder;
    private CurrentUser currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           ValidationUtils validationUtils,
                           PasswordEncoder encoder,
                           CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.encoder = encoder;
        this.currentUser = currentUser;
    }

    @Override
    public Boolean loginUser(UserLoginDTO userLoginDTO) {

        User persistedUser = userRepository.findFirstByUsername(userLoginDTO.getUsername()).orElse(null);

        boolean isCorrect = false;

        if (persistedUser != null) {
            String encodedPassword = persistedUser.getPassword();
            String rawPassword = userLoginDTO.getPassword();

            isCorrect = encodedPassword != null && encoder.matches(rawPassword, encodedPassword);

            if (isCorrect) {
                currentUser.setLogged(true);
                currentUser.setUsername(userLoginDTO.getUsername());
            } else {
                currentUser.logout();
            }
        }

        return isCorrect;
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

        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User user = modelMapper.map(userDTO, User.class);

        userRepository.save(user);
        return errors;
    }

    @Override
    public List<String> updateUser(UserUpdateDTO userUpdateDTO) {

        List<String> errors = new ArrayList<>();
        Optional<User> firstByUsername = userRepository.findFirstByUsername(currentUser.getUsername());
        User user = null;
        // todo validate if this is the user
        if (firstByUsername.isPresent()) {
            user = firstByUsername.get();
        }else {
            return errors;
        }
//todo try to refactor code
        boolean isValidDTO = validationUtils.isValid(userUpdateDTO);
        Optional<User> foundUserByUsername = userRepository.findFirstByUsername(userUpdateDTO.getUsername());
        Optional<User> foundUserByMainEmail = userRepository.findFirstByMainEmail(userUpdateDTO.getMainEmail());

        if (!isValidDTO) {
            errors.add(USER_ERR.toString());
        }

        if (!userUpdateDTO.getUsername().equals(user.getUsername())) {
            if (foundUserByUsername.isPresent()) {
                errors.add(String.format("%s already exist", userUpdateDTO.getUsername()));
            }
        }
        if (!userUpdateDTO.getMainEmail().equals(user.getMainEmail())) {
            if (foundUserByMainEmail.isPresent()) {
                errors.add(String.format("%s already exist", userUpdateDTO.getMainEmail()));
            }
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        user.setLastChangedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setPassword(encoder.encode(userUpdateDTO.getPassword()));
        user.setMainEmail(userUpdateDTO.getMainEmail());
        user.setUsername(userUpdateDTO.getUsername());

        userRepository.save(user);

        return errors;
    }

    @Override
    public void logoutUser() {
        currentUser.logout();
    }

    @Override
    public void deleteUser() {

    }

//    private <E> List<String> validateUserInput(Class<E> entity) {
//
//        List<String> errors = new ArrayList<>();
//
////        if (entity.isInstance()) {
////
////        }
//
//        boolean isValidDTO = validationUtils.isValid(entity);
//        Optional<User> foundUserByUsername = userRepository.findFirstByUsername(entity.getUsername());
//        Optional<User> foundUserByMainEmail = userRepository.findFirstByMainEmail(entity.getMainEmail());
//
//        if (!isValidDTO) {
//            errors.add(USER_ERR.toString());
//        }
//
//        if (foundUserByUsername.isPresent()) {
//            errors.add(String.format("%s already exist", entity.getUsername()));
//        }
//
//        if (foundUserByMainEmail.isPresent()) {
//            errors.add(String.format("%s already exist", entity.getMainEmail()));
//        }
//
//        if (!errors.isEmpty()) {
//            return errors;
//        }
//
//        return errors;
//
//    }


}
