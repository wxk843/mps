package com.cesske.mps.service.file.impl;

import com.cesske.mps.model.ServiceResponse;
import com.cesske.mps.service.file.FileService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Override
    public ServiceResponse uploadFile(MultipartFile file) {
        String targetFilePath = uploadFolder;
        String saveFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString().replace("-", "")+saveFileName.substring(saveFileName.lastIndexOf("."));
        File targetFile = new File(targetFilePath + File.separator + fileName);
        File saveFile = new File(targetFilePath+ File.separator + saveFileName);
        System.out.println(targetFile);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile));
            out.write(file.getBytes());
            out.flush();
            out.close();
            return ServiceResponse.createSuccessResponse("",saveFile.getName() + "上传成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ServiceResponse.createFailResponse("",0,"上传失败," + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResponse.createFailResponse("",0,"上传失败," + e.getMessage());
        }
    }
}
