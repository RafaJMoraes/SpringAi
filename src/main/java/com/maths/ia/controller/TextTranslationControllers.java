package com.maths.ia.controller;


import com.maths.ia.controller.dto.FileBase64Dto;
import com.maths.ia.service.OpenAIService;
import com.maths.ia.service.TextTranslationService;
import com.maths.ia.service.TextTranslationServiceImp;
import com.maths.ia.utils.FileUtils;
import org.json.HTTP;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

@RestController
@RequestMapping("api/v1/text-translation")
@CrossOrigin(maxAge = 3600,origins = "*")
public class TextTranslationControllers {


    @Autowired
    private TextTranslationService service;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity translation(
            @RequestParam(value = "file", required = true) MultipartFile file
            ) throws Exception {


        File newFile = FileUtils.createEmptyFile("ogg");
        try (OutputStream os = new FileOutputStream(newFile)) {
            os.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.textTranslationByFile(newFile));
    }

    @PostMapping(value = "/filebase64")
    public ResponseEntity translationWithBase64(
            @RequestBody FileBase64Dto file
    ) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(service.textTranslation(file.getFileBase64(), "ogg"));
    }


}
