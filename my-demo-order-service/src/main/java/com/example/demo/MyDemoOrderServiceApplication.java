package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.OrderWebConfiguration;
import com.example.demo.order.config.OrderConfiguration;


import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;

@SpringBootApplication
@Configuration
@Import({OrderWebConfiguration.class, OrderConfiguration.class, TramEventsPublisherConfiguration.class, TramCommandProducerConfiguration.class,
		TramJdbcKafkaConfiguration.class })
@ComponentScan
public class MyDemoOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDemoOrderServiceApplication.class, args);
	}
	@Bean
	  public ChannelMapping channelMapping() {
	    return DefaultChannelMapping.builder().build();
	  }
	
}
