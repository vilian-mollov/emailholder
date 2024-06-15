package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.*;
import com.emailspringproject.emailholder.domain.entities.User;

import java.util.List;

public interface UserService {

    Boolean loginUser(UserLoginDTO userLoginDTO);

    List<String> registerUser(UserRegisterDTO userDTO);

    List<String> updateUser(UserUpdateDTO userUpdateDTO);

    void logoutUser();

    User getCurrentUser();

    void deleteUser();
}
