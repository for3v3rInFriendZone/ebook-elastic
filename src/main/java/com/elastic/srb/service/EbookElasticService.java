package com.elastic.srb.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.elastic.srb.dto.SearchDTO;
import com.elastic.srb.model.Ebook;

public interface EbookElasticService {

	Ebook save(Ebook book);

	void delete(Ebook book);

	Ebook findOne(Long id);

	Iterable<Ebook> findAll();

	List<Ebook> findByTitle(String title);
	
	List<Ebook> findByAuthor(String author);
    
    List<Ebook> findByKeywords(String keyword);
    
    Page<Ebook> findByLanguageName(String name, Pageable pageable);
    
    List<Ebook> findByText(String text);
    
    List<Ebook> findByFields (List<SearchDTO> searchModel);
}
