package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public ModelAndView loginUser() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");

        // TODO add userService.loginUser()

        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView registerUser() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");

        // TODO add userService.registerUser()

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
