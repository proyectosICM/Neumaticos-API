package com.icm.TireManagementApi.Services;

import com.icm.TireManagementApi.Models.TireModel;
import com.icm.TireManagementApi.Repositories.TireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing operations related to Tire entities.
 * This service provides methods for retrieving, creating, updating, and deleting tire records in the system.
 */
@Service
public class TireService {
    @Autowired
    private TireRepository tireRepository;

    /**
     * Retrieves a list of all tires in the system.
     *
     * @return List of TireModel objects.
     */
    public List<TireModel> getAll() {
        return tireRepository.findAll();
    }

    /**
     * Retrieves a specific tire by its ID.
     *
     * @param id The ID of the tire to retrieve.
     * @return Optional containing the TireModel if found, otherwise empty.
     */
    public Optional<TireModel> getById(Long id) {
        return tireRepository.findById(id);
    }

    /**
     * Creates a new tire record in the system.
     *
     * @param tire The TireModel object representing the new tire.
     * @return The created TireModel.
     */
    public TireModel createTire(TireModel tire) {
        return tireRepository.save(tire);
    }

    /**
     * Updates an existing tire record in the system.
     *
     * @param tire The updated TireModel.
     * @param id   The ID of the tire to update.
     * @return The updated TireModel if the tire with the given ID is found, otherwise null.
     */
    public TireModel updateTire(TireModel tire, Long id) {
        return tireRepository.findById(id)
                .map(existingTire -> {
                    existingTire.setIdentificationCode(tire.getIdentificationCode());
                    existingTire.setTemperature(tire.getTemperature());
                    existingTire.setPressure(tire.getPressure());
                    return tireRepository.save(existingTire);
                })
                .orElse(null);
    }

    /**
     * Deletes a tire record from the system by its ID.
     *
     * @param id The ID of the tire to delete.
     */
    public void deleteTire(Long id) {
        tireRepository.deleteById(id);
    }
}
