package com.thescientist.stellardiary;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class DiaryTest {

    @Test
    void newDiary() {
        String name = "Diary";
        Diary sut = new Diary(name);
        assertThat(sut.getName(), is(name));
        assertThat(sut.getEntries(), is(emptyList()));
    }

    @Test
    void diaryNewEntry() {
        Entry newEntry = Entry.builder().build();
        Diary sut = new Diary("diary");
        assertThat(sut.newEntry(newEntry), is(true));
    }

    @Test
    void viewDailyEntries() {
        LocalDate searchDate = LocalDate.of(2021, 1, 1);
        Entry firstEntry = Entry.builder().entry("first entry").timestamp(searchDate.atStartOfDay()).build();
        Entry secondEntry = Entry.builder().entry("second entry").timestamp(searchDate.atStartOfDay()).build();

        Diary sut = new Diary("diary");
        sut.newEntry(firstEntry);
        sut.newEntry(secondEntry);
        sut.newEntry(Entry.builder().entry("another entry").timestamp(now()).build());

        List<Entry> entries = sut.getEntries(searchDate);
        assertThat(entries, is(asList(firstEntry, secondEntry)));
    }
}