package com.elastic.srb.config;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
public class ElasticConfig {
	
	@Bean
	NodeBuilder nodeBuilder() {
		return new NodeBuilder();
	}
	
	@Bean
	ElasticsearchOperations elasticsearchTemplate() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("temp-elastic", Long.toString(System.nanoTime()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Settings.Builder elasticSearchSettings = 
				Settings.settingsBuilder()
				.put("http.enabled", "true")
				.put("index.number_of_shards", "1")
				.put("path.data", new File(tempFile, "data").getAbsolutePath())
				.put("path.logs", new File(tempFile, "logs").getAbsolutePath())
				.put("path.work", new File(tempFile, "work").getAbsolutePath())
				.put("path.home", tempFile);
		
		return new ElasticsearchTemplate(nodeBuilder()
				.local(true)
				.settings(elasticSearchSettings)
				.node()
				.client());
	}

}
