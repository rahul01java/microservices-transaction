package com.example.demo.config;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.demo.customer.CustomerRepository;
import com.example.demo.service.CustomerCommandHandler;
import com.example.demo.service.CustomerService;

@Configuration
@EnableAutoConfiguration
@Import({SagaParticipantConfiguration.class})
public class CustomerConfiguration {

  @Bean
  public CustomerService customerService(CustomerRepository customerRepository) {
    return new CustomerService(customerRepository);
  }

  @Bean
  public CustomerCommandHandler customerCommandHandler(CustomerService customerService) {
    return new CustomerCommandHandler(customerService);
  }

  // TODO Exception handler for CustomerCreditLimitExceededException

  @Bean
  public CommandDispatcher consumerCommandDispatcher(CustomerCommandHandler target,SagaCommandDispatcherFactory dispatcherFactory) {

    return dispatcherFactory.make("customerCommandDispatcher", target.commandHandlerDefinitions());
  }

}
