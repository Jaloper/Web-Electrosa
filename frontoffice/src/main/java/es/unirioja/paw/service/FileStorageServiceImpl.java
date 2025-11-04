package es.unirioja.paw.service;

import es.unirioja.paw.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public void store(MultipartFile file, String targetPath) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        try {
            Path targetLocation = Paths.get(targetPath).toAbsolutePath().normalize();
            Files.createDirectories(targetLocation.getParent());

            Files.copy(
                file.getInputStream(),
                targetLocation,
                StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException ex) {
            throw new RuntimeException("Error al almacenar el archivo " + file.getOriginalFilename() + ". " + ex.getMessage(), ex);
        }
    }
}