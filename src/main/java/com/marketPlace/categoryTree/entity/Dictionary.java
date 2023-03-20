package com.marketPlace.categoryTree.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "dictionary")
@Data
@NoArgsConstructor
public class Dictionary {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column
    private String language;

    @Column
    private String value;

}