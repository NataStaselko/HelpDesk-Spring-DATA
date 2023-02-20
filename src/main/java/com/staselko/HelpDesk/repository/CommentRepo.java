package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entiti.Comment;
import com.staselko.HelpDesk.model.entiti.Ticket;
import com.staselko.HelpDesk.model.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByTicket(Ticket ticket, Pageable pageable);
}
