package com.elastic.srb.service;

import com.elastic.srb.model.Language;

public interface LanguageService {

	public Language save(Language lang);
	
	public Language findOne(Long id);
	
	public Iterable<Language> findAll();
	
	public void delete(Long id);
	
	public void delete(Language lang);
	
	public void deleteAll();
}
