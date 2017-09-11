package com.elastic.srb.service;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.elastic.srb.dto.SearchDTO;
import com.elastic.srb.model.Ebook;
import com.elastic.srb.model.Language;

public interface EbookElasticService {

	Ebook save(Ebook book);

	void delete(Ebook book);

	Ebook findOne(Long id);

	Iterable<Ebook> findAll();

	List<Ebook> findByTitle(String title);
	
	List<Ebook> findByAuthor(String author);
    
    List<Ebook> findByKeywords(String keyword);
    
    List<Ebook> findByLanguage(Language language);
    
    List<Ebook> findByText(String text);
    
    List<Ebook> findByFields (List<SearchDTO> searchModel);
}
