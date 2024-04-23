package com.maths.ia.service;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.openai.OpenAiAudioTranscriptionClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OpenAIService {


    @Value("${spring.ai.openai.api-key}")
    private String privateKey;

    protected OpenAiAudioTranscriptionClient transcriptionClient;

    @PostConstruct
    public void init() {
        OpenAiAudioApi openAiAudioApi = new OpenAiAudioApi(privateKey);
        this.transcriptionClient = new OpenAiAudioTranscriptionClient(openAiAudioApi);
    }


    public String textTranslationByFile(File file) {

        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .withTemperature(0f)
                .build();

        var audioFile = new FileSystemResource(file);

        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        AudioTranscriptionResponse response = transcriptionClient.call(transcriptionRequest);
        return response.getResults().get(0).getOutput();
    }


}
