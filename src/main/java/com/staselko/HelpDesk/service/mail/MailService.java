package com.staselko.HelpDesk.service.mail;

import com.staselko.HelpDesk.model.entiti.Ticket;
import com.staselko.HelpDesk.model.entiti.User;

public interface MailService {
    void sendMailToUser(User user, Ticket ticket);

}
