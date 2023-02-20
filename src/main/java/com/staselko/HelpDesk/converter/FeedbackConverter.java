package com.staselko.HelpDesk.converter;

import com.staselko.HelpDesk.model.dto.FeedbackDto;
import com.staselko.HelpDesk.model.entiti.Feedback;

public interface FeedbackConverter {
    Feedback toFeedback(FeedbackDto feedbackDto);

    FeedbackDto toFeedbackDto(Feedback feedback);
}
