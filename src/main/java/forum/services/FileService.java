package forum.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    public File toFile(MultipartFile mFile, String path) {
        if (mFile == null) {
            return null;
        }

        File file = new File(path);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(mFile.getBytes());
        } catch (IOException e) {
            return null;
        }

        return file;
    }

    public String getPathFixed(File file) {
        if (file == null) {
            return null;
        }

        return file.getAbsolutePath();
    }
}
