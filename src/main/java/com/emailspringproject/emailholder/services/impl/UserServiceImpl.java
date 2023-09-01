package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.UserRepository;
import com.emailspringproject.emailholder.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void loginUser() {

    }

    @Override
    public void registerUser(UserRegisterDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        userRepository.save(user);

    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }

}
