package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.model.entiti.Ticket;
import com.staselko.HelpDesk.model.entiti.User;
import com.staselko.HelpDesk.model.response.HistoryResponse;

public interface HistoryService {
    void addHistory(Ticket ticket, User user, String action, String description);

    HistoryResponse getHistoriesByTicket(Long ticketId, int pageNo, int pageSize);
}
