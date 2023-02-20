package com.staselko.HelpDesk.converter;
import com.staselko.HelpDesk.model.dto.HistoryDto;
import com.staselko.HelpDesk.model.entiti.History;

public interface HistoryConverter {

    HistoryDto toHistoryDto(History history);
}
