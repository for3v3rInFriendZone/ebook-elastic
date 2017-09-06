package com.elastic.srb.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import com.elastic.srb.elasticRepository.EbookElasticRepository;
import com.elastic.srb.model.Ebook;
import com.elastic.srb.repository.EbookRepository;
import com.elastic.srb.service.EbookElasticService;

@Service
public class EbookElasticServiceImpl implements EbookElasticService{

	@Autowired
	private EbookElasticRepository bookElastic;
	
	@Autowired
	ElasticsearchOperations operations;
	
	@Override
	public Ebook save(Ebook book) {
		// TODO Auto-generated method stub
		operations.putMapping(Ebook.class);
		return bookElastic.save(book);
	}

	@Override
	public void delete(Ebook book) {
		// TODO Auto-generated method stub
		bookElastic.delete(book);
	}

	@Override
	public Ebook findOne(Long id) {
		// TODO Auto-generated method stub
		return bookElastic.findOne(id);
	}

	@Override
	public Iterable<Ebook> findAll() {
		// TODO Auto-generated method stub
		return bookElastic.findAll();
	}

	@Override
	public List<Ebook> findByTitle(String title) {
		// TODO Auto-generated method stub
		return bookElastic.findByTitle(title);
	}

	@Override
	public List<Ebook> findByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ebook> findByKeywords(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ebook> findByLanguage(String language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ebook> findByText(String text) {
		// TODO Auto-generated method stub
		return bookElastic.findByText(text);
	}

}
