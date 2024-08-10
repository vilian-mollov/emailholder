package com.emailspringproject.emailholder.web.controllers;


import com.emailspringproject.emailholder.domain.dtos.CommentDTO;
import com.emailspringproject.emailholder.domain.dtos.UserLoginDTO;
import com.emailspringproject.emailholder.services.CommentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private CommentsService commentsService;

    @Autowired
    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/site/{site_id}")
    public ModelAndView getAllCommentsForSite(@ModelAttribute("commentDTO") CommentDTO commentDTO, ModelAndView modelAndView, @PathVariable Long site_id){
        modelAndView.setViewName("comments");
        modelAndView.addObject("comments", commentsService.getAllCommentsForSite(site_id));
        return modelAndView;
    }


    @PostMapping("/site")
    public ModelAndView addCommentForSite(@ModelAttribute("commentDTO") @Valid CommentDTO commentDTO,
                                              BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.setViewName("comments");
        commentsService.addCommentForSite(commentDTO); //todo nz dali da e dr void ili ddz

        return modelAndView;
    }
}
