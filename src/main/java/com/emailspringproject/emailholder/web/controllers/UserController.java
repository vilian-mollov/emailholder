package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
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
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(@ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO) {

        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute("userLoginDTO") @Valid UserLoginDTO userLoginDTO,
                                  BindingResult bindingResult, ModelAndView modelAndView) {

        if(bindingResult.hasErrors()){

            modelAndView.setViewName("login");
            return modelAndView;
        }


        Boolean isLogged = userService.loginUser(userLoginDTO);

        if(!isLogged){
            modelAndView.addObject("hasLoginError", true);
            modelAndView.setViewName("login");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }


    @GetMapping("/register")
    public ModelAndView getRegister(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(ModelAndView modelAndView, @Valid UserRegisterDTO userDto, BindingResult bindingResult ) {

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

        List<String> problems = userService.registerUser(userDto);

        if (!problems.isEmpty()) {
            ModelAndView errorView = new ModelAndView();
            errorView.setViewName("error");
            errorView.addObject("errors",problems);

            return errorView;
        }

        return modelAndView;
    }

    @PutMapping("/add/email")
    public ModelAndView addEmailToUser(ModelAndView modelAndView) {
        return modelAndView;
    }


    @PostMapping("/update")
    public ModelAndView updateUser(ModelAndView modelAndView) {

        modelAndView.setViewName("redirect:/index");

        // TODO add userService.updateUser()

        return modelAndView;
    }

    @DeleteMapping("/delete")
    public ModelAndView deleteUser(ModelAndView modelAndView) {

        modelAndView.setViewName("redirect:/index");

        // TODO add userService.deleteUser()

        return modelAndView;
    }

    
}
