package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import com.icm.tiremanagementapi.models.TireModel;
import com.icm.tiremanagementapi.models.TireSensorModel;
import com.icm.tiremanagementapi.models.VehicleModel;
import com.icm.tiremanagementapi.repositories.IrregularitiesTireRepository;
import com.icm.tiremanagementapi.repositories.TireSensorRepository;
import com.icm.tiremanagementapi.repositories.VehicleRepository;
import com.icm.tiremanagementapi.requests.CheckResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TireSensorService {
    @Autowired
    TireSensorRepository tireSensorRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private IrregularitiesTireRepository irregularitiesTireRepository;
    /**
     * Retrieves a list of all tires in the system.
     *
     * @return List of TireModel objects.
     */
    public List<TireSensorModel> getAll() {
        return tireSensorRepository.findAll();
    }

    /**
     * Retrieves a specific tire by its ID.
     *
     * @param id The ID of the tire to retrieve.
     * @return Optional containing the TireModel if found, otherwise empty.
     */
    public Optional<TireSensorModel> getById(Long id) {
        return tireSensorRepository.findById(id);
    }

    /**
     * Retrieves a specific tire by its identification code.
     *
     * @param code The identification code of the tire to retrieve.
     * @return Optional containing the TireModel if found, otherwise empty.
     */
    public Optional<TireSensorModel> findByIdentificationCode(String code) {
        return tireSensorRepository.findByIdentificationCode(code);
    }

    /**
     * Retrieves a list of all tires associated with a specific vehicle.
     * This method is designed to fetch a comprehensive list without pagination,
     * suitable for scenarios where the complete set of associated tires is required.
     *
     * @param vehicleId The ID of the vehicle for which to retrieve the associated tires.
     * @return List of TireModel objects associated with the specified vehicle ID.
     */
    public List<TireSensorModel> findTiresByVehicleId(Long vehicleId) {
        return tireSensorRepository.findByVehicleModelId(vehicleId);
    }

    /**
     * Retrieves a list of tires associated with a specific vehicle using pagination.
     *
     * @param vehicle  The vehicle for which to retrieve tires.
     * @param pageable The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle.
     */
    public Page<TireSensorModel> findByVehicleModelId(Long vehicle, Pageable pageable) {
        return tireSensorRepository.findByVehicleModelId(vehicle, pageable);
    }

    /**
     * Finds all tires related to a specific vehicle and positioned at a specified location code.
     * Useful for retrieving tires based on their physical location on a vehicle.
     * @param vehicle The ID of the vehicle for which to find tires.
     * @param positioning The positioning code of the tire on the vehicle.
     * @return A list of TireModel objects associated with the given vehicle and positioning code.
     */
    public List<TireSensorModel> findTiresByVehicleAndPositioning(Long vehicle, String positioning) {
        return tireSensorRepository.findByVehicleModelIdAndPositioningLocationCode(vehicle, positioning);
    }

    /**
     * Retrieves a paginated list of tires associated with a specific vehicle and status.
     *
     * @param vehicleId The ID of the vehicle for which to retrieve tires.
     * @param status    The status of the tires to filter.
     * @param pageable  The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle and status.
     */
    public Page<TireSensorModel> findByVehicleModelIdAndStatus(Long vehicleId, Boolean status, Pageable pageable) {
        return tireSensorRepository.findByVehicleModelIdAndStatus(vehicleId, status, pageable);
    }

    /**
     * Creates a new tire record in the system.
     *
     * @param tireSensorModel The TireModel object representing the new tire.
     * @return The created TireModel.
     */
    public TireSensorModel createTire(TireSensorModel tireSensorModel) {
        return tireSensorRepository.save(tireSensorModel);
    }

    /**
     * Updates an existing tire record in the system.
     *
     * @param tire The updated TireModel.
     * @param id   The ID of the tire to update.
     * @return The updated TireModel if the tire with the given ID is found, otherwise null.
     */
    public TireSensorModel updateTire(TireSensorModel tire, Long id) {
        return tireSensorRepository.findById(id)
                .map(existingTire -> {
                    existingTire.setVehicleModel(tire.getVehicleModel());
                    return tireSensorRepository.save(existingTire);
                })
                .orElse(null);
    }

    /**
     * Deletes a tire record from the system by its ID.
     *
     * @param id The ID of the tire to delete.
     */
    public void deleteTire(Long id) {
        tireSensorRepository.deleteById(id);
    }

    public TireSensorModel updateProperties(Double temperature, Double pressure, Integer battery, Long idvehicle, Long idtire) {
        VehicleModel vehicle = vehicleRepository.findById(idvehicle)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        TireSensorModel tire = tireSensorRepository.findById(idtire)
                .orElseThrow(() -> new RuntimeException("Neumático no encontrado"));

        // Ejecuta las verificaciones consolidadas y decide sobre la creación de irregularidades
        CheckResult checkResult = checkAllConditions(temperature, pressure, battery, vehicle, tire);
        if (checkResult.isShouldCreateIrregularity()) {
            createIrregularity(checkResult, tire);
        }

        // Actualiza las propiedades del neumático según los datos recibidos
        tire.setTemperature(temperature);
        tire.setBatteryLevel(battery.doubleValue());
        tire.setPressure(pressure);

        // Guarda el neumático actualizado en la base de datos
        return tireSensorRepository.save(tire);
    }


    private CheckResult checkAllConditions(Double temperature, Double pressure, Integer battery, VehicleModel vehicle, TireSensorModel tire) {
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
        result.setIdtire(tire.getId());
        return result;
    }

    private void createIrregularity(CheckResult checkResult, TireSensorModel tire) {
        ZonedDateTime tenMinutesAgo = ZonedDateTime.now().minusMinutes(10);
        List<IrregularitiesTireModel> recentIrregularities = irregularitiesTireRepository.findByNameIrregularityAndTireSensorModelIdAndCreatedAtGreaterThanEqual(checkResult.getIrregularityName(), tire.getId(), tenMinutesAgo);

        if (!recentIrregularities.isEmpty()) {
            // Existe al menos una incidencia en los últimos 10 minutos con el mismo nombre
            return; // No crear una nueva irregularidad
        }

        // No se encontraron incidencias recientes, proceder a crear una nueva
        IrregularitiesTireModel irregularity = new IrregularitiesTireModel();
        irregularity.setNameIrregularity(checkResult.getIrregularityName());
        irregularity.setDetailsIrregularity(checkResult.getIrregularityDetail());
        irregularity.setVehicleModel(tire.getVehicleModel());
        irregularity.setCompany(tire.getCompany());
        irregularity.setStatus(true); // Asumiendo que true indica una irregularidad activa
        irregularity.setRecordedTemperature(checkResult.getRecordedTemperature());
        irregularity.setRecordedPressure(checkResult.getRecordedPressure());
        irregularity.setRecordedBatteryLevel(checkResult.getRecordedBatteryLevel());
        irregularity.setTireSensorModel(tire);
        irregularitiesTireRepository.save(irregularity);
    }
}
