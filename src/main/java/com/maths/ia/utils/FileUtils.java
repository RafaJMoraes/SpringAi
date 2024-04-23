package com.maths.ia.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class FileUtils {



    public static File createEmptyFile(String type) throws IOException {
        Path path = Path.of("temp");

        // Verifica se o diretório não existe e cria se necessário
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        return File.createTempFile("temp", "." + type,
                new File(path.toUri()));
    }

    public static File createFileWithBase64(String fileBase64, String type) throws IOException {
        byte[] fileByte = Base64.getDecoder().decode(fileBase64);
        File file = createEmptyFile(type);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileByte);
        }
        return file;
    }
}
