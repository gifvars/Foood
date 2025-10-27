package com.foood.commons_svc.util;

import java.time.Instant;
import java.util.List;

public class OpeningHours {
    private List<DayOfWeekTimeInterval> openingHours;

    public OpeningHours(List<DayOfWeekTimeInterval> openingHours) {
        this.openingHours = openingHours;
    }

    public OpeningHours() {

    }

    public List<DayOfWeekTimeInterval> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<DayOfWeekTimeInterval> openingHours) {
        this.openingHours = openingHours;
    }

    public boolean isOpenAt(Instant instant){
        return openingHours.stream().anyMatch(i -> i.isInTimeInterval(instant));
    }

    public boolean isOpen() {
        return isOpenAt(Instant.now());
    }

}
