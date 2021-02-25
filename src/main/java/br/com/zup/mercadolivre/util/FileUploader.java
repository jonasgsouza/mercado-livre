package br.com.zup.mercadolivre.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploader {

    List<String> send(List<MultipartFile> files);

}
