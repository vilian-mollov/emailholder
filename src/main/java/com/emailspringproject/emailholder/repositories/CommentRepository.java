package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
