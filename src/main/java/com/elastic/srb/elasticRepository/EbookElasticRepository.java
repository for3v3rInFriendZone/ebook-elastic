package com.elastic.srb.elasticRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elastic.srb.model.Ebook;
import com.elastic.srb.model.Language;

@Repository
public interface EbookElasticRepository extends ElasticsearchRepository<Ebook, Long>{
	
    List<Ebook> findByTitle(String title);
    
    List<Ebook> findByAuthor(String author);
    
    List<Ebook> findByKeywords(String keyword);
    
    List<Ebook> findByText(String text);
    
    Page<Ebook> findByLanguageName(String name, Pageable pageable);
    
}
