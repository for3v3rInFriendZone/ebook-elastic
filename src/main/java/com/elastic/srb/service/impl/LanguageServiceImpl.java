package com.elastic.srb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elastic.srb.model.Language;
import com.elastic.srb.repository.LanguageRepository;
import com.elastic.srb.service.LanguageService;


@Service
public class LanguageServiceImpl implements LanguageService{

	@Autowired
	LanguageRepository langRepo;
	
	@Override
	public Language save(Language lang) {
		// TODO Auto-generated method stub
		return langRepo.save(lang);
	}

	@Override
	public Language findOne(Long id) {
		// TODO Auto-generated method stub
		return langRepo.findOne(id);
	}

	@Override
	public Iterable<Language> findAll() {
		// TODO Auto-generated method stub
		return langRepo.findAll();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		langRepo.delete(id);
	}

	@Override
	public void delete(Language lang) {
		// TODO Auto-generated method stub
		langRepo.delete(lang);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		langRepo.deleteAll();
	}

}
