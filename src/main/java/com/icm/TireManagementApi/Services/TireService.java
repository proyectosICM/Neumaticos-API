package com.icm.TireManagementApi.Services;

import com.icm.TireManagementApi.Models.IrregularitiesTireModel;
import com.icm.TireManagementApi.Models.TireModel;
import com.icm.TireManagementApi.Models.VehicleModel;
import com.icm.TireManagementApi.Repositories.IrregularitiesTireRepository;
import com.icm.TireManagementApi.Repositories.TireRepository;
import com.icm.TireManagementApi.Repositories.VehicleRepository;
import com.icm.TireManagementApi.Requests.CheckResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private IrregularitiesTireRepository irregularitiesTireRepository;
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
     * Retrieves a specific tire by its identification code.
     *
     * @param code The identification code of the tire to retrieve.
     * @return Optional containing the TireModel if found, otherwise empty.
     */
    public Optional<TireModel> findByIdentificationCode(String code) {
        return tireRepository.findByIdentificationCode(code);
    }

    /**
     * Retrieves a list of all tires associated with a specific vehicle.
     * This method is designed to fetch a comprehensive list without pagination,
     * suitable for scenarios where the complete set of associated tires is required.
     *
     * @param vehicleId The ID of the vehicle for which to retrieve the associated tires.
     * @return List of TireModel objects associated with the specified vehicle ID.
     */
    public List<TireModel> findTiresByVehicleId(Long vehicleId) {
        return tireRepository.findByVehicleModelId(vehicleId);
    }

    /**
     * Retrieves a list of tires associated with a specific vehicle using pagination.
     *
     * @param vehicle  The vehicle for which to retrieve tires.
     * @param pageable The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle.
     */
    public Page<TireModel> findByVehicleModelId(Long vehicle, Pageable pageable) {
        return tireRepository.findByVehicleModelId(vehicle, pageable);
    }

    /**
     * Finds all tires related to a specific vehicle and positioned at a specified location code.
     * Useful for retrieving tires based on their physical location on a vehicle.
     * @param vehicle The ID of the vehicle for which to find tires.
     * @param positioning The positioning code of the tire on the vehicle.
     * @return A list of TireModel objects associated with the given vehicle and positioning code.
     */
    public List<TireModel> findTiresByVehicleAndPositioning(Long vehicle, String positioning) {
        return tireRepository.findByVehicleModelIdAndPositioningLocationCode(vehicle, positioning);
    }

    /**
     * Retrieves a paginated list of tires associated with a specific vehicle and status.
     *
     * @param vehicleId The ID of the vehicle for which to retrieve tires.
     * @param status    The status of the tires to filter.
     * @param pageable  The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle and status.
     */
    public Page<TireModel> findByVehicleModelIdAndStatus(Long vehicleId, Boolean status, Pageable pageable) {
        return tireRepository.findByVehicleModelIdAndStatus(vehicleId, status, pageable);
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

    public TireModel updateProperties(Double temperature, Double pressure, Integer battery, Long idvehicle, Long idtire) {
        VehicleModel vehicle = vehicleRepository.findById(idvehicle)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        TireModel tire = tireRepository.findById(idtire)
                .orElseThrow(() -> new RuntimeException("Neumático no encontrado"));

        // Ejecuta las verificaciones consolidadas y decide sobre la creación de irregularidades
        CheckResult checkResult = checkAllConditions(temperature, pressure, battery, vehicle);
        if (checkResult.isShouldCreateIrregularity()) {
            createIrregularity(checkResult, tire);
        }

        // Actualiza las propiedades del neumático según los datos recibidos
        tire.setTemperature(temperature);
        tire.setBatteryLevel(battery.doubleValue());
        tire.setPressure(pressure);

        // Guarda el neumático actualizado en la base de datos
        return tireRepository.save(tire);
    }


    private CheckResult checkAllConditions(Double temperature, Double pressure, Integer battery, VehicleModel vehicle) {
        CheckResult result = new CheckResult();
        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder detailBuilder = new StringBuilder();

        // Verificar batería
        if (battery <= 30) {
            result.setShouldCreateIrregularity(true);
            nameBuilder.append("Batería baja; ");
            detailBuilder.append(String.format("Batería por debajo del %d%%. ", battery));
            result.setRecordedBatteryLevel(battery.doubleValue());
        }

        // Verificar presión
        if (pressure.compareTo(vehicle.getStandardPressure()) < 0) {
            result.setShouldCreateIrregularity(true);
            nameBuilder.append("Presión baja; ");
            detailBuilder.append("Presión demasiado baja para el estándar definido. ");
            result.setRecordedPressure(pressure);
        } else if (pressure.compareTo(vehicle.getStandardPressure()) > 0) {
            result.setShouldCreateIrregularity(true);
            nameBuilder.append("Presión alta; ");
            detailBuilder.append("Presión demasiado alta para el estándar definido. ");
            result.setRecordedPressure(pressure);
        }

        // Verificar temperatura
        if (temperature.compareTo(vehicle.getStandardTemperature()) < 0) {
            result.setShouldCreateIrregularity(true);
            nameBuilder.append("Temperatura baja; ");
            detailBuilder.append("Temperatura demasiado baja para el estándar definido. ");
            result.setRecordedTemperature(temperature);
        } else if (temperature.compareTo(vehicle.getStandardTemperature()) > 0) {
            result.setShouldCreateIrregularity(true);
            nameBuilder.append("Temperatura alta; ");
            detailBuilder.append("Temperatura demasiado alta para el estándar definido. ");
            result.setRecordedTemperature(temperature);
        }

        result.setIrregularityName(nameBuilder.toString());
        result.setIrregularityDetail(detailBuilder.toString());

        return result;
    }

    private void createIrregularity(CheckResult checkResult, TireModel tire) {
        if (!checkResult.isShouldCreateIrregularity()) return; // No crear irregularidad si no se cumplen las condiciones

        IrregularitiesTireModel irregularity = new IrregularitiesTireModel();
        irregularity.setNameIrregularity(checkResult.getIrregularityName());
        irregularity.setDetailsIrregularity(checkResult.getIrregularityDetail());
        irregularity.setVehicleModel(tire.getVehicleModel());
        irregularity.setCompany(tire.getCompany());
        irregularity.setStatus(true); // Asumiendo que true indica una irregularidad activa
        irregularity.setRecordedTemperature(checkResult.getRecordedTemperature());
        irregularity.setRecordedPressure(checkResult.getRecordedPressure());
        irregularity.setRecordedBatteryLevel(checkResult.getRecordedBatteryLevel());
        irregularitiesTireRepository.save(irregularity);
    }
}
