package com.elastic.srb.service.impl;

import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.elastic.srb.dto.SearchDTO;
import com.elastic.srb.elasticRepository.EbookElasticRepository;
import com.elastic.srb.model.Ebook;
import com.elastic.srb.model.Language;
import com.elastic.srb.repository.EbookRepository;
import com.elastic.srb.service.EbookElasticService;

@Service
public class EbookElasticServiceImpl implements EbookElasticService {

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
	public List<Ebook> findByText(String text) {
		// TODO Auto-generated method stub
		return bookElastic.findByText(text);
	}

	@Override
	public List<Ebook> findByFields(List<SearchDTO> searchModel) {

		List<QueryBuilder> qbList = new ArrayList<QueryBuilder>();

		for (SearchDTO sdto : searchModel) {
			if (sdto.getLanguage().equals(Boolean.TRUE)) {
				
				qbList.add(
						QueryBuilders.nestedQuery("language", QueryBuilders.matchQuery(sdto.getField(), sdto.getValue())
								.operator(AND).fuzziness(Fuzziness.TWO).prefixLength(2)));
			} else {
				qbList.add(QueryBuilders.matchQuery(sdto.getField(), sdto.getValue()).operator(AND)
						.fuzziness(Fuzziness.TWO).prefixLength(2));
			}

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

		List<Ebook> returnBooks = operations.queryForList(sq, Ebook.class);

		return returnBooks;
	}

	@Override
	public Page<Ebook> findByLanguageName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return bookElastic.findByLanguageName(name, pageable);
	}

}
