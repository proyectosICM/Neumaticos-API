package com.icm.tiremanagementapi.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/files")
public class FilesController {
    @Value("${file.image}")
    private String pathimg;

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename, @RequestParam String company, @RequestParam String irregularity) {
        try {
            // Construir la ruta personalizada usando los parámetros company e irregularity
            String fullPath = pathimg + "/" + company + "/" + "irregularidades" + "/" + irregularity + "/" + filename;

            // Crear un Path a partir de la ruta completa
            Path file = Paths.get(fullPath);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);
            } else {
                throw new RuntimeException("No se pudo leer el archivo: " + fullPath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
