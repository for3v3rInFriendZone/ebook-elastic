package com.elastic.srb;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.elastic.srb.service.EbookElasticService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrbApplication.class)
public class SrbApplicationTests {
	
	@Autowired
    private EbookElasticService bookService;

    @Autowired
    private ElasticsearchTemplate esTemplate;
    



}
