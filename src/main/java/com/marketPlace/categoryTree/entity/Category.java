package com.marketPlace.categoryTree.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @ManyToMany
    @JoinTable(
            name = "category_parent",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private Set<Category> parents;

    @ManyToMany(mappedBy = "parents")
    private Set<Category> children;

    @Override
    public String toString() {
        return "Category{" +
                "id: " + id + "}";
    }

}
