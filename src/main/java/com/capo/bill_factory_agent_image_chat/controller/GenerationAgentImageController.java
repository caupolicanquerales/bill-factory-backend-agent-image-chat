package com.capo.bill_factory_agent_image_chat.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.capo.bill_factory_agent_image_chat.service.ExecutingPromptImageService;
import com.capo.bill_factory_agent_image_chat.utils.SseStreamUtil;

@RestController
@RequestMapping("agent-image")
@CrossOrigin(origins = "${app.frontend.url}")
public class GenerationAgentImageController {
	
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final ExecutingPromptImageService executingImage;
	
	@Value(value="${event.name.image}")
	private String eventName;
	
	public GenerationAgentImageController(ExecutingPromptImageService executingImage) {
		this.executingImage= executingImage;
	}
	
	@GetMapping(path = "/stream-image", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamImageGeneration() {
        return SseStreamUtil.stream(executor, eventName, "Image generation started for prompt",
                () -> executingImage.generateImageAsync());
	}
}
