package com.thescientist.stellardiary;

import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class Diary {

    String name;
    List<Entry> entries;
    LocalDate startTime;

    public Diary(String name) {
        this.name = name;
        this.startTime = LocalDate.now();
        entries = new ArrayList<>();
    }

    public boolean newEntry(Entry newEntry) {
        return entries.add(newEntry);
    }

    public List<Entry> getEntries(LocalDate date) {
        return entries.stream()
                .filter(d -> d.getTimestamp().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }
}
