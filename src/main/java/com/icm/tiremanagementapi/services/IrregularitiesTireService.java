package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import com.icm.tiremanagementapi.repositories.IrregularitiesTireRepository;
import com.icm.tiremanagementapi.services.utils.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing operations related to IrregularitiesTire entities.
 * This service provides methods for retrieving, creating, updating, and deleting irregularity records in the system.
 */
@Service
public class IrregularitiesTireService {
    @Autowired
    private IrregularitiesTireRepository irregularitiesTireRepository;
    @Autowired
    private DirectoryService directoryService;

    @Value("${file.image}")
    private String basePath;

    /**
     * Retrieves a paginated list of all tire irregularities in the system.
     *
     * @param pageable Pageable object for pagination.
     * @return Page of IrregularitiesTireModel objects.
     */
    public Page<IrregularitiesTireModel> getAllIrregularities(Pageable pageable) {
        return irregularitiesTireRepository.findAll(pageable);
    }

    /**
     * Retrieves a paginated list of all tire irregularities associated with a specific company.
     * This method creates a Pageable object based on the page number and page size parameters to control pagination.
     *
     * @param companyId The ID of the company for which to retrieve irregularities.
     * @param page The page number of the requested page.
     * @param size The size of the requested page.
     * @return A Page of IrregularitiesTireModel objects for the specified company.
     */
    public Page<IrregularitiesTireModel> findIrregularitiesByCompanyId(Long companyId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return irregularitiesTireRepository.findByCompanyId(companyId, pageable);
    }

    /**
     * Retrieves a paginated list of all tire irregularities associated with a specific company and vehicle.
     *
     * @param companyId The ID of the company.
     * @param vehicleId The ID of the vehicle.
     * @param page The page number to retrieve.
     * @param size The size of the page.
     * @return Page of IrregularitiesTireModel objects.
     */
    public Page<IrregularitiesTireModel> findIrregularitiesByCompanyIdAndVehicleId(Long companyId, Long vehicleId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return irregularitiesTireRepository.findByCompanyIdAndVehicleModelId(companyId, vehicleId, pageable);
    }

    /**
     * Retrieves the latest 6 irregularities for a specific vehicle model.
     * This method uses Spring Data JPA's method naming conventions to automatically generate the query,
     * limiting the results to the top 6 most recent entries based on the 'createdAt' timestamp.
     *
     * @param vehicleModelId The ID of the vehicle model for which to retrieve irregularities.
     * @return A page of IrregularitiesTireModel containing the latest 6 irregularities for the specified vehicle model.
     */
    public Page<IrregularitiesTireModel> findRecentIrregularitiesByVehicleModelId(Long vehicleModelId) {
        Pageable topSix = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "createdAt"));
        return irregularitiesTireRepository.findByVehicleModelIdOrderByCreatedAtDesc(vehicleModelId, topSix);
    }

    public List<IrregularitiesTireModel> findAndSortIrregularities(Long companyId, Long vehicleId) {
        List<IrregularitiesTireModel> irregularities = irregularitiesTireRepository.findByCompanyIdAndVehicleModelId(companyId, vehicleId);
        irregularities.sort((i1, i2) -> i2.getId().compareTo(i1.getId()));
        return irregularities;
    }

    /**
     * Retrieves a specific tire irregularity by its ID.
     *
     * @param id The ID of the irregularity to retrieve.
     * @return Optional containing the IrregularitiesTireModel if found, otherwise empty.
     */
    public Optional<IrregularitiesTireModel> getIrregularityById(Long id) {
        return irregularitiesTireRepository.findById(id);
    }

    /**
     * Creates a new tire irregularity record in the system.
     *
     * @param irregularity The IrregularitiesTireModel object representing the new irregularity.
     * @return The created IrregularitiesTireModel.
     */
    public IrregularitiesTireModel createIrregularity(IrregularitiesTireModel irregularity) {
        // Guardar la irregularidad en la base de datos
        IrregularitiesTireModel savedData = irregularitiesTireRepository.save(irregularity);

        if (savedData != null) {
            // Construir la ruta del directorio basada en la compañía y el ID de la irregularidad
            String directoryPath = basePath + File.separator + savedData.getCompany().getName() + File.separator + "irregularidades" + File.separator + savedData.getId();

            // Crear el directorio
            File directory = new File(directoryPath);
            boolean isDirectoryCreated = directory.mkdirs();

        } else {
            // Manejar el caso en que la irregularidad no se pudo guardar
            System.out.println("No se pudo guardar la irregularidad");
        }

        return savedData;
    }

    /**
     * Updates an existing tire irregularity record in the system.
     *
     * @param irregularity The updated IrregularitiesTireModel.
     * @param id           The ID of the irregularity to update.
     * @return The updated IrregularitiesTireModel if the irregularity with the given ID is found, otherwise null.
     */
    public IrregularitiesTireModel updateIrregularity(IrregularitiesTireModel irregularity, Long id) {
        return irregularitiesTireRepository.findById(id)
                .map(existingIrregularity -> {
                    existingIrregularity.setVehicleModel(irregularity.getVehicleModel());
                    existingIrregularity.setCompany(irregularity.getCompany());
                    existingIrregularity.setStatus(irregularity.getStatus());
                    return irregularitiesTireRepository.save(existingIrregularity);
                })
                .orElse(null);
    }

    /**
     * Deletes a tire irregularity record from the system by its ID.
     *
     * @param id The ID of the irregularity to delete.
     */
    public void deleteIrregularity(Long id) {
        irregularitiesTireRepository.deleteById(id);
    }
}
