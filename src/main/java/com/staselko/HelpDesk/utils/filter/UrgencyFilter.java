package com.staselko.HelpDesk.utils.filter;

import com.staselko.HelpDesk.model.enums.Urgency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrgencyFilter {
    public Urgency getUrgency(String name) {
        if (name.toLowerCase().startsWith("l")) {
            return Urgency.LOW;
        }
        if (name.toLowerCase().startsWith("a")) {
            return Urgency.AVERAGE;
        }
        if (name.toLowerCase().startsWith("h")) {
            return Urgency.HIGH;
        }
        if (name.toLowerCase().startsWith("c")) {
            return Urgency.CRITICAL;
        }
        return null;
    }
}