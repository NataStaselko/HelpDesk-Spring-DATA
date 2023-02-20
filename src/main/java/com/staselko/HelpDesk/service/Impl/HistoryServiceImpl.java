package com.staselko.HelpDesk.service.Impl;

import com.staselko.HelpDesk.converter.HistoryConverter;
import com.staselko.HelpDesk.model.dto.HistoryDto;
import com.staselko.HelpDesk.model.entiti.History;
import com.staselko.HelpDesk.model.entiti.Ticket;
import com.staselko.HelpDesk.model.entiti.User;
import com.staselko.HelpDesk.model.exception.ResourceNotFoundException;
import com.staselko.HelpDesk.model.response.HistoryResponse;
import com.staselko.HelpDesk.repository.HistoryRepo;
import com.staselko.HelpDesk.repository.TicketRepo;
import com.staselko.HelpDesk.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepo historyRepo;
    private final TicketRepo ticketRepo;
    private final HistoryConverter historyConverter;

    @Override
    public void addHistory(Ticket ticket, User user, String action, String description) {
        History history = new History();
        history.setTicket(ticket);
        history.setUser(user);
        history.setAction(action);
        history.setDescription(description);
        historyRepo.save(history);
    }

    @Override
    public HistoryResponse getHistoriesByTicket(Long ticketId, int pageNo, int pageSize) {
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketId));
        Sort sort = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<History> page = historyRepo.findAllByTicket(ticket, pageable);
        List<HistoryDto> content = page.getContent().stream()
                .map(historyConverter::toHistoryDto)
                .collect(Collectors.toList());
        HistoryResponse historyResponse = new HistoryResponse();
        historyResponse.setContent(content);
        historyResponse.setPageNo(page.getNumber());
        historyResponse.setPageSize(page.getSize());
        historyResponse.setTotalElements(page.getTotalElements());
        historyResponse.setTotalPages(page.getTotalPages());
        historyResponse.setLast(page.isLast());
        return historyResponse;
    }
}
