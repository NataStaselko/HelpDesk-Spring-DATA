package com.staselko.HelpDesk.converter;

import com.staselko.HelpDesk.model.dto.CommentDto;
import com.staselko.HelpDesk.model.entity.Comment;

public interface CommentConverter {

    Comment toComment(CommentDto commentDto);

    CommentDto toCommentDto(Comment comment);
}
