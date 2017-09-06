package com.elastic.srb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;
import com.elastic.srb.model.Ebook;
import com.elastic.srb.service.EbookElasticService;
import com.elastic.srb.service.EbookService;

@RestController
@RequestMapping(value="/book")
public class EbookController {

	@Autowired
	EbookElasticService ebookElastic;
	
	@Autowired
	EbookService ebookSer;
	
	@Autowired
	ElasticsearchOperations operations;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Ebook>> getBooks() {

		Iterable<Ebook> books = ebookElastic.findAll();
		List<Ebook> returnBooks = new ArrayList<Ebook>();
		books.forEach(returnBooks::add);
		
		return new ResponseEntity<List<Ebook>>(returnBooks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Ebook> getBook(@PathVariable Long id) {

		Ebook ebook = ebookElastic.findOne(id);
		return new ResponseEntity<Ebook>(ebook, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/{text}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<Ebook>> searchBook(@PathVariable String text) {
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				  .withQuery(matchQuery("title", text)
				  .operator(AND)
				  .fuzziness(Fuzziness.TWO)
				  .prefixLength(2))
				  .build();
		
		//Iterable<Ebook> books = ebookElastic.findByText(text);
		List<Ebook> returnBooks = operations.queryForList(searchQuery, Ebook.class);
	//	books.forEach(returnBooks::add);
		
		return new ResponseEntity<List<Ebook>>(returnBooks, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Ebook> saveBook(@RequestBody Ebook ebook) throws IOException {
		
		Ebook newBook = ebookElastic.save(ebookSer.save(ebook));
		return new ResponseEntity<Ebook>(newBook, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Ebook> deleteBook(@PathVariable Long id) {

		ebookElastic.delete(ebookElastic.findOne(id));
		return new ResponseEntity<Ebook>(HttpStatus.OK);
	}
}
