package com.cesske.mps.service.file;

import com.cesske.mps.model.ServiceResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public ServiceResponse uploadFile(MultipartFile zipFile);
}
