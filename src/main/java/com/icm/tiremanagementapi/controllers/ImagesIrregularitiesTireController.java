package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.ImagesIrregularitiesTireModel;
import com.icm.tiremanagementapi.services.ImagesIrregularitiesTireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/IIT")
public class ImagesIrregularitiesTireController {
    @Autowired
    private ImagesIrregularitiesTireService imagesIrregularitiesTireService;

    @GetMapping
    public List<ImagesIrregularitiesTireModel> getAllImages() {
        return imagesIrregularitiesTireService.getAllImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagesIrregularitiesTireModel> getImagesById(@PathVariable Long id) {
        Optional<ImagesIrregularitiesTireModel> data = imagesIrregularitiesTireService.getImageById(id);
        return data.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ImagesIrregularitiesTireModel> saveImage(@RequestBody ImagesIrregularitiesTireModel image,
                                                                   @RequestParam("file") MultipartFile file){
        ImagesIrregularitiesTireModel data = imagesIrregularitiesTireService.saveImage(image, file);

        if (data != null) {
            return new ResponseEntity<>(data, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImagesIrregularitiesTireModel> deleteImage(@PathVariable Long id){
        imagesIrregularitiesTireService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
