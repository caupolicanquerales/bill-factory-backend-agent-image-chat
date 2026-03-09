package com.capo.bill_factory_agent_image_chat.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

@Service
public class ImageBridge {
	
	private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer(256, false);

    public void publish(String prompt) {
        Sinks.EmitResult result = sink.tryEmitNext(prompt);
        if (result.isFailure()) {
            System.err.println("ImageBridge emit failed: " + result);
        }
    }

    public Sinks.Many<String> getSink() {
        return sink;
    }
}
