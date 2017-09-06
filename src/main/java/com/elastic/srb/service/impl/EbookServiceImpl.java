package com.elastic.srb.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elastic.srb.model.Ebook;
import com.elastic.srb.repository.EbookRepository;
import com.elastic.srb.service.EbookService;

@Service
public class EbookServiceImpl implements EbookService{

	@Autowired
	EbookRepository ebookRep;
	
	@Override
	public Ebook save(Ebook ebook) throws IOException {
		// TODO Auto-generated method stub
		return ebookRep.save(ebook);
	}

	@Override
	public Ebook update(Long id, Ebook ebook) {
		// TODO Auto-generated method stub
		return ebookRep.save(ebook);
	}

	@Override
	public Ebook findOne(Long id) {
		// TODO Auto-generated method stub
		return ebookRep.findOne(id);
	}

	@Override
	public Iterable<Ebook> findAll() {
		// TODO Auto-generated method stub
		return ebookRep.findAll();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ebookRep.delete(id);
	}

	@Override
	public void delete(Ebook ebook) {
		// TODO Auto-generated method stub
		ebookRep.delete(ebook);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		ebookRep.deleteAll();
	}

}
