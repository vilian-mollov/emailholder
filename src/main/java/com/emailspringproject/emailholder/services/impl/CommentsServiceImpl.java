package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.CommentDTO;
import com.emailspringproject.emailholder.domain.entities.Comment;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.repositories.CommentRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.CommentsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final SiteRepository siteRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentsServiceImpl(SiteRepository siteRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.siteRepository = siteRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CommentDTO> getAllCommentsForSite(Long siteId) {
        Optional<Site> siteOpt = siteRepository.findById(siteId);
        Site site = siteOpt.get();
        List<Comment> comments = site.getComments();

        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
            commentsDTO.add(commentDTO);
        }

        return commentsDTO;
    }

    @Override
    public CommentDTO  addCommentForSite(CommentDTO commentDTO, Long siteId) {

        Optional<Site> siteOpt = siteRepository.findById(siteId);
        Site site = siteOpt.get();

        Comment comment = modelMapper.map(commentDTO, Comment.class);

        comment.setSite(site);
        site.addComment(comment);

        commentRepository.save(comment);
        siteRepository.save(site);
        return null;
    }
}
