package com.maths.ia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiAudioTranscriptionClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IaApplicationTests {
	private static final Logger logger = LogManager.getLogger(IaApplicationTests.class);

	@Value("${spring.ai.openai.api-key}")
	private String privateKey;
	@Autowired
	private OpenAiChatClient chatClient;
	@Autowired
	protected OpenAiAudioTranscriptionClient transcriptionClient;

	OpenAiAudioApi.TranscriptResponseFormat responseFormat = OpenAiAudioApi.TranscriptResponseFormat.VTT;
	@Autowired
	private OpenAiAudioTranscriptionClient openAiAudioTranscriptionClient;

	@Test
	void contextLoads() {
		var audioFile = new FileSystemResource("/meusProjetosJava/ia/temp/teste0.ogg");
		OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
				.withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
				.withTemperature(0f)
				.build();
		AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
		AudioTranscriptionResponse response = transcriptionClient.call(transcriptionRequest);

		var result = response.getResults().get(0).getOutput().toLowerCase().contains("teste de transcrição de texto");

		logger.info(result);
		assertThat(result).isTrue();
	}

	@Test
	void textTranscriptionFile01() {
		var openAiAudioApi = new OpenAiAudioApi(privateKey);

		var openAiAudioTranscriptionClient = new OpenAiAudioTranscriptionClient(openAiAudioApi);

		var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
				.withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
				.withTemperature(0f)
				.build();

		var audioFile =  new FileSystemResource("/meusProjetosJava/ia/temp/teste0.ogg");

		AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
		AudioTranscriptionResponse response = transcriptionClient.call(transcriptionRequest);
		var result = response.getResults().get(0).getOutput();
		response.getResults().stream().forEach(System.out::println);
		logger.info("### " +result+" ### ");
		assertThat(result).isEqualTo("Teste de transcrição de texto.");
	}

}
