package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entity.Comment;
import com.staselko.HelpDesk.model.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByTicket(Ticket ticket, Pageable pageable);
}
