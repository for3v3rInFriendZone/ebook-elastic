package com.elastic.srb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.tomcat.util.codec.binary.Base64;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.ToXContent.Params;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;

import com.elastic.srb.dto.EbookDTO;
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

		Iterable<EbookDTO> books = ebookElastic.findAll();
		List<EbookDTO> returnBooksDTO = new ArrayList<EbookDTO>();
		List<Ebook> returnBooks = new ArrayList<Ebook>();
		books.forEach(returnBooksDTO::add);
		for(int i=0; i<returnBooksDTO.size(); i++) {
			returnBooks.add(ebookSer.toEbook(returnBooksDTO.get(i)));
		}
		
		return new ResponseEntity<List<Ebook>>(returnBooks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Ebook> getBook(@PathVariable Long id) {

		Ebook ebook = ebookSer.toEbook(ebookElastic.findOne(id));
		
		return new ResponseEntity<Ebook>(ebook, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<List<Ebook>> searchBook(@RequestBody List<SearchDTO> searchModel) {
		
		return new ResponseEntity<List<Ebook>>(ebookElastic.findByFields(searchModel), HttpStatus.OK);
		
		
		/*List<QueryBuilder> qbList = new ArrayList<QueryBuilder>();
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
		System.out.println("Query---->  " + bqb.toString());*/
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
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Ebook> uploadPdf(@RequestParam(value = "file") MultipartFile pdf) throws IOException {
		
		Ebook newBook = ebookSer.uploadPDF(pdf);
		return new ResponseEntity<Ebook>(newBook, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Ebook> editBook(@PathVariable("id") Long id, @RequestBody Ebook ebook) throws IOException {

		return new ResponseEntity<Ebook>(ebookSer.update(id, ebook), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Ebook> saveBook(@RequestBody Ebook ebook) throws IOException {
		
		EbookDTO newBook = ebookElastic.save(ebookSer.toEbookDTO(ebookSer.save(ebook)));
		return new ResponseEntity<Ebook>(ebookSer.toEbook(newBook), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Ebook> deleteBook(@PathVariable Long id) {

		ebookElastic.delete(ebookElastic.findOne(id));
		return new ResponseEntity<Ebook>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/all", method = RequestMethod.DELETE)
	public ResponseEntity<List<Ebook>> deleteAllBooks() throws IOException {
		
		ebookElastic.deleteAll();
		return new ResponseEntity<List<Ebook>>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadBook(@PathVariable Long id) {

		Ebook ebook = ebookSer.toEbook(ebookElastic.findOne(id));
		
		File file = new File("C:/Users/Marko.STRISKO/git/ebook-elastic/src/main/resources/static/assets/PdfStorage/" + ebook.getFilename());
		
		try {
	        FileSystemResource fileResource = new FileSystemResource(file);

	        byte[] base64Bytes = Base64.encodeBase64(IOUtils.toByteArray(fileResource.getInputStream()));

	        return ResponseEntity.ok().body(base64Bytes);
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public ResponseEntity<List<EbookDTO>> getBooksTest() {

		Iterable<EbookDTO> books = ebookElastic.findAll();
		List<EbookDTO> returnBooks = new ArrayList<EbookDTO>();
		books.forEach(returnBooks::add);
		
		return new ResponseEntity<List<EbookDTO>>(returnBooks, HttpStatus.OK);
	}
	
	
}
