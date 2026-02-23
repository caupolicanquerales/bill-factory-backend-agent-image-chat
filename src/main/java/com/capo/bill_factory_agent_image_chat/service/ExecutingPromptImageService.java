package com.capo.bill_factory_agent_image_chat.service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ExecutingPromptImageService {
	
	private final ImageModel imageModel;
	private final ImageBridge imageBridge;
	
	@Value(value="${event.name.image}")
	private String eventName;
	
	@Value(value="${image.model.name}")
	private String imageModelName;
	
	public ExecutingPromptImageService(@Qualifier("customOpenAiImageModel") ImageModel imageModel,
			ImageBridge imageBridge) {
		this.imageModel=imageModel;
		this.imageBridge= imageBridge;
	}
	
	
	public CompletableFuture<String> generateImageAsync() {
		return imageBridge.getSink().asFlux()
	            .filter(event -> Objects.nonNull(event))
	            .next() 
	            .map(prompt -> {
	                var options =  OpenAiImageOptions.builder()
	    					.model(imageModelName)
	    					.build();

	                ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
	                ImageResponse response = imageModel.call(imagePrompt);
	                
	                return response.getResult().getOutput().getB64Json();
	            })
	            .toFuture();
	}
}

