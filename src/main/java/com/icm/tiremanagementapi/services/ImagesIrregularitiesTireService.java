package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.ImagesIrregularitiesTireModel;
import com.icm.tiremanagementapi.repositories.ImagesIrregularitiesTireRepository;
import com.icm.tiremanagementapi.services.utils.DirectoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImagesIrregularitiesTireService {
    @Autowired
    private ImagesIrregularitiesTireRepository imagesIrregularitiesTireRepository;
    @Autowired
    private DirectoryService directoryService;

    @Value("${file.image}")
    private String basePath;

    public List<ImagesIrregularitiesTireModel> getAllImages(){
        return imagesIrregularitiesTireRepository.findAll();
    }

    public Optional<ImagesIrregularitiesTireModel> getImageById(Long id){
        return imagesIrregularitiesTireRepository.findById(id);
    }

    public List<ImagesIrregularitiesTireModel> getByIrregularityId(Long id){
        return imagesIrregularitiesTireRepository.findByIrregularitiesTireModelId(id);
    }

    public ImagesIrregularitiesTireModel getImageByImageName(String imageName, Long idIrregularities) {
        return imagesIrregularitiesTireRepository.findByImageNameAndIrregularitiesTireModelId(imageName,idIrregularities)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with name: " + imageName));
    }

    public ImagesIrregularitiesTireModel saveImage(ImagesIrregularitiesTireModel imagesIrregularitiesTireModel, MultipartFile file) {
        String directoryPath = basePath + File.separator + imagesIrregularitiesTireModel.getCompanyModel().getName()
                + File.separator + "irregularidades" + File.separator + imagesIrregularitiesTireModel.getIrregularitiesTireModel().getId();

        if (directoryService.doesDirectoryExist(directoryPath)) {
            try {
                // Construye la ruta completa del archivo
                String filePath = directoryPath + File.separator + file.getOriginalFilename();
                File destinationFile = new File(filePath);

                // Guarda el archivo en el directorio
                file.transferTo(destinationFile);

                // Establece el nombre de la imagen en el modelo
                imagesIrregularitiesTireModel.setImageName(file.getOriginalFilename());
                return imagesIrregularitiesTireRepository.save(imagesIrregularitiesTireModel);
            } catch (IOException e) {
                e.printStackTrace();
                // Manejar la excepción adecuadamente
                return null;
            }
        } else {
            return null;
        }
    }

    public ImagesIrregularitiesTireModel editImage(Long id, ImagesIrregularitiesTireModel imagesIrregularitiesTireModel){
        return imagesIrregularitiesTireRepository.findById(id)
                .map(data -> {
                    data.setImageName(imagesIrregularitiesTireModel.getImageName());
                    data.setDetails(imagesIrregularitiesTireModel.getDetails());
                    data.setIrregularitiesTireModel(imagesIrregularitiesTireModel.getIrregularitiesTireModel());
                    return imagesIrregularitiesTireRepository.save(data);
                }).orElse(null);

    }

    public void deleteImage(Long id){
        imagesIrregularitiesTireRepository.deleteById(id);
    }
}
