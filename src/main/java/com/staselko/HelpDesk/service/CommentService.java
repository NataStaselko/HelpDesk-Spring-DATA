package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.model.dto.CommentDto;
import com.staselko.HelpDesk.model.response.CommentsResponse;

public interface CommentService {
    void saveComment(CommentDto commentDto, Long ticketId);
    CommentsResponse getComment(Long ticketId, int pageNo, int pageSize);
    void changeComment(Long commentId,  CommentDto commentDto);

    void deleteComment(Long commentId);
}
