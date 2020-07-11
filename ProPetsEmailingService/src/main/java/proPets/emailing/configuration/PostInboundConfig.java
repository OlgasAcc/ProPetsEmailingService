package proPets.emailing.configuration;

import org.springframework.cloud.stream.annotation.EnableBinding;

import proPets.emailing.service.DataExchange;
import proPets.emailing.service.DataExchange2;

@EnableBinding({ DataExchange.class, DataExchange2.class })
public class PostInboundConfig {

}