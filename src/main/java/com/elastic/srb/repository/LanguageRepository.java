package com.elastic.srb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.elastic.srb.model.Language;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Long>{

}
