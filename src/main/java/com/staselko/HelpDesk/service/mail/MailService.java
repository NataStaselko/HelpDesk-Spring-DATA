package com.staselko.HelpDesk.service.mail;

import com.staselko.HelpDesk.model.entity.Ticket;
import com.staselko.HelpDesk.model.entity.User;

public interface MailService {
    void sendMailToUser(User user, Ticket ticket);

}
