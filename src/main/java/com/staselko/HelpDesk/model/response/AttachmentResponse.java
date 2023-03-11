package com.staselko.HelpDesk.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttachmentResponse {
    private Long id;
    private String name;
    private String path;
    private String type;
}
