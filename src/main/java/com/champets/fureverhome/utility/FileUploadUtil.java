package com.champets.fureverhome.utility;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class FileUploadUtil {
    private static final String UPLOAD_DIR = "src/main/resources/static/assets";
    public static String uploadFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists())
                    uploadDir.mkdirs();

                String originalFileName = file.getOriginalFilename();
                if (originalFileName != null && !originalFileName.isEmpty()) {
                    File serverFile = new File(uploadDir, originalFileName);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();

                    String imagePath = "/assets/" + originalFileName;
                    return imagePath;
                }

            } catch (Exception e) {

            }

        } else {

        }
        return null;
    }

}
