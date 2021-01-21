package com.thescientist.stellardiary;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
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

    @Test
    void viewThisWeekEntries() {
        Entry thisWeekEntry = Entry.builder().entry("this week entry").timestamp(now()).build();
        Entry thisWeekEntry2 = Entry.builder().entry("this week's another entry").timestamp(now()).build();
        Entry lastWeekEntry = Entry.builder().entry("last week entry").timestamp(now().minusWeeks(1)).build();

        Diary sut = new Diary("diary");
        sut.newEntry(thisWeekEntry);
        sut.newEntry(thisWeekEntry2);
        sut.newEntry(lastWeekEntry);
        List<Entry> entries = sut.getWeekEntries();
        assertThat(entries, is(asList(thisWeekEntry, thisWeekEntry2)));
    }

    @Test
    void getStartOfTheWeekWhenSunday() {
        Diary sut = new Diary("diary");
        LocalDate sunday = LocalDate.of(2021, 1, 17);
        LocalDateTime startOfTheWeek = sut.getStartOfTheWeek(sunday);
        assertThat(startOfTheWeek, is(sunday.atStartOfDay()));
    }

    @Test
    void getStartOfTheWeekWhenMonday() {
        Diary sut = new Diary("diary");
        LocalDate monday = LocalDate.of(2021, 1, 18);
        LocalDateTime startOfTheWeek = sut.getStartOfTheWeek(monday);
        assertThat(startOfTheWeek, is(monday.minusDays(1).atStartOfDay()));
    }

    @Test
    void getStartOfTheWeekWhenSaturday() {
        Diary sut = new Diary("diary");
        LocalDate saturday = LocalDate.of(2021, 1, 16);
        LocalDateTime startOfTheWeek = sut.getStartOfTheWeek(saturday);
        assertThat(startOfTheWeek, is(saturday.minusDays(6).atStartOfDay()));
    }


}