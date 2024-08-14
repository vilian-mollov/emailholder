package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.domain.dtos.UserUpdateUsernameDTO;
import com.emailspringproject.emailholder.domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    Authentication login(String email);

    List<String> registerUser(UserRegisterDTO userDTO);

    List<String> updateUserUsername(UserUpdateUsernameDTO userUpdateUsernameDTO, UserDetails userDetails);

    User getCurrentUser(UserDetails userDetails);

}
