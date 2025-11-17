package com.uninter.vidaplus.schedule.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TimeSlot {
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(DayOfWeek dayOfWeekTimeSlot, LocalTime startTimeTimeSlot, LocalTime endTimeTimeSlot) {
        this.dayOfWeek = dayOfWeekTimeSlot;
        this.startTime = startTimeTimeSlot;
        this.endTime = endTimeTimeSlot;
    }
}
