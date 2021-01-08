package com.thescientist.stellardiary;

import org.apache.coyote.Response;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EntryControllerTest {

    @Test
    void addNewEntry() {
        String entry = "new entry";
        Entry expectedEntry = Entry.builder().entry(entry).id("id").build();
        ResponseEntity<Entry> expected = ResponseEntity.created(URI.create("/entry/id")).body(expectedEntry);

        EntryRepo repo = Mockito.mock(EntryRepo.class);
        when(repo.insert(any(Entry.class))).thenReturn(expectedEntry);

        EntryController sut = new EntryController(repo);
        ResponseEntity<Entry> entryResponse = sut.addEntry(entry);
        assertThat(entryResponse, is(expected));
    }

    @Test
    void getEntry(){
        String id = "id";
        Optional<Entry> expected = ofNullable(Entry.builder().id(id).build());

        EntryRepo repo = Mockito.mock(EntryRepo.class);
        when(repo.findById(id)).thenReturn(expected);
        EntryController sut = new EntryController(repo);
        Optional<Entry> entry = sut.getEntry(id);
        assertThat(entry, is(expected));
    }

    @Test
    void getAll() {
        EntryRepo repo = Mockito.mock(EntryRepo.class);
        List<Entry> expected = Collections.singletonList(Entry.builder().build());
        when(repo.findAll()).thenReturn(expected);
        EntryController sut = new EntryController(repo);
        List<Entry> entries = sut.getAllEntries();
        assertThat(entries, is(expected));
    }

    @Test
    void getEntriesByDate() {
        List<Entry> expected = Collections.singletonList(Entry.builder().timestamp(LocalDateTime.now()).build());
        EntryRepo repo = Mockito.mock(EntryRepo.class);
        when(repo.findAll()).thenReturn(expected);
        EntryController sut = new EntryController(repo);
        List<Entry> entries = sut.getEntries(LocalDate.now());
        assertThat(entries, is(expected));
    }

    @Test
    void getEntriesByDateWithNoReturn() {
        EntryRepo repo = Mockito.mock(EntryRepo.class);
        when(repo.findAll()).thenReturn(Collections.singletonList(Entry.builder().timestamp(LocalDateTime.MIN).build()));
        EntryController sut = new EntryController(repo);
        List<Entry> entries = sut.getEntries(LocalDate.now());
        assertThat(entries, is(emptyList()));
    }
}
