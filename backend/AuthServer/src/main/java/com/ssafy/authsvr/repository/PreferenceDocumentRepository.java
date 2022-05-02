package com.ssafy.authsvr.repository;

import com.ssafy.authsvr.entity.PreferenceDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreferenceDocumentRepository extends MongoRepository<PreferenceDocument, ObjectId> {

    Integer countAllBy();
}
