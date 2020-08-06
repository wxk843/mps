package com.cesske.mps.controller.file;


import com.cesske.mps.constants.CommonConst;
import com.cesske.mps.model.ServiceResponse;
import com.cesske.mps.service.file.FileService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = CommonConst.API_PATH_VERSION_1+"/file")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;
    /**
     * 文件上传接口
     * @return
     */
    @ApiOperation(value = "单文件上传",notes = "单文件上传")
    @RequestMapping(value = "/upload",method = RequestMethod.POST,headers = "content-type=multipart/form-data")
    @ResponseBody
    public ServiceResponse uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("mail Test!+++++++++++++++++=============================");
        System.out.println(file.toString());
        if (null == file) {
            return ServiceResponse.createFailResponse("",0,"上传失败，无法找到文件！");
        }
        // BMP、JPG、JPEG、PNG、GIF
        String fileName = file.getOriginalFilename().toLowerCase();
        if (!fileName.endsWith(".bmp") && !fileName.endsWith(".jpg")
                && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")
                && !fileName.endsWith(".gif")) {
            return ServiceResponse.createFailResponse("",0,"上传失败，请选择BMP、JPG、JPEG、PNG、GIF文件！");
        }
        return fileService.uploadFile(file);
    }

    @ApiOperation(value = "多文件上传",notes = "多文件上传")
    @RequestMapping(value = "/multiImport", method = RequestMethod.POST,headers = "content-type=multipart/form-data")
    @ResponseBody
    public Map<String, Object> multiImport(@RequestParam("file") MultipartFile[] file) {
        Map<String, Object> result = new HashMap<String, Object>();
        System.out.println(file.length);
        for (MultipartFile multipartFile:file) {
            System.out.println("文件"+multipartFile.getOriginalFilename());
            fileService.uploadFile(multipartFile);

        }
        return result;
    }

//    @PostMapping(value = "/uploadFile", consumes = "multipart/*", headers = "content-type=multipart/form-data")
//    @ApiOperation(value = "上传", notes = "上传")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "form", name = "file", value = "文件对象", required = true, dataType = "__file"),
//            @ApiImplicitParam(paramType = "form", name = "files", value = "文件数组", allowMultiple = true, dataType = "__file")
//    })
//    public String uploadFiles(@ApiParam(value = "图片", required = true) MultipartFile[] files) {
//
//    for(MultipartFile file : files) {
//        if (!file.isEmpty()) {
//            if (file.getContentType().contains("image")) {
//                try {
//                    String temp = "images" + File.separator + "upload" + File.separator;
//                    // 获取图片的文件名
//                    String fileName = file.getOriginalFilename();
//                    // 获取图片的扩展名
//                    String extensionName = fileName.substring(fileName.indexOf("."));
//                    // 新的图片文件名 = 获取时间戳+"."图片扩展名
//                    String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
//                    // 数据库保存的目录
//                    String datdDirectory = temp.concat(String.valueOf(1)).concat(File.separator);
//                    // 文件路径
//                    String filePath ="D://java/mps/upload/";
//
//                    File dest = new File(filePath, newFileName);
//                    if (!dest.getParentFile().exists()) {
//                        dest.getParentFile().mkdirs();
//                    }
//                    // 上传到指定目录
//                    file.transferTo(dest);
//                } catch (Exception e) {
//                    return "上传失败";
//                }
//            }
//        }
//    }
//    return "上传成功";
//
//}

}
