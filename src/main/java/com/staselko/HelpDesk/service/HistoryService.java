package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.model.entity.Ticket;
import com.staselko.HelpDesk.model.entity.User;
import com.staselko.HelpDesk.model.response.HistoryResponse;

public interface HistoryService {
    void addHistory(Ticket ticket, User user, String action, String description);

    HistoryResponse getHistoriesByTicket(Long ticketId, int pageNo, int pageSize);
}
