package com.staselko.HelpDesk.converter.impl;

import com.staselko.HelpDesk.converter.FeedbackConverter;
import com.staselko.HelpDesk.model.dto.FeedbackDto;
import com.staselko.HelpDesk.model.entiti.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackConverterImpl implements FeedbackConverter {
    @Override
    public Feedback toFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setRate(feedbackDto.getRate());
        feedback.setText(feedbackDto.getText());
        return feedback;
    }

    @Override
    public FeedbackDto toFeedbackDto(Feedback feedback) {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setRate(feedback.getRate());
        feedbackDto.setText(feedback.getText());
        return feedbackDto;
    }
}
