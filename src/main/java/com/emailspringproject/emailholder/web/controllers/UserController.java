package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.UserRegisterDTO;
import com.emailspringproject.emailholder.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView loginUser() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");

        // TODO add userService.loginUser()

        return modelAndView;
    }


    @GetMapping("/register")
    public ModelAndView registerUser2() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/register");

        // TODO add userService.registerUser()

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid UserRegisterDTO userDto, BindingResult bindingResult ) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");

        List<String> errors = bindingResult
                .getAllErrors()
                .stream()
                .map(e -> e.getObjectName() + " " + e.getDefaultMessage())
                .toList();

        // Go to error page
        if (!errors.isEmpty()) {
            ModelAndView errorView = new ModelAndView();
            errorView.setViewName("error");
            errorView.addObject("errors",errors);

            return errorView;
        }

        userService.registerUser(userDto);

        return modelAndView;
    }


    @PostMapping("/update")
    public ModelAndView updateUser() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");

        // TODO add userService.updateUser()

        return modelAndView;
    }

    @DeleteMapping("/delete")
    public ModelAndView deleteUser() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");

        // TODO add userService.deleteUser()

        return modelAndView;
    }

    
}
