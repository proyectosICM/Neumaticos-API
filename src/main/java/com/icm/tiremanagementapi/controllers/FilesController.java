package com.icm.tiremanagementapi.controllers;


import com.icm.tiremanagementapi.models.CompanyModel;
import com.icm.tiremanagementapi.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@RestController
@RequestMapping("api/files")
public class FilesController {
    @Value("${file.image}")
    private String pathimg;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename, @RequestParam Long company, @RequestParam String irregularity) {
        try {
            Optional<CompanyModel> companyModel = companyService.findById(company);
            // Construir la ruta personalizada usando los parámetros company e irregularity
            String fullPath = pathimg + "/" + companyModel.get().getName() + "/" + "irregularidades" + "/" + irregularity + "/" + filename;

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

    @GetMapping("/AllNamesimages")
    public ResponseEntity<List<String>> serveNameImages(@RequestParam Long company, @RequestParam String irregularity) {
        try {
            Optional<CompanyModel> companyModel = companyService.findById(company);
            String directoryPath = pathimg + "/" + companyModel.get().getName() + "/" + "irregularidades" + "/" + irregularity;

            Path dir = Paths.get(directoryPath);
            if (!Files.exists(dir) || !Files.isDirectory(dir)) {
                throw new RuntimeException("El directorio no existe: " + directoryPath);
            }

            try (Stream<Path> paths = Files.walk(dir)) {
                List<String> fileNames = paths
                        .filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toList());

                return ResponseEntity.ok().body(fileNames);
            } catch (Exception e) {
                throw new RuntimeException("Error al listar los archivos en el directorio: " + directoryPath, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @GetMapping("/allImages")
    public ResponseEntity<List<Map<String, Object>>> serveAllImages(@RequestParam Long company, @RequestParam String irregularity) {
        try {
            Optional<CompanyModel> companyModel = companyService.findById(company);
            if (!companyModel.isPresent()) {
                throw new RuntimeException("La compañía no existe: " + company);
            }

            String directoryPath = pathimg + "/" + companyModel.get().getName() + "/" + "irregularidades" + "/" + irregularity;
            Path dir = Paths.get(directoryPath);

            if (!Files.exists(dir) || !Files.isDirectory(dir)) {
                throw new RuntimeException("El directorio no existe: " + directoryPath);
            }

            try (Stream<Path> paths = Files.walk(dir)) {
                List<Map<String, Object>> urls = new ArrayList<>();
                List<String> fileNames = paths.filter(Files::isRegularFile).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());

                for (int i = 0; i < fileNames.size(); i++) {
                    Map<String, Object> map = new HashMap<>();
                    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/files/images/")
                            .path(fileNames.get(i))
                            .queryParam("company", company)
                            .queryParam("irregularity", irregularity)
                            .toUriString();
                    map.put("index", i);
                    map.put("url", url);
                    map.put("name", fileNames.get(i));
                    //map.put("details")
                    urls.add(map);
                }

                return ResponseEntity.ok().body(urls);
            } catch (Exception e) {
                throw new RuntimeException("Error al listar los archivos en el directorio: " + directoryPath, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
