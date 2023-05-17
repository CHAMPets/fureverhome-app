package com.champets.fureverhome.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public static void saveFile(String uploadDir, String nameImage, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(nameImage);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + nameImage, ioe);
        }
    }

}
