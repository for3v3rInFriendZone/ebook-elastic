package com.elastic.srb.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.elastic.srb.dto.EbookDTO;
import com.elastic.srb.dto.SearchDTO;
import com.elastic.srb.model.Ebook;

public interface EbookElasticService {

	EbookDTO save(EbookDTO book);

	void delete(EbookDTO book);
	
	void deleteAll();

	EbookDTO findOne(Long id);

	Iterable<EbookDTO> findAll();
	
	List<Ebook> findByFields(List<SearchDTO> searchModel);
}
