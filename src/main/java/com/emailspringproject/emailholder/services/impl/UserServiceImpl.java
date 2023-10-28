package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
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

import static com.emailspringproject.emailholder.constants.Errors.USER_REGISTER_ERR;

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

        if(persistedUser != null) {
            String encodedPassword = persistedUser.getPassword();
            String rawPassword = userLoginDTO.getPassword();

            isCorrect = encodedPassword != null && encoder.matches(rawPassword, encodedPassword);

            if(isCorrect) {
                currentUser.setLogged(true);
                currentUser.setUsername(userLoginDTO.getUsername());
            }else {
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

        boolean validDTO = validationUtils.isValid(userDTO);
        Optional<User> foundUserByUsername = userRepository.findFirstByUsername(userDTO.getUsername());
        Optional<User> foundUserByMainEmail = userRepository.findFirstByMainEmail(userDTO.getMainEmail());

        if (!validDTO ) {
            errors.add(USER_REGISTER_ERR.toString());
        }

        if(foundUserByUsername.isPresent()){
            errors.add(String.format("%s already exist",userDTO.getUsername()));
        }

        if(foundUserByMainEmail.isPresent()) {
            errors.add(String.format("%s already exist",userDTO.getMainEmail()));
        }

        if(!errors.isEmpty()) {
            return errors;
        }

        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User user = modelMapper.map(userDTO, User.class);

        userRepository.save(user);
        return errors;
    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }




}
