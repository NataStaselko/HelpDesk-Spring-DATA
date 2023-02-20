package com.staselko.HelpDesk.model.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data
public class HistoryDto {
    private String date;
    private UserDto userDto;
    private String action;
    private String description;
}
