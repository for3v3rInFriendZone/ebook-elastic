package com.elastic.srb.elasticRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.elastic.srb.model.Ebook;

public interface EbookElasticRepository extends ElasticsearchRepository<Ebook, Long>{
	
    List<Ebook> findByTitle(String title);
    
    List<Ebook> findByAuthor(String author);
    
    List<Ebook> findByKeywords(String keyword);
    
    List<Ebook> findByLanguage(String language);
    
    List<Ebook> findByText(String text);
}
