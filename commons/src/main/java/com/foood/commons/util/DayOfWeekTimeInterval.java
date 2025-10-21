package com.foood.commons.util;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

@Getter
@Setter
public class DayOfWeekTimeInterval {
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;

    public DayOfWeekTimeInterval(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isInTimeInterval(Instant instant) {
        DayOfWeek instantDayOfWeek = DayOfWeek.from(instant.atOffset(ZoneOffset.from(ZoneId.systemDefault().getRules().getOffset(instant))));
        LocalTime instantTime = LocalTime.ofInstant(instant, ZoneId.systemDefault());

        if (instantDayOfWeek.equals(this.dayOfWeek)) {
            if (startTime.equals(instantTime) || endTime.equals(instantTime))
                return true;
            return instantTime.isAfter(startTime) && instantTime.isBefore(endTime);
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayOfWeekTimeInterval that)) return false;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && dayOfWeek == that.dayOfWeek;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, dayOfWeek);
    }
}
