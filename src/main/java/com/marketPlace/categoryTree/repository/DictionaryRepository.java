package com.marketPlace.categoryTree.repository;

import com.marketPlace.categoryTree.entity.Category;
import com.marketPlace.categoryTree.entity.Dictionary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    Dictionary findByValue(String value);

}