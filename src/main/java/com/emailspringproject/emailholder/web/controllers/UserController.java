package com.emailspringproject.emailholder.web.controllers;


import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.domain.dtos.UserUpdateUsernameDTO;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/login")
    public ModelAndView loginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login-error")
    public String onFailure(@ModelAttribute("username") String username, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("bad_credentials", "true");
        return "login";
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

//    @PutMapping("/add/email")
//    public ModelAndView addEmailToUser(ModelAndView modelAndView) {
//        return modelAndView;
//    }

    @GetMapping("/profile")
    public ModelAndView getProfile(@AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {
        User user = userService.getCurrentUser(userDetails);

        modelAndView.setViewName("profile");
        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("email", user.getMainEmail());
        return modelAndView;
    }

    @GetMapping("/update/username")
    public ModelAndView getUpdateUser(@ModelAttribute("userUpdateUsernameDTO") UserUpdateUsernameDTO userUpdateUsernameDTO,
                                      @AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {
        User user = userService.getCurrentUser(userDetails);
        if (!userDetails.isAccountNonExpired()) {
            modelAndView.setViewName("redirect:/home");
            return modelAndView;
        }

        modelAndView.setViewName("update_username");
        modelAndView.addObject("username_value", user.getUsername());
        return modelAndView;
    }

    @PostMapping("/update/username")
    public ModelAndView updateUserUsername(@ModelAttribute("userUpdateUsernameDTO") @Valid UserUpdateUsernameDTO userUpdateUsernameDTO, BindingResult bindingResult,
                                           @AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("update_username");
            return modelAndView;
        }

        List<String> errors = userService.updateUserUsername(userUpdateUsernameDTO, userDetails);

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
}
