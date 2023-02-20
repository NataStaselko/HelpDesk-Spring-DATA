package com.staselko.HelpDesk.service.Impl;

import com.staselko.HelpDesk.model.entiti.Attachment;
import com.staselko.HelpDesk.model.entiti.Ticket;
import com.staselko.HelpDesk.model.exception.ResourceNotFoundException;
import com.staselko.HelpDesk.repository.AttachmentRepo;
import com.staselko.HelpDesk.service.AttachmentService;
import com.staselko.HelpDesk.service.HistoryService;
import com.staselko.HelpDesk.service.TicketService;
import com.staselko.HelpDesk.utils.DescriptionHistoryCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepo attachmentRepo;
    private final TicketService ticketService;
    private final HistoryService historyService;
    private final DescriptionHistoryCreator descriptionCreator;

    @Override
    public void saveAllAttachment(MultipartFile[] files, Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        for (MultipartFile file : files) {
            if (file.getSize() / 1048576 > 5) {
                throw new MaxUploadSizeExceededException(5242880L);
            } else {
                Attachment attachment = new Attachment();
                attachment.setName(file.getOriginalFilename());
                try {
                    attachment.setBlob(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                attachment.setTicket(ticket);
                attachmentRepo.save(attachment);
                historyService.addHistory(ticket, ticket.getOwner(),
                        descriptionCreator.actionAddFile(),
                        descriptionCreator.descriptionAddFile(attachment.getName()));
            }
        }
    }

    @Override
    public List<Attachment> getAllAttachment(Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return attachmentRepo.findAllByTicket(ticket);
    }

    @Override
    public void deleteAttachment(Long attachmentId) {
        Attachment attachment = attachmentRepo.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId));
        Ticket ticket = attachment.getTicket();
        historyService.addHistory(ticket, ticket.getOwner(),
                descriptionCreator.actionRemoveFile(),
                descriptionCreator.descriptionRemoveFile(attachment.getName()));
        attachmentRepo.deleteById(attachmentId);
    }

    @Override
    public Attachment getAttachmentById(Long attachmentId) {
        return attachmentRepo.findById(attachmentId).orElseThrow();
    }

}
