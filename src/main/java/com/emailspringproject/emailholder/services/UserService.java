package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.domain.dtos.UserUpdateUsernameDTO;
import com.emailspringproject.emailholder.domain.entities.User;

import java.util.List;

public interface UserService {

    Boolean loginUser(UserLoginDTO userLoginDTO);

    List<String> registerUser(UserRegisterDTO userDTO);

    List<String> updateUserUsername(UserUpdateUsernameDTO userUpdateUsernameDTO);

    void logoutUser();

    User getCurrentUser();

    void deleteUser();
}
