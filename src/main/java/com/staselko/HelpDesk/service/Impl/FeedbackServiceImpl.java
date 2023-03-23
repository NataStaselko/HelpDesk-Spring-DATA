package com.staselko.HelpDesk.service.Impl;

import com.staselko.HelpDesk.converter.FeedbackConverter;
import com.staselko.HelpDesk.model.dto.FeedbackDto;
import com.staselko.HelpDesk.model.entity.Feedback;
import com.staselko.HelpDesk.model.entity.Ticket;
import com.staselko.HelpDesk.repository.FeedbackRepo;
import com.staselko.HelpDesk.service.FeedbackService;
import com.staselko.HelpDesk.service.TicketService;
import com.staselko.HelpDesk.utils.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepo feedbackRepo;
    private final TicketService ticketService;
    private final UserProvider userProvider;
    private final FeedbackConverter feedbackConverter;

    @Override
    public void saveFeedback(FeedbackDto feedbackDto, Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        Feedback feedback = feedbackConverter.toFeedback(feedbackDto);
        feedback.setTicket(ticket);
        feedback.setUser(userProvider.getCurrentUser());
        feedbackRepo.save(feedback);
    }

    @Override
    public List<FeedbackDto> getFeedbackByTicket(Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return feedbackRepo.findAllByTicket(ticket).stream()
                .map(feedbackConverter::toFeedbackDto)
                .collect(Collectors.toList());
    }
}
