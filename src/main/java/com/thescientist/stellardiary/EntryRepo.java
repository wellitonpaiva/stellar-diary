package com.thescientist.stellardiary;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepo extends MongoRepository<Entry, String> {

}
