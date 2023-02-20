package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entiti.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TicketRepo extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

}
