package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;

import java.util.List;

public interface UserService {

    Boolean loginUser(UserLoginDTO userLoginDTO);

    List<String> registerUser(UserRegisterDTO userDTO);

    void updateUser();

    void deleteUser();
}
