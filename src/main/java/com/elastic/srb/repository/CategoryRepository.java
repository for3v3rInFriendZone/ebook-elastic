package com.elastic.srb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.elastic.srb.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

}
