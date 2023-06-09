package com.staselko.HelpDesk.controller;

import com.staselko.HelpDesk.model.dto.CommentDto;
import com.staselko.HelpDesk.model.response.CommentsResponse;
import com.staselko.HelpDesk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Value("${pagination.page_size}")
    private String page_size;

    @PostMapping
    public ResponseEntity<Void> saveComment(@RequestBody @Valid CommentDto commentDto,
                                            @RequestParam(value = "ticketId") Long ticketId){
        commentService.saveComment(commentDto, ticketId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<CommentsResponse> getAllCommentByTicket(@RequestParam(value = "ticketId") Long ticketId,
                                                                  @RequestParam(value = "pageNo", defaultValue = "0",
                                                                          required = false) int pageNo){
        CommentsResponse comments = commentService.getComment(ticketId,pageNo, Integer.parseInt(page_size));
        return ResponseEntity.ok(comments);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable(value = "id") Long commentId,
                                              @RequestBody  @Valid CommentDto commentDto){
        commentService.changeComment(commentId, commentDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "id") Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
