package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.model.dto.TicketDto;
import com.staselko.HelpDesk.model.entity.Ticket;
import com.staselko.HelpDesk.model.enums.Action;
import com.staselko.HelpDesk.model.response.TicketResponse;
public interface TicketService {

    Ticket createTicket(TicketDto ticketDto);
    Ticket getTicketById(Long ticketId);

    TicketDto getTicketDtoById(Long ticketDtoId);

    TicketResponse getTicketList(int pageNo, int pageSize, String sortBy, String sortDir, Long
                                 filter_id, String filter_name, String filter_date, String filter_urgency,
                                 String filter_state, String flag);
    void  updateOrChangeAction(Long ticketId, Action action, TicketDto ticketDto);
}
