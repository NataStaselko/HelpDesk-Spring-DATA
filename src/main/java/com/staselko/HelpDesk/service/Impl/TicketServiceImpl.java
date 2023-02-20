package com.staselko.HelpDesk.service.Impl;

import com.staselko.HelpDesk.converter.TicketConverter;
import com.staselko.HelpDesk.converter.UserConverter;
import com.staselko.HelpDesk.model.dto.TicketDto;
import com.staselko.HelpDesk.model.entiti.Ticket;
import com.staselko.HelpDesk.model.entiti.User;
import com.staselko.HelpDesk.model.enums.Action;
import com.staselko.HelpDesk.model.enums.State;
import com.staselko.HelpDesk.model.exception.ResourceNotFoundException;
import com.staselko.HelpDesk.model.response.TicketResponse;
import com.staselko.HelpDesk.repository.Operation;
import com.staselko.HelpDesk.repository.SearchCriteria;
import com.staselko.HelpDesk.repository.TicketRepo;
import com.staselko.HelpDesk.repository.TicketSpecification;
import com.staselko.HelpDesk.service.HistoryService;
import com.staselko.HelpDesk.service.TicketService;
import com.staselko.HelpDesk.service.mail.MailService;
import com.staselko.HelpDesk.utils.ActionsList;
import com.staselko.HelpDesk.utils.DescriptionHistoryCreator;
import com.staselko.HelpDesk.utils.StateByTicket;
import com.staselko.HelpDesk.utils.UserProvider;
import com.staselko.HelpDesk.utils.filter.StateFilter;
import com.staselko.HelpDesk.utils.filter.UrgencyFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepo ticketRepo;
    private final TicketConverter ticketConverter;
    private final UserProvider userProvider;
    private final MailService mailService;
    private final ActionsList actionsList;
    private final UserConverter userConverter;
    private final HistoryService historyService;
    private final DescriptionHistoryCreator descriptionCreator;
    private final StateByTicket stateByTicket;
    private final UrgencyFilter urgencyFilter;
    private final StateFilter stateFilter;


    @Override
    public Ticket createTicket(TicketDto ticketDto) {
        User user = getCurrentUser();
        ticketDto.setOwner(userConverter.toUserDto(user));
        Ticket ticket = ticketRepo.save(ticketConverter.toTicket(ticketDto));
        ticket.setOwner(user);
        mailService.sendMailToUser(user, ticket);
        getHistory(ticket, null, null);
        return ticket;
    }

    @Override
    public Ticket getTicketById(Long ticketId) {
        return ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketId));
    }

    @Override
    public TicketDto getTicketDtoById(Long ticketDtoId) {
        return ticketRepo.findById(ticketDtoId).map(ticketConverter::toTicketDto)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketDtoId));
    }

    @Override
    public TicketResponse getTicketList(int pageNo, int pageSize, String sortBy, String orderBy,
                                        Long filter_id, String filter_name, String filter_date,
                                        String filter_urgency, String filter_state, String flag) {

        Pageable pageable = getPageable (pageNo, pageSize, sortBy, orderBy, filter_id, filter_name,
                                         filter_date, filter_urgency, filter_state);
        User user = userProvider.getCurrentUser();
        TicketSpecification specificationFilter = getFilterSpecification(filter_id, filter_name, filter_date, filter_urgency, filter_state);
        TicketSpecification specification = new TicketSpecification();

        if (user.isEmployee()) {
            specification.add(new SearchCriteria("owner", user, Operation.EQUAL));
        }
        if (user.isManager()) {
            if (flag == null){
                specification.add(new SearchCriteria("state", State.NEW, Operation.EQUAL));
            }
            specification.add(new SearchCriteria("owner", user, Operation.EQUAL));
            specification.add(new SearchCriteria("manager", user, Operation.EQUAL));
        }
        if (user.isEngineer()) {
            if (flag == null){
                specification.add(new SearchCriteria("state", State.APPROVED, Operation.EQUAL));
            }
            specification.add(new SearchCriteria("engineer", user, Operation.EQUAL));
        }
        Page<Ticket> page = ticketRepo.findAll(Specification.where(specification).and(specificationFilter), pageable);
        return createResponse(page, user);
    }

    @Override
    public void updateOrChangeAction(Long ticketId, Action action, TicketDto ticketDto) {
        if(action != null){
            changeStatus(ticketId, action);
        }
        if (ticketDto != null){
            updateTicket(ticketId, ticketDto);
        }
    }


    private void changeStatus(Long ticketId, Action action) {
        Ticket ticket = getTicketById(ticketId);
        State oldState = ticket.getState();
        User user = getCurrentUser();
        State newState = stateByTicket.getNewState(ticket, action);
        ticket.setState(newState);
        if (user.isManager() && !(Objects.equals(ticket.getOwner().getId(), user.getId()))) {
            ticket.setManager(user);
        }
        if (user.isEngineer()) {
            ticket.setEngineer(user);
        }
        ticketRepo.save(ticket);
        mailService.sendMailToUser(user, ticket);
        getHistory(ticket, oldState, newState);
    }


    private void updateTicket(Long ticketId, TicketDto ticketDto) {
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketId));
        State oldState = ticket.getState();
        ticketDto.setOwner(userConverter.toUserDto(getCurrentUser()));
        Ticket ticketUpdate = ticketConverter.toTicket(ticketDto);
        ticket.setCategory(ticketUpdate.getCategory());
        ticket.setName(ticketUpdate.getName());
        ticket.setDescription(ticketUpdate.getDescription());
        ticket.setUrgency(ticketUpdate.getUrgency());
        ticket.setDesired(ticketUpdate.getDesired());
        ticket.setState(ticketUpdate.getState());
        ticketRepo.save(ticket);
        mailService.sendMailToUser(ticket.getOwner(), ticket);
        if (oldState != ticketUpdate.getState()) {
            getHistory(ticket, null, null);
        }
    }

    private TicketSpecification getFilterSpecification(Long filter_id, String filter_name, String filter_date,
                                                       String filter_urgency, String filter_state) {
        TicketSpecification specificationFilter = new TicketSpecification();
        if (filter_id != null) {
            specificationFilter.add(new SearchCriteria("id", filter_id, Operation.GTE));
            return specificationFilter;
        }
        if (filter_name != null) {
            specificationFilter.add(new SearchCriteria("name", filter_name, Operation.LIKE));
            return specificationFilter;
        }
        if (filter_date != null) {

            specificationFilter.add(new SearchCriteria("desired", filter_date, Operation.LIKE));
            return specificationFilter;
        }
        if (filter_urgency != null) {
            specificationFilter.add(new SearchCriteria("urgency", urgencyFilter.getUrgency(filter_urgency), Operation.EQUAL));
            return specificationFilter;
        }
        if (filter_state != null) {
            specificationFilter.add(new SearchCriteria("state", stateFilter.getState(filter_state), Operation.EQUAL));
            return specificationFilter;
        }
        return null;
    }

    private Pageable getPageable(int pageNo, int pageSize, String sortBy, String orderBy,
                                 Long filter_id, String filter_name, String filter_date,
                                 String filter_urgency, String filter_state) {
        if (filter_id != null) {
            sortBy = "id";
        }
        if (filter_name != null) {
            sortBy = "name";
        }
        if (filter_date != null) {
            sortBy = "desired";
        }
        if (filter_urgency != null) {
            sortBy = "urgency";
        }
        if (filter_state != null) {
            sortBy = "state";
        }
        Sort sort = (sortBy == null) ? Sort.by("urgency").and(Sort.by("desired").descending()) :
                (orderBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending());

        return PageRequest.of(pageNo, pageSize, sort);
    }

    private TicketResponse createResponse(Page<Ticket> page, User user) {
        List<TicketDto> content = page.getContent().stream()
                .map(ticketConverter::toTicketDto)
                .peek(ticketDto -> ticketDto.setActions(actionsList.getActions(user, ticketDto)))
                .collect(Collectors.toList());
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setContent(content);
        ticketResponse.setPageNo(page.getNumber());
        ticketResponse.setPageSize(page.getSize());
        ticketResponse.setTotalElements(page.getTotalElements());
        ticketResponse.setTotalPages(page.getTotalPages());
        ticketResponse.setLast(page.isLast());
        return ticketResponse;
    }

    private User getCurrentUser() {
        return userProvider.getCurrentUser();
    }

    private void getHistory(Ticket ticket, State oldState, State newState) {
        if (ticket.getState() == State.NEW) {
            historyService.addHistory(ticket, ticket.getOwner(),
                    descriptionCreator.actionCreate(),
                    descriptionCreator.descriptionCreate());
        } else if (ticket.getState() == State.DRAFT) {
            historyService.addHistory(ticket, ticket.getOwner(),
                    descriptionCreator.actionEdit(),
                    descriptionCreator.descriptionEdit());
        } else {
            historyService.addHistory(ticket, getCurrentUser(),
                    descriptionCreator.actionChangeState(),
                    descriptionCreator.descriptionChangeState(oldState.name(), newState.name()));
        }
    }
}
