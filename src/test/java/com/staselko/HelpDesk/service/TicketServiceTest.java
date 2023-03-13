package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.converter.TicketConverter;
import com.staselko.HelpDesk.converter.UserConverter;
import com.staselko.HelpDesk.model.dto.TicketDto;
import com.staselko.HelpDesk.model.dto.UserDto;
import com.staselko.HelpDesk.model.entiti.Category;
import com.staselko.HelpDesk.model.entiti.Ticket;
import com.staselko.HelpDesk.model.entiti.User;
import com.staselko.HelpDesk.model.enums.State;
import com.staselko.HelpDesk.model.enums.Urgency;
import com.staselko.HelpDesk.repository.TicketRepo;
import com.staselko.HelpDesk.service.Impl.TicketServiceImpl;
import com.staselko.HelpDesk.service.mail.MailService;
import com.staselko.HelpDesk.utils.DescriptionHistoryCreator;
import com.staselko.HelpDesk.utils.UserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketConverter ticketConverter;

    @Mock
    private TicketRepo ticketRepo;

    @Mock
    private UserProvider userProvider;

    @Mock
    private UserConverter userConverter;

    @Mock
    private MailService mailService;

    @Mock
    private HistoryService historyService;

    @Mock
    private DescriptionHistoryCreator descriptionCreator;

    @InjectMocks
    private TicketServiceImpl ticketService;



    @Test
    void createTicket(){
        TicketDto ticketDto = getTicketDto();
        Ticket ticketBefore = getTicketBefore();
        Ticket ticketAfter = getTicketAfter();
        User user = new User();
        UserDto userDto = new UserDto();
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(userConverter.toUserDto(user)).thenReturn(userDto);
        when(ticketConverter.toTicket(ticketDto)).thenReturn(ticketBefore);
        when(ticketRepo.save(ticketBefore)).thenReturn(ticketAfter);
        Ticket test = ticketService.createTicket(ticketDto);
        assertEquals(ticketAfter, test);
    }

    @Test
    void getTicketById(){
        Ticket ticket = getTicketAfter();
        when(ticketRepo.findById(anyLong())).thenReturn(Optional.of(ticket));
        Ticket test = ticketService.getTicketById(1l);
        assertEquals(ticket, test);
    }

    private TicketDto getTicketDto(){
        Category category = new Category(1L, "Application & Services");
        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("name");
        ticketDto.setState("DRAFT");
        ticketDto.setUrgency("CRITICAL");
        ticketDto.setCategory(category);
        return ticketDto;
    }

    private Ticket getTicketBefore(){
        Category category = new Category(1L, "Application & Services");
        Ticket ticket = new Ticket();
        ticket.setName("name");
        ticket.setState(State.valueOf("DRAFT"));
        ticket.setUrgency(Urgency.valueOf("CRITICAL"));
        ticket.setCategory(category);
        return ticket;
    }

    private Ticket getTicketAfter(){
        Category category = new Category(1L, "Application & Services");
        Ticket ticket = new Ticket();
        ticket.setId(1l);
        ticket.setName("name");
        ticket.setState(State.valueOf("DRAFT"));
        ticket.setUrgency(Urgency.valueOf("CRITICAL"));
        ticket.setCategory(category);
        return ticket;
    }


}
