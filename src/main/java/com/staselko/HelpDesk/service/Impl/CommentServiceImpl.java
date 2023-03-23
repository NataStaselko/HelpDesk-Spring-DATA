package com.staselko.HelpDesk.service.Impl;

import com.staselko.HelpDesk.converter.CommentConverter;
import com.staselko.HelpDesk.model.dto.CommentDto;
import com.staselko.HelpDesk.model.entity.Comment;
import com.staselko.HelpDesk.model.entity.Ticket;
import com.staselko.HelpDesk.model.exception.ResourceNotFoundException;
import com.staselko.HelpDesk.model.response.CommentsResponse;
import com.staselko.HelpDesk.repository.CommentRepo;
import com.staselko.HelpDesk.service.CommentService;
import com.staselko.HelpDesk.service.TicketService;
import com.staselko.HelpDesk.utils.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final TicketService ticketService;
    private final CommentRepo commentRepo;
    private final CommentConverter commentConverter;
    private final UserProvider userProvider;


    @Override
    public void saveComment(CommentDto commentDto, Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        Comment comment = commentConverter.toComment(commentDto);
        comment.setTicket(ticket);
        comment.setUser(userProvider.getCurrentUser());
        commentRepo.save(comment);
    }

    @Override
    public CommentsResponse getComment(Long ticketId, int pageNo, int pageSize) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        Sort sort = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> page = commentRepo.findAllByTicket(ticket, pageable);
        List<CommentDto> content = page.getContent().stream()
                .map(commentConverter::toCommentDto)
                .collect(Collectors.toList());
        CommentsResponse commentsResponse = new CommentsResponse();
        commentsResponse.setContent(content);
        commentsResponse.setPageNo(page.getNumber());
        commentsResponse.setPageSize(page.getSize());
        commentsResponse.setTotalElements(page.getTotalElements());
        commentsResponse.setTotalPages(page.getTotalPages());
        commentsResponse.setLast(page.isLast());
        return commentsResponse;
    }

    @Override
    public void changeComment(Long commentId,  CommentDto commentDto) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));
        comment.setText(commentDto.getText());
        commentRepo.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));
        commentRepo.delete(comment);
    }
}
