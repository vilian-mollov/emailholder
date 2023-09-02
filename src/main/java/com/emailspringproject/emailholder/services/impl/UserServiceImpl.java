package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.UserRepository;
import com.emailspringproject.emailholder.services.UserService;
import com.emailspringproject.emailholder.utilities.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private ValidationUtils validationUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           ValidationUtils validationUtils) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public void loginUser() {

    }

    @Override
    public List<String> registerUser(UserRegisterDTO userDTO) {

        List<String> problems = new ArrayList<>();
        userDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userDTO.setLastChangedAt(Timestamp.valueOf(LocalDateTime.now()));

        boolean validDTO = validationUtils.isValid(userDTO);
        Optional<User> foundUserByUsername = userRepository.findFirstByUsername(userDTO.getUsername());
        Optional<User> foundUserByMainEmail = userRepository.findFirstByMainEmail(userDTO.getMainEmail());

        if (!validDTO || foundUserByUsername.isPresent() || foundUserByMainEmail.isPresent()) {

            if (!validDTO) {
                problems.add("Not valid properties!");
            }

            if (foundUserByUsername.isPresent()) {
                problems.add("User with username " + userDTO.getUsername() + " already exists!");
            }

            if (foundUserByMainEmail.isPresent()) {
                problems.add("User with email " + userDTO.getMainEmail() + " already exists!");
            }

            return problems;
        }


        User user = modelMapper.map(userDTO, User.class);

        userRepository.save(user);
        return problems;
    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }




}
