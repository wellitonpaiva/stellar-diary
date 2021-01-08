package com.thescientist.stellardiary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EntryController {

    EntryRepo repo;

    @Autowired
    public EntryController(EntryRepo repo) {
        this.repo = repo;
    }

    @PostMapping("/entry")
    public ResponseEntity<Entry> addEntry(@RequestBody String entry) {
        Entry entryAdded = repo.insert(Entry.builder().entry(entry).timestamp(LocalDateTime.now()).build());
        return ResponseEntity.created(URI.create(String.format("/entry/%s", entryAdded.getId()))).body(entryAdded);
    }

    @GetMapping(value = "/entry/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Entry> getEntry(@PathVariable("id") String id) {
        return repo.findById(id);
    }

    @GetMapping(value = "/entry")
    public List<Entry> getAllEntries() {
        return repo.findAll();
    }

    @GetMapping(value = "/entry", params = "date")
    public List<Entry> getEntries(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return repo.findAll().stream()
                .filter(e -> e.getTimestamp().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }
}
