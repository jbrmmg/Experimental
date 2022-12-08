package com.jbr.exp.tfl;

import com.jbr.exp.tfl.manage.StopCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TflApplication {

	@Autowired
	StopCalculator stopCalculator;

	@EventListener(ContextRefreshedEvent.class)
	public void handleContextStartedEvent(ContextRefreshedEvent ctxStartEvt) {
		stopCalculator.initialise();
	}

	public static void main(String[] args) {
		SpringApplication.run(TflApplication.class, args);
	}
}
