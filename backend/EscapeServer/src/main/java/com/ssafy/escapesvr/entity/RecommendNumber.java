package com.ssafy.escapesvr.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_number_id")
    private Integer id;

    //
    @NotNull
    private Integer numberIsTwo;

    //
    @NotNull
    private Integer numberIsThree;

    //
    @NotNull
    private Integer numberIsFour;

    //
    @NotNull
    private Integer numberIsFive;

    @OneToOne(mappedBy = "recommendNumber",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Theme theme;

}
