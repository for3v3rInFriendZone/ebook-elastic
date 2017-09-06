package com.elastic.srb.service;

import java.io.IOException;

import com.elastic.srb.model.Ebook;

public interface EbookService {

	public Ebook save(Ebook ebook) throws IOException;
	
	public Ebook update(Long id, Ebook ebook);
	
	public Ebook findOne(Long id);
	
	public Iterable<Ebook> findAll();
	
	public void delete(Long id);
	
	public void delete(Ebook ebook);
	
	public void deleteAll();
	
}
