package com.staselko.HelpDesk.converter.impl;

import com.staselko.HelpDesk.converter.HistoryConverter;
import com.staselko.HelpDesk.converter.UserConverter;
import com.staselko.HelpDesk.model.dto.HistoryDto;
import com.staselko.HelpDesk.model.entiti.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class HistoryConverterImpl implements HistoryConverter {
    private final UserConverter userConverter;


    @Override
    public HistoryDto toHistoryDto(History history) {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setDate(history.getDate().format(DateTimeFormatter
                .ofPattern("MMM dd, yyyy HH:mm:ss").localizedBy(Locale.ENGLISH)));
        historyDto.setUserDto(userConverter.toUserDto(history.getUser()));
        historyDto.setAction(history.getAction());
        historyDto.setDescription(history.getDescription());
        return historyDto;
    }
}
