package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.CommentDTO;
import com.emailspringproject.emailholder.domain.entities.Comment;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.CommentRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.repositories.UserRepository;
import com.emailspringproject.emailholder.services.CommentsService;
import com.emailspringproject.emailholder.utilities.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final SiteRepository siteRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    private final UserRepository userRepository;


    @Autowired
    public CommentsServiceImpl(SiteRepository siteRepository,
                               CommentRepository commentRepository,
                               ModelMapper modelMapper,
                               ValidationUtils validationUtils,
                               UserRepository userRepository) {
        this.siteRepository = siteRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentDTO> getAllCommentsForSite(Long siteId) {
        Optional<Site> siteOpt = siteRepository.findById(siteId);
        Site site = siteOpt.get();
        Set<Comment> comments = site.getComments();
        List<Comment> sortedComments = new ArrayList<>(comments.stream().sorted(Comparator.comparing(Comment::getCreatedTime)).toList());
        Collections.reverse(sortedComments);

        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : sortedComments) {
            CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
            commentsDTO.add(commentDTO);
        }

        return commentsDTO;
    }

    @Override
    public CommentDTO addCommentForSite(CommentDTO commentDTO, Long siteId, UserDetails userDetails) {

        if (!this.validationUtils.isValid(commentDTO)) {
            return null;
        }

        Optional<Site> siteOpt = siteRepository.findById(siteId);
        Site site = siteOpt.get();

        Comment comment = modelMapper.map(commentDTO, Comment.class);

        Optional<User> userOpt = userRepository.findFirstByUsername(userDetails.getUsername());
        User user = userOpt.get();

        comment.setSite(site);
        comment.setUser(user);
        comment.setCreatedTime(LocalDateTime.now());
        commentRepository.save(comment);

        user.getComments().add(comment);
        userRepository.save(user);

//        site.addComment(comment);
        site.getComments().add(comment);
        siteRepository.save(site);
        return null;
    }
}
