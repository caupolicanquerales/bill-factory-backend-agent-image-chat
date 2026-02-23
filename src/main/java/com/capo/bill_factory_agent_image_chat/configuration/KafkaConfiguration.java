package com.capo.bill_factory_agent_image_chat.configuration;

import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import com.capo.bill_factory_agent_image_chat.service.ImageBridge;

import kafkaEvents.PromptGeneratedEvent;


@Configuration
public class KafkaConfiguration {
	
	private final ImageBridge imageBridge;
	
	public KafkaConfiguration(ImageBridge imageBridge) {
		this.imageBridge= imageBridge;
	}
	
	@Bean
    public Consumer<Message<PromptGeneratedEvent>> promptConsumer() {
        return prompt -> {
            System.out.println("Processing prompt via Binding: " + prompt.getPayload().getPrompt());
            imageBridge.publish(prompt.getPayload().getPrompt());
        };
    }
}
