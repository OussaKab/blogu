package dev.oussama.blogu.services;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final Path uploads = Paths.get("./src/main/resources/uploads");

    public void setup() {
        this.deleteAll();
        try {
            Files.createDirectory(uploads);
        } catch (IOException ioe) {
            throw new RuntimeException("Couldn't initialize folder for uploads");
        }
    }

    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(uploads);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @SneakyThrows
    public String saveFile(MultipartFile file, String username) {
        Path path = this.uploads
                .resolve(username)
                .resolve(System.currentTimeMillis() + file.getOriginalFilename());
        File newFile = new File(path.toString());

        newFile.getParentFile().mkdirs();
        if (!newFile.exists())
            newFile.createNewFile();

        final InputStream inputStream = file.getInputStream();
        byte[] buf = new byte[inputStream.available()];
        final FileOutputStream fos = new FileOutputStream(newFile);
        int read;
        while ((read = inputStream.read(buf)) != -1)
            fos.write(buf, 0, read);
        return path.toString();
    }

    public Resource load(String path) {
        try {
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists())
                return resource;
            else
                throw new RuntimeException("Could not read the file!");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}