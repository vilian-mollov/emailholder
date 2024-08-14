package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.domain.dtos.UserUpdateUsernameDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.emailspringproject.emailholder.constants.Errors.USER_ERR;

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

        if (persistedUser != null) {
            String encodedPassword = persistedUser.getPassword();
            String rawPassword = userLoginDTO.getPassword();

            isCorrect = encodedPassword != null && encoder.matches(rawPassword, encodedPassword);

            if (isCorrect) {
                currentUser.setLogged(true);
                currentUser.setUsername(userLoginDTO.getUsername());
            } else {
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

        boolean isValidDTO = validationUtils.isValid(userDTO);
        Optional<User> foundUserByUsername = userRepository.findFirstByUsername(userDTO.getUsername());
        Optional<User> foundUserByMainEmail = userRepository.findFirstByMainEmail(userDTO.getMainEmail());

        if (!isValidDTO) {
            errors.add(USER_ERR.toString());
        }

        if (foundUserByUsername.isPresent()) {
            errors.add(String.format("%s already exist", userDTO.getUsername()));
        }

        if (foundUserByMainEmail.isPresent()) {
            errors.add(String.format("%s already exist", userDTO.getMainEmail()));
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        String rawPassword = userDTO.getPassword();
        userDTO.setPassword(encoder.encode(rawPassword));
        User user = modelMapper.map(userDTO, User.class);

        userRepository.save(user);

//      Successful Register and need a login
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername(user.getUsername());
        loginDTO.setPassword(rawPassword);

        loginUser(loginDTO);

        return errors;
    }

    @Override
    public List<String> updateUserUsername(UserUpdateUsernameDTO userUpdateUsernameDTO) {

        List<String> errors = new ArrayList<>();
        Optional<User> firstByUsername = userRepository.findFirstByUsername(currentUser.getUsername());

        User user = null;
        if (firstByUsername.isPresent()) {
            user = firstByUsername.get();
        } else {
            return errors;
        }

        if (!user.getUsername().equals(userUpdateUsernameDTO.getCurrentUsername())) {
            errors.add("Current Username is incorrect");
            return errors;
        }

        boolean isValidDTO = validationUtils.isValid(userUpdateUsernameDTO);
        if (!isValidDTO) {
            errors.add(USER_ERR.toString());
        }

        Optional<User> foundUserByUsername = userRepository.findFirstByUsername(userUpdateUsernameDTO.getUsernameNew());

        if (foundUserByUsername.isPresent()) {
            errors.add("This Username already exist");
            return errors;
        }

        String encodedPass = user.getPassword();
        String rawPass = userUpdateUsernameDTO.getPassword();
        boolean isCorrect = false;
        isCorrect = encodedPass != null && encoder.matches(rawPass, encodedPass);

        if (!isCorrect) {
            errors.add("Incorrect password");
            return errors;
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        user.setLastChangedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setUsername(userUpdateUsernameDTO.getUsernameNew());

        userRepository.save(user);

//      Change is Successful and need a login
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername(user.getUsername());
        loginDTO.setPassword(userUpdateUsernameDTO.getPassword());

        loginUser(loginDTO);
        return errors;
    }

    @Override
    public void logoutUser() {
        currentUser.logout();
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public User getCurrentUser() {
        Optional<User> optUser = userRepository.findFirstByUsername(currentUser.getUsername());
        User user = optUser.get();
        return user;
    }

}
