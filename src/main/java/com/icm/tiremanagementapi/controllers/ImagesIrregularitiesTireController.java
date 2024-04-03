package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.CompanyModel;
import com.icm.tiremanagementapi.models.ImagesIrregularitiesTireModel;
import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import com.icm.tiremanagementapi.services.CompanyService;
import com.icm.tiremanagementapi.services.ImagesIrregularitiesTireService;
import com.icm.tiremanagementapi.services.IrregularitiesTireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/IIT")
public class ImagesIrregularitiesTireController {
    @Autowired
    private ImagesIrregularitiesTireService imagesIrregularitiesTireService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<ImagesIrregularitiesTireModel> findById(@PathVariable Long id) {
        Optional<ImagesIrregularitiesTireModel> data = imagesIrregularitiesTireService.findById(id);
        return data.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ImagesIrregularitiesTireModel> findAll() {
        return imagesIrregularitiesTireService.findAll();
    }

    @GetMapping("/byIrregularities/{id}")
    public ResponseEntity<List<ImagesIrregularitiesTireModel>> findByIrregularitiesTireModelId(@PathVariable Long id) {
        List<ImagesIrregularitiesTireModel> images = imagesIrregularitiesTireService.findByIrregularitiesTireModelId(id);
        if (images.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<ImagesIrregularitiesTireModel> getImageByImageName(@RequestParam String imageName,
                                                                             @RequestParam Long irregularityId) {
        ImagesIrregularitiesTireModel image = imagesIrregularitiesTireService.findByImageNameAndIrregularitiesTireModelId(imageName, irregularityId);
        return ResponseEntity.ok(image);
    }


    @PostMapping
    public ResponseEntity<ImagesIrregularitiesTireModel> saveImage(
            @RequestParam("irregularitiesTireModelId") Long irregularitiesTireModelId,
            @RequestParam("companyModelId") Long companyModelId,
            @RequestParam("details") String details,
            @RequestParam("file") MultipartFile file) {

        ImagesIrregularitiesTireModel image = new ImagesIrregularitiesTireModel();

        IrregularitiesTireModel irregularitiesTire = new IrregularitiesTireModel();
        irregularitiesTire.setId(irregularitiesTireModelId);
        Optional<CompanyModel> company = companyService.findById(companyModelId);

        CompanyModel com = new CompanyModel();
        com.setId(companyModelId);
        com.setName(company.get().getName());
        image.setIrregularitiesTireModel(irregularitiesTire);
        image.setCompanyModel(com);
        image.setDetails(details);

        ImagesIrregularitiesTireModel data = imagesIrregularitiesTireService.saveImage(image, file);

        if (data != null) {
            return new ResponseEntity<>(data, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImagesIrregularitiesTireModel> deleteImage(@PathVariable Long id) {
        imagesIrregularitiesTireService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
