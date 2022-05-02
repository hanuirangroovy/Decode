package com.ssafy.escapesvr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.time.LocalDateTime;

@Document("review")
@Getter
@Setter
@NoArgsConstructor
public class ThemeReviewDocument {

        @Id
        private ObjectId id;

        private Integer reviewId;

        private Integer userId;

        private Integer themeId;

        private Integer rating;

        private String gender;

        private Integer age;

    }

