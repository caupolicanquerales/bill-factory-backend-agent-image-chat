package com.capo.bill_factory_agent_image_chat.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

@Service
public class ImageBridge {
	
	private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void publish(String prompt) {
        sink.tryEmitNext(prompt);
    }

    public Sinks.Many<String> getSink() {
        return sink;
    }
}
