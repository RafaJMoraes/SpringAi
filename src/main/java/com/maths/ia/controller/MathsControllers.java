package com.maths.ia.controller;


import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/v1/java")
@CrossOrigin(maxAge = 3600,origins = "*")
public class MathsControllers {


    private OpenAiChatClient chatClient;

    public MathsControllers(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("/information")
    public String mathsChat(
            @RequestParam(value = "message", defaultValue = "Quais são as diferenças dos tipos de Java?") String message){
        return chatClient.call(message);
    }

    @GetMapping("/junit")
    public ChatResponse unitTest(
            @RequestParam(value = "message", defaultValue = "Quais melhores anotations do Junit5?") String message){
        return chatClient.call(new Prompt(message));
    }

    @GetMapping("/algorithm")
    public String algorithm(
            @RequestParam(
                    value = "message",
                    defaultValue = "Algoritimos mais performáticos?") String message){
        PromptTemplate promptTemplate = new PromptTemplate("""
                Por favor, me forneça os melhores algoritimos
                para estudar com a linguagem Java, e também um
                roadmap para esse estudo
                """);
        promptTemplate.add("message", message);
        return this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

    /**
     * Stream returning String ("**")
     * */
    @GetMapping("/stream/information")
    public Flux<String> streamInformation(
            @RequestParam(
                    value = "message",
                    defaultValue = "Ultimas atualizações de Java") String message){
        return this.chatClient.stream(message);
    }

    /**
     * Stream returning Prompt(ChatResponse)
     *
     * */
    @GetMapping("/stream/information/2")
    public Flux<ChatResponse> streamInformationResponse(
            @RequestParam(
                    value = "message",
                    defaultValue = "Quais as apis do stream do Java?") String message){
        return this.chatClient.stream(new Prompt(message));
    }


}
