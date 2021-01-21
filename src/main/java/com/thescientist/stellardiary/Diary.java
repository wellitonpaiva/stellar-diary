package com.thescientist.stellardiary;

import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Value
public class Diary {

    String name;
    List<Entry> entries;
    LocalDate startTime;

    public Diary(String name) {
        this.name = name;
        this.startTime = now();
        entries = new ArrayList<>();
    }

    public boolean newEntry(Entry newEntry) {
        return entries.add(newEntry);
    }

    public List<Entry> getEntries(LocalDate date) {
        return entries.stream()
                .filter(e -> e.getTimestamp().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    public List<Entry> getWeekEntries() {
        return entries.stream()
                .filter(e -> e.getTimestamp().isAfter(getStartOfTheWeek(now())))
                .collect(Collectors.toList());
    }

    LocalDateTime getStartOfTheWeek(LocalDate date) {
        DayOfWeek dayOfWeek = DayOfWeek.from(date);
        if(dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return date.atStartOfDay();
        } else {
            return date.minusDays(dayOfWeek.getValue()).atStartOfDay();
        }
    }
}
