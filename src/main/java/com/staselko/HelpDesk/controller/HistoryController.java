package com.staselko.HelpDesk.controller;


import com.staselko.HelpDesk.model.dto.HistoryDto;
import com.staselko.HelpDesk.model.response.HistoryResponse;
import com.staselko.HelpDesk.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/histories")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;
    @Value("${pagination.page_size}")
    private String page_size;

    @GetMapping
    public ResponseEntity<HistoryResponse> getHistoriesByTicket(@RequestParam(value = "ticketId") Long ticketId,
                                                                @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo){
        HistoryResponse historyResponse = historyService.getHistoriesByTicket(ticketId, pageNo, Integer.parseInt(page_size));
        return ResponseEntity.ok(historyResponse);
    }
}
