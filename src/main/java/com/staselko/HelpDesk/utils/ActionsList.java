package com.staselko.HelpDesk.utils;

import com.staselko.HelpDesk.model.dto.TicketDto;
import com.staselko.HelpDesk.model.entity.Ticket;
import com.staselko.HelpDesk.model.entity.User;
import com.staselko.HelpDesk.model.enums.Action;
import com.staselko.HelpDesk.model.enums.State;
import com.staselko.HelpDesk.model.exception.ResourceNotFoundException;
import com.staselko.HelpDesk.repository.FeedbackRepo;
import com.staselko.HelpDesk.repository.TicketRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ActionsList {
    private final FeedbackRepo feedbackRepo;
    private final TicketRepo ticketRepo;

    public List<String> getActions(User user, TicketDto ticketDto) {
        return mapState(ticketDto).get(getKey(user, ticketDto)).get(State.valueOf(ticketDto.getState()));
    }

    private Map<String, EnumMap<State, List<String>>> mapState(TicketDto ticketDto) {
        Ticket ticket = ticketRepo.findById(ticketDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketDto.getId()));
        Map<String, EnumMap<State, List<String>>> actions = new HashMap<>();

        EnumMap<State, List<String>> enumMapOwner = new EnumMap<>(State.class);
        enumMapOwner.put(State.DRAFT, Arrays.asList(Action.SUBMIT.getCode(), Action.CANCEL.getCode()));
        enumMapOwner.put(State.DECLINED, Arrays.asList(Action.SUBMIT.getCode(), Action.CANCEL.getCode()));

        EnumMap<State, List<String>> enumMapManager = new EnumMap<>(State.class);
        enumMapManager.put(State.NEW, Arrays.asList(Action.APPROVE.getCode(), Action.DECLINE.getCode(), Action.CANCEL.getCode()));

        EnumMap<State, List<String>> enumMapEngineer = new EnumMap<>(State.class);
        enumMapEngineer.put(State.APPROVED, Arrays.asList(Action.ASSIGN_TO_ME.getCode(), Action.CANCEL.getCode()));
        enumMapEngineer.put(State.IN_PROGRESS, Collections.singletonList(Action.DONE.getCode()));


        if (feedbackRepo.findAllByTicket(ticket).isEmpty()) {
            enumMapOwner.put(State.DONE, Collections.singletonList(Action.LEAVE_FEEDBACK.getCode()));
        }else {
            enumMapOwner.put(State.DONE, Collections.singletonList(Action.VIEW_FEEDBACK.getCode()));
            enumMapEngineer.put(State.DONE, Collections.singletonList(Action.VIEW_FEEDBACK.getCode()));
        }

        actions.put("owner", enumMapOwner);
        actions.put("manager", enumMapManager);
        actions.put("engineer", enumMapEngineer);
        return actions;
    }

    private String getKey(User user, TicketDto ticketDto) {
        if (user.isEmployee()) {
            return "owner";
        } else if (user.isManager()) {
            if (ticketDto.getOwner().getId().equals(user.getId())) {
                return "owner";
            } else {
                return "manager";
            }
        }
        return "engineer";
    }
}

