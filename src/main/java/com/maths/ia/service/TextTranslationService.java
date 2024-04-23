package com.maths.ia.service;

import java.io.File;

public interface TextTranslationService {

    public String textTranslationByFile(File file) throws Exception;

    public String textTranslation(String fileBase64, String type) throws Exception;
}
