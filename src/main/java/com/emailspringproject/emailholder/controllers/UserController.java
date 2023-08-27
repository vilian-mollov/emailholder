package com.emailspringproject.emailholder.controllers;

import com.emailspringproject.emailholder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    
}
