package com.ssafy.escapesvr.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer id;

    @NotNull
    @Column(name="store_name")
    private String name;

    @NotNull
    private String largeRegion;

    @NotNull
    private String smallRegion;

    @NotNull
    @Column(length = 1000)
    private String siteUrl;

    //
    @NotNull
    private Integer isClosed;

    private String closedDays;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Theme> themes = new ArrayList<>();


}
