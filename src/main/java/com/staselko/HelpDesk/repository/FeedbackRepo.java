package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entiti.Feedback;
import com.staselko.HelpDesk.model.entiti.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
    List<Feedback>findAllByTicket(Ticket ticket);
}
