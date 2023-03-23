package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entity.Feedback;
import com.staselko.HelpDesk.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
    List<Feedback>findAllByTicket(Ticket ticket);
}
