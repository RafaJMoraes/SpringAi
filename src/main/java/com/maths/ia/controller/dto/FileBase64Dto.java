package com.maths.ia.controller.dto;


import lombok.Data;

@Data
public class FileBase64Dto {
    private String fileBase64;

    public FileBase64Dto(String fileBase64) {
        this.fileBase64 = fileBase64;
    }

    public FileBase64Dto() {
        super();
    }
}
