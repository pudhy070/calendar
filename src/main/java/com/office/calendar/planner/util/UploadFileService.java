package com.office.calendar.planner.util;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
public class UploadFileService {

    @Value("${calendar.image.upload.dir}")
    private String imageUploadDir;

    @Value("${calendar.image.upload.dir.seperator}")
    private String imageUploadDirSeperator;


    public String upload(String id, MultipartFile file) {
        boolean result = false;

        String fileOriName = file.getOriginalFilename();
        String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."));


        /*
        // Windows
        // String uploadDir = "c:\\calendar\\upload\\" + id;
        // File saveFile = new File(uploadDir + "\\" + uniqueFileName + fileExtension);

        // Ubuntu
        String uploadDir = "/calendar/upload/" + id;
        */

        String uploadDir = imageUploadDir;


        // 새로운 파일 이름 생성
        UUID uuid = UUID.randomUUID();
        String uniqueFileName = uuid.toString().replaceAll("-", "");

        /*
        File saveFile = new File(uploadDir + "/" + uniqueFileName + fileExtension);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }

         */

        File saveFile = new File(uploadDir + uniqueFileName);

        try {
            file.transferTo(saveFile);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result) {
            log.info("FILE UPLOAD SUCCESS!!");
            return uniqueFileName + fileExtension;
        } else {
            log.info("FILE UPLOAD FAIL!!");
            return null;
        }
    }
}