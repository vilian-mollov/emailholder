package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.*;
import com.emailspringproject.emailholder.services.UserService;
import com.emailspringproject.emailholder.utilities.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/update")
    public ModelAndView getUpdateUser(@ModelAttribute("userUpdateDTO") UserUpdateDTO userUpdateDTO, ModelAndView modelAndView) {

        if (!currentUser.isLogged()) {
            modelAndView.setViewName("redirect:/home");
            return modelAndView;
        }

        modelAndView.setViewName("update-profile");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateUser(@ModelAttribute("userUpdateDTO") @Valid UserUpdateDTO userUpdateDTO, BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("update-profile");
            return modelAndView;
        }

        List<String> errors = userService.updateUser(userUpdateDTO);

        if (errors != null && !errors.isEmpty()) {
            modelAndView.addObject("hasUpdateError", true);
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("update-profile");
            return modelAndView;
        }


        modelAndView.setViewName("redirect:/home");
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
