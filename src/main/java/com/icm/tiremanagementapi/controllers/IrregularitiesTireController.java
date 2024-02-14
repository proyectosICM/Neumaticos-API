package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import com.icm.tiremanagementapi.services.IrregularitiesTireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Handles HTTP requests related to tire irregularities.
 * Supports operations such as listing, retrieving, creating, updating, and deleting irregularities.
 */
@RestController
@RequestMapping("/api/irregularities")
public class IrregularitiesTireController {
    @Autowired
    private IrregularitiesTireService irregularitiesTireService;

    /**
     * Fetches a paginated list of all irregularities.
     *
     * @param pageable Pagination details.
     * @return Paginated list of irregularities.
     */
    @GetMapping("/page")
    public ResponseEntity<Page<IrregularitiesTireModel>> getAllIrregularities(Pageable pageable) {
        Page<IrregularitiesTireModel> page = irregularitiesTireService.getAllIrregularities(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * Fetches a paginated list of irregularities for a specific company.
     *
     * @param companyId The ID of the company.
     * @param page The page number to retrieve.
     * @param size The size of the page.
     * @return Paginated list of irregularities.
     */
    @GetMapping("/company/page")
    public ResponseEntity<Page<IrregularitiesTireModel>> getIrregularitiesByCompanyId(@RequestParam Long companyId,
                                                                                      @RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "6") int size) {
        Page<IrregularitiesTireModel> data = irregularitiesTireService.findIrregularitiesByCompanyId(companyId, page, size);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * Fetches a paginated list of irregularities for a specific company and vehicle.
     *
     * @param companyId The ID of the company.
     * @param vehicleId The ID of the vehicle.
     * @param page The page number to retrieve, default is 0.
     * @param size The size of the page, default is 6.
     * @return ResponseEntity containing the paginated list of irregularities and the HTTP status.
     */
    @GetMapping("/companyAndVehicle/page")
    public ResponseEntity<Page<IrregularitiesTireModel>> getIrregularitiesByCompanyIdAndVehicleId(@RequestParam Long companyId,
                                                                                                  @RequestParam Long vehicleId,
                                                                                                  @RequestParam(defaultValue = "0") int page,
                                                                                                  @RequestParam(defaultValue = "6") int size) {
        Page<IrregularitiesTireModel> data = irregularitiesTireService.findIrregularitiesByCompanyIdAndVehicleId(companyId, vehicleId, page, size);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * Fetches recent irregularities for a specific vehicle model.
     *
     * @param vehicleModelId The ID of the vehicle model.
     * @return Paginated list of recent irregularities.
     */
    @GetMapping("/recent/{vehicleModelId}")
    public ResponseEntity<Page<IrregularitiesTireModel>> getRecentIrregularitiesByVehicleModelId(@PathVariable Long vehicleModelId) {
        Page<IrregularitiesTireModel> page = irregularitiesTireService.findRecentIrregularitiesByVehicleModelId(vehicleModelId);
        return ResponseEntity.ok(page);
    }

    /**
     * Retrieves a specific irregularity by its ID.
     *
     * @param id The ID of the irregularity.
     * @return The requested irregularity if found, or HTTP 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IrregularitiesTireModel> getIrregularityById(@PathVariable Long id) {
        return irregularitiesTireService.getIrregularityById(id)
                .map(irregularity -> new ResponseEntity<>(irregularity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new irregularity record.
     *
     * @param irregularity The irregularity details to create.
     * @return The created irregularity record.
     */
    @PostMapping
    public ResponseEntity<IrregularitiesTireModel> createIrregularity(@RequestBody IrregularitiesTireModel irregularity) {
        IrregularitiesTireModel createdIrregularity = irregularitiesTireService.createIrregularity(irregularity);
        return new ResponseEntity<>(createdIrregularity, HttpStatus.CREATED);
    }

    /**
     * Updates an existing irregularity.
     *
     * @param id The ID of the irregularity to update.
     * @param irregularity The updated irregularity details.
     * @return The updated irregularity if found, or HTTP 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IrregularitiesTireModel> updateIrregularity(@PathVariable Long id, @RequestBody IrregularitiesTireModel irregularity) {
        IrregularitiesTireModel updatedIrregularity = irregularitiesTireService.updateIrregularity(irregularity, id);
        return updatedIrregularity != null ?
                new ResponseEntity<>(updatedIrregularity, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes an irregularity by its ID.
     *
     * @param id The ID of the irregularity to delete.
     * @return HTTP 204 if the operation is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIrregularity(@PathVariable Long id) {
        irregularitiesTireService.deleteIrregularity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
