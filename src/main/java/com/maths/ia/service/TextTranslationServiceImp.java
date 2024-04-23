package com.maths.ia.service;

import com.maths.ia.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class TextTranslationServiceImp implements TextTranslationService {

    @Autowired
    private OpenAIService openAIService;

    @Override
    public String textTranslationByFile(File file) throws Exception {
        return openAIService.textTranslationByFile(file);
    }

    @Override
    public String textTranslation(String fileBase64, String type) throws Exception {
        File file = FileUtils.createFileWithBase64(fileBase64,type);
        return openAIService.textTranslationByFile(file);
    }
}
