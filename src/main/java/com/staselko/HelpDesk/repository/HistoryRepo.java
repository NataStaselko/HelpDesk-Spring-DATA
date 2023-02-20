package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entiti.History;
import com.staselko.HelpDesk.model.entiti.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepo extends JpaRepository<History, Long> {
    Page<History> findAllByTicket(Ticket ticket, Pageable pageable);
}
