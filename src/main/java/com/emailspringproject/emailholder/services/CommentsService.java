package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.CommentDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;

import java.util.List;

public interface CommentsService {

    List<CommentDTO> getAllCommentsForSite(Long siteId);

    CommentDTO addCommentForSite(CommentDTO commentDTO, Long siteId);
}
