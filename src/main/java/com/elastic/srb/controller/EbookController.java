package com.elastic.srb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.ToXContent.Params;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;

import com.elastic.srb.dto.SearchDTO;
import com.elastic.srb.model.Ebook;
import com.elastic.srb.model.Language;
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
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<List<Ebook>> searchBook(@RequestBody List<SearchDTO> searchModel) {
		
		
		List<QueryBuilder> qbList = new ArrayList<QueryBuilder>();
		//QueryBuilder qb1 = QueryBuilders.simpleQueryStringQuery("{'field' : {'title' : 'steven'}}");
		//QueryBuilder qb = QueryBuilders.boolQuery().must(qb1);
		
		for(SearchDTO sdto : searchModel){
			qbList.add(QueryBuilders.matchQuery(sdto.getField(), sdto.getValue()).operator(AND).fuzziness(Fuzziness.TWO).prefixLength(2));
		}
		
		//QueryBuilder q1 = QueryBuilders.matchQuery(searchModel.get(0).getField(), searchModel.get(0).getValue()).operator(AND).fuzziness(Fuzziness.TWO).prefixLength(2);
		//QueryBuilder q2 = QueryBuilders.matchQuery(searchModel.get(1).getField(), searchModel.get(1).getValue()).operator(AND).fuzziness(Fuzziness.TWO).prefixLength(2);
		
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		
		for(int i = 0; i < searchModel.size(); i++){
			if(searchModel.get(i).getOperator().equals("AND")){
				bqb.must(qbList.get(i));
			} else {
				bqb.should(qbList.get(i));
			}
		}
		
		//.must(q1).filter(q2);
		SearchQuery sq = new NativeSearchQuery(bqb);
		
		List<Ebook> returnBooks = operations.queryForList(sq, Ebook.class);
		System.out.println("Query---->  " + bqb.toString());
		//QueryBuilder qb2 = QueryBuilders.simpleQueryStringQuery("{'field' : {'title' : 'dark'}}");
		
		/*BoolQueryBuilder qb = QueryBuilders.boolQuery();
		
		if(searchModel != null && !searchModel.isEmpty()) {
			for(int i = 0; i < searchModel.size(); i++) {
				if(searchModel.get(i).getOperator().equals("AND")){
					qb.must(QueryBuilders.simpleQueryStringQuery("{'field' : {'"+ searchModel.get(i).getField() +"' : '" +searchModel.get(i).getValue() + "'}}"));
				}else {
					qb.should(QueryBuilders.simpleQueryStringQuery("{'field' : {'"+ searchModel.get(i).getField() +"' : '" +searchModel.get(i).getValue() + "'}}"));
				}
			}
		}
	//	QueryBuilder asdsd = QueryBuilders.queryStringQuery(queryString);
		
		System.out.println("Query---->  " + qb.toString());
		
		SearchQuery sq = new NativeSearchQuery(qb);
		response = operations.queryForList(sq, Ebook.class);*/
		/*List<String> listOfParams = new ArrayList<String>();
		if(params != null && params.length != 0) {
			listOfParams = Arrays.asList(params);
		}
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				  .withQuery(matchQuery("title", text)
				  .operator(AND)
				  .fuzziness(Fuzziness.TWO)
				  .prefixLength(2))
				  .build();
		List<Ebook> returnBooks = operations.queryForList(searchQuery, Ebook.class);
		
		QueryBuilder matchSpecificFieldQuery= QueryBuilders.queryStringQuery("{'bool' : {'must' : {'field' : {'name' : 'dark'}}}}");
		SearchQuery sq = new NativeSearchQuery(matchSpecificFieldQuery);
		List<Ebook> returnBooks = operations.queryForList(sq, Ebook.class);
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				  .withQuery(matchQuery("title", text)
				  .operator(AND)
				  .fuzziness(Fuzziness.TWO)
				  .prefixLength(2))
				  .build();
		
		StringBuilder stringQuery = new StringBuilder();
		stringQuery.append(" 'query': {'query_string' : {'query': '(saerch~1 algorithm~1) AND (grant ingersoll)  OR (tom morton)','fields': ['_all', 'summary^2]}}");
		
		QueryBuilder matchSpecificFieldQuery= QueryBuilders.simpleQueryStringQuery("{'bool' : {'must' : {'field' : {'name' : " + text + "}}}}");
		
		
		QueryBuilder qb1 = QueryBuilders.simpleQueryStringQuery("'query': {'fuzzy' : {'title' : {'value' :" + text + ",'fuzziness' :2, 'prefix_length' : 2}}}");
		//QueryBuilder qb2 = QueryBuilders.simpleQueryStringQuery("{'field' : {'title' : 'dark'}}");
		
		QueryBuilder q1 = QueryBuilders.fuzzyQuery("author", text).fuzziness(Fuzziness.TWO).prefixLength(2);
		QueryBuilder q2 = QueryBuilders.fuzzyQuery("title", "dar").fuzziness(Fuzziness.TWO).prefixLength(2);
		
		QueryBuilder qb = QueryBuilders.boolQuery().must(qb1).must(q2);
		SearchQuery sq = new NativeSearchQuery(qb);
		List<Ebook> returnBooks = operations.queryForList(sq, Ebook.class);
		//Iterable<Ebook> books = ebookElastic.findByLanguage(lang);
		
				
		
		//books.forEach(returnBooks::add);
		
		*/
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
