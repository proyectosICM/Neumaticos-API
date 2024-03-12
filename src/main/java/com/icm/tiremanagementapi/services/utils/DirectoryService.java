package com.icm.tiremanagementapi.services.utils;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DirectoryService {

    /**
     * Crea un directorio en la ruta especificada, incluyendo el nombre del directorio.
     *
     * @param basePath Ruta base donde se creará el directorio.
     * @param directoryName Nombre del directorio a crear.
     */
    public void createDirectoryWithName(String basePath, String directoryName) {
        // Construye la ruta completa del directorio, incluyendo el nombre del directorio al final.
        String fullDirectoryPath = basePath + File.separator + directoryName;

        File directory = new File(fullDirectoryPath);
        if (!directory.exists()) {
            boolean wasSuccessful = directory.mkdirs();
            if (wasSuccessful) {
                System.out.println("Directorio creado exitosamente en " + fullDirectoryPath);
            } else {
                System.out.println("No se pudo crear el directorio en " + fullDirectoryPath);
            }
        } else {
            System.out.println("El directorio ya existe en " + fullDirectoryPath);
        }
    }

    /**
     * Verifica si un directorio existe en la ruta especificada.
     *
     * @param basePath Ruta base donde se verificará la existencia del directorio.

     * @return true si el directorio existe, false en caso contrario.
     */
    public boolean doesDirectoryExist(String basePath) {
        String fullDirectoryPath = basePath;
        File directory = new File(fullDirectoryPath);
        return directory.exists();
    }
}