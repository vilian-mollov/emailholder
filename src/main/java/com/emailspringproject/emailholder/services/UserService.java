package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;

public interface UserService {

    void loginUser();

    void registerUser(UserRegisterDTO userDTO);

    void updateUser();

    void deleteUser();
}
