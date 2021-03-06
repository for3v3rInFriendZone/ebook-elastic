package com.elastic.srb.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.elastic.srb.dto.EbookDTO;
import com.elastic.srb.model.Ebook;

public interface EbookService {

	public Ebook save(Ebook ebook) throws IOException;
	
	public Ebook update(Long id, Ebook ebook);
	
	public Ebook findOne(Long id);
	
	public Iterable<Ebook> findAll();
	
	public void delete(Long id);
	
	public void delete(Ebook ebook);
	
	public void deleteAll();
	
	public Ebook uploadPDF(MultipartFile pdf);
	
	public Ebook toEbook(EbookDTO ebookDto);
	
	public EbookDTO toEbookDTO(Ebook ebook);
	
}
