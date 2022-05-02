package com.ssafy.escapesvr.repository;

import com.ssafy.escapesvr.entity.ThemeReviewDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThemeReviewDocumentRepository extends MongoRepository<ThemeReviewDocument, ObjectId> {
    void deleteByReviewId(Integer id);
}
