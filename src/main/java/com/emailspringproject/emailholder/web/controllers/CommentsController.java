package com.emailspringproject.emailholder.web.controllers;


import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;
import com.emailspringproject.emailholder.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    private CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/site")
    public ModelAndView getAllCommentsForSite(@ModelAttribute("siteDTO") SiteImportDTO siteDTO, ModelAndView modelAndView){
        modelAndView.setViewName("comments");
        modelAndView.addObject("sites", commentsService.getAllCommentsForSite(siteDTO));
        return modelAndView;
    }
}
