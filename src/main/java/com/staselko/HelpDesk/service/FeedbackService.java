package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.model.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    void saveFeedback(FeedbackDto feedbackDto, Long ticketId);

    List<FeedbackDto> getFeedbackByTicket(Long ticketId);
}
