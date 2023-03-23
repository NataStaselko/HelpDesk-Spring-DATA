package com.staselko.HelpDesk.converter;

import com.staselko.HelpDesk.model.dto.TicketDto;
import com.staselko.HelpDesk.model.entity.Ticket;

public interface TicketConverter {
    Ticket toTicket(TicketDto ticketDto);
    TicketDto toTicketDto(Ticket ticket);
}