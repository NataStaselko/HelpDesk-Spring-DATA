package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entity.Attachment;
import com.staselko.HelpDesk.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
    List<Attachment> findAllByTicket(Ticket ticket);
}
