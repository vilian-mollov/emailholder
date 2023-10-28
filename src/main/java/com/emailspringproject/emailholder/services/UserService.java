package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.*;

import java.util.List;

public interface UserService {

    Boolean loginUser(UserLoginDTO userLoginDTO);

    List<String> registerUser(UserRegisterDTO userDTO);

    List<String> updateUser(UserUpdateDTO userUpdateDTO);

    void logoutUser();

    void deleteUser();
}
