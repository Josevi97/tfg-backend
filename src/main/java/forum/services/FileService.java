package forum.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import forum.constants.FileConstants;
import forum.exceptions.IlegalFileExtensionException;

@Service
public class FileService {

    public String toImage(MultipartFile mFile, String name, boolean bDelete) throws IlegalFileExtensionException {
        String path = String.format("%s/%s", FileConstants.STATIC_FILES_ROUTE, name);
        File file = new File(path);

        if (mFile == null) {
            if (bDelete) {
                file.delete();
                return null;
            }

            return name;
        }

        String extension = mFile.getOriginalFilename().split("\\.")[1];
        boolean b = Arrays.asList(FileConstants.IMAGE_EXTENSIONS).contains(extension);

        if (!b) {
            throw new IlegalFileExtensionException();
        }

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(mFile.getBytes());
        } catch (IOException e) {
        }

        return name;
    }

    public void removeFile(String name) {
        String path = String.format("%s/%s", FileConstants.STATIC_FILES_ROUTE, name);
        new File(path).delete();
    }

}
