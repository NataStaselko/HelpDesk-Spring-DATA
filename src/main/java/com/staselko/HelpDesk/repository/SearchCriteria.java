package com.staselko.HelpDesk.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String field;
    private Object value;
    private Operation operation;
}
