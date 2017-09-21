package com.elastic.srb.service.impl;

import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.elastic.srb.dto.EbookDTO;
import com.elastic.srb.dto.SearchDTO;
import com.elastic.srb.elasticRepository.EbookElasticRepository;
import com.elastic.srb.model.Ebook;
import com.elastic.srb.model.Language;
import com.elastic.srb.repository.EbookRepository;
import com.elastic.srb.service.EbookElasticService;
import com.elastic.srb.service.EbookService;

@Service
public class EbookElasticServiceImpl implements EbookElasticService {

	@Autowired
	private EbookElasticRepository bookElastic;
	
	@Autowired
	private EbookService ebookSer;

	@Autowired
	ElasticsearchOperations operations;

	@Override
	public EbookDTO save(EbookDTO book) {
		// TODO Auto-generated method stub
		operations.putMapping(EbookDTO.class);
		return bookElastic.save(book);
	}

	@Override
	public void delete(EbookDTO book) {
		// TODO Auto-generated method stub
		bookElastic.delete(book);
	}

	@Override
	public EbookDTO findOne(Long id) {
		// TODO Auto-generated method stub
		return bookElastic.findOne(id);
	}

	@Override
	public Iterable<EbookDTO> findAll() {
		// TODO Auto-generated method stub
		return bookElastic.findAll();
	}

	@Override
	public List<Ebook> findByFields(List<SearchDTO> searchModel) {

		List<QueryBuilder> qbList = new ArrayList<QueryBuilder>();

		/*for (SearchDTO sdto : searchModel) {
			if (sdto.getLanguage().equals(Boolean.TRUE)) {
				
				qbList.add(
						QueryBuilders.nestedQuery("language", QueryBuilders.matchQuery(sdto.getField(), sdto.getValue())
								.operator(AND).fuzziness(Fuzziness.TWO).prefixLength(2)));
			} else {
				qbList.add(QueryBuilders.matchQuery(sdto.getField(), sdto.getValue()).operator(AND)
						.fuzziness(Fuzziness.TWO).prefixLength(2));
			}

		}*/

		for (SearchDTO sdto : searchModel) {
			qbList.add(QueryBuilders.matchQuery(sdto.getField(), sdto.getValue()).operator(AND)
					.fuzziness(Fuzziness.TWO).prefixLength(2));
		}
		
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();

		for (int i = 0; i < searchModel.size(); i++) {
			if (searchModel.get(i).getOperator().equals("AND")) {
				bqb.must(qbList.get(i));
			} else {
				bqb.should(qbList.get(i));
			}
		}

		SearchQuery sq = new NativeSearchQuery(bqb);
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(bqb).withHighlightFields(new HighlightBuilder.Field("text")).build();
		
		Page<EbookDTO> returnPage = operations.queryForPage(searchQuery, EbookDTO.class, new SearchResultMapper(){

			@Override
			public <T> AggregatedPage<T> mapResults(SearchResponse arg0, Class<T> arg1, Pageable arg2) {
				List<EbookDTO> chunk = new ArrayList<EbookDTO>();
	            for (SearchHit searchHit : arg0.getHits()) {
	                if (arg0.getHits().getHits().length <= 0) {
	                    return null;
	                }
	                EbookDTO book = new EbookDTO();
	                book.setId(Long.parseLong(searchHit.getId()));
	                if(searchHit.getHighlightFields().get("text") != null) {
	                	book.setHighlight(searchHit.getHighlightFields().get("text").fragments()[0].toString());
	                }
	                chunk.add(book);
	            }
	            if (chunk.size() > 0) {
	                return new AggregatedPageImpl<T>((List<T>) chunk);
	            }
	            return null;
			}
			
		});

	//	List<EbookDTO> returnBooksDTO = operations.queryForList(searchQuery, EbookDTO.class);
		
		List<Ebook> returnBooks = new ArrayList<Ebook>();

		if(returnPage != null) {
			for(int i=0; i<returnPage.getContent().size(); i++) {
				returnBooks.add(ebookSer.toEbook(returnPage.getContent().get(i)));
			}
		}
		
		return returnBooks;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		bookElastic.deleteAll();
	}

}
