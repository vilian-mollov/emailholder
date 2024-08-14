package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.domain.dtos.UserUpdateUsernameDTO;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.services.UserService;
import com.emailspringproject.emailholder.utilities.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private CurrentUser currentUser;

    @Autowired
    public UserController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(@ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO) {

        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute("userLoginDTO") @Valid UserLoginDTO userLoginDTO,
                                  BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName("login");
            return modelAndView;
        }


        Boolean isLogged = userService.loginUser(userLoginDTO);

        if (!isLogged) {
            modelAndView.addObject("hasLoginError", true);
            modelAndView.setViewName("login");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }


    @GetMapping("/register")
    public ModelAndView getRegister(@ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO, ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("userRegisterDTO") @Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult, ModelAndView modelAndView) {


        if (bindingResult.hasErrors()) {

            modelAndView.setViewName("register");
            return modelAndView;
        }

        List<String> errors = userService.registerUser(userRegisterDTO);

        if (errors != null && !errors.isEmpty()) {
            modelAndView.addObject("hasRegisterError", true);
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("register");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @PutMapping("/add/email")
    public ModelAndView addEmailToUser(ModelAndView modelAndView) {
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView getProfile(ModelAndView modelAndView) {
        User user = userService.getCurrentUser();

        modelAndView.setViewName("profile");
        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("email", user.getMainEmail());
        return modelAndView;
    }

    @GetMapping("/update/username")
    public ModelAndView getUpdateUser(@ModelAttribute("userUpdateUsernameDTO") UserUpdateUsernameDTO userUpdateUsernameDTO, ModelAndView modelAndView) {
        User user = userService.getCurrentUser();
        if (!currentUser.isLogged()) {
            modelAndView.setViewName("redirect:/home");
            return modelAndView;
        }

        modelAndView.setViewName("update_username");
        modelAndView.addObject("username_value", user.getUsername());
        return modelAndView;
    }

    @PostMapping("/update/username")
    public ModelAndView updateUserUsername(@ModelAttribute("userUpdateUsernameDTO") @Valid UserUpdateUsernameDTO userUpdateUsernameDTO, BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("update_username");
            return modelAndView;
        }

        List<String> errors = userService.updateUserUsername(userUpdateUsernameDTO);

        if (errors != null && !errors.isEmpty()) {
            modelAndView.addObject("hasUpdateError", true);
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("update_username");
            return modelAndView;
        }

        modelAndView.addObject("userUpdateUsernameDTO", userUpdateUsernameDTO);
        modelAndView.setViewName("redirect:/users/profile");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView) {

        userService.logoutUser();

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @DeleteMapping("/delete")
    public ModelAndView deleteUser(ModelAndView modelAndView) {

        modelAndView.setViewName("redirect:/index");

        // TODO add userService.deleteUser()

        return modelAndView;
    }


}
