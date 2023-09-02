package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;

import java.util.List;

public interface UserService {

    void loginUser();

    List<String> registerUser(UserRegisterDTO userDTO);

    void updateUser();

    void deleteUser();
}
