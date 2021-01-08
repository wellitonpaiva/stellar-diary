package com.thescientist.stellardiary;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder
@Value
public class Entry {
    @Id
    private String id;

    private String entry;
    private LocalDateTime timestamp;
}
