package com.elastic.srb.elasticRepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.elastic.srb.dto.EbookDTO;

@Repository
public interface EbookElasticRepository extends ElasticsearchRepository<EbookDTO, Long>{
	
}
