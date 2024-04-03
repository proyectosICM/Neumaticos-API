package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.controllers.MailController;
import com.icm.tiremanagementapi.controllers.RoleController;
import com.icm.tiremanagementapi.controllers.UserController;
import com.icm.tiremanagementapi.domain.EmailDTO;
import com.icm.tiremanagementapi.models.*;
import com.icm.tiremanagementapi.repositories.IrregularitiesTireRepository;
import com.icm.tiremanagementapi.repositories.PositioningRepository;
import com.icm.tiremanagementapi.repositories.TireSensorRepository;
import com.icm.tiremanagementapi.repositories.VehicleRepository;
import com.icm.tiremanagementapi.requests.CheckResult;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TireSensorService {
    @Autowired
    private TireSensorRepository tireSensorRepository;

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserController userController;

    @Autowired
    private MailController mailController;

    @Autowired
    private RoleController roleController;

    @Autowired
    private IrregularitiesTireRepository irregularitiesTireRepository;

    @Autowired
    private PositioningRepository positioningRepository;

    @Value("${file.image}")
    private String basePath;

    public List<TireSensorModel> getAll() {
        return tireSensorRepository.findAll();
    }
    public List<TireSensorModel> findByCompanyModelIdAndStatus(Long companyId, Boolean status) {
        return tireSensorRepository.findByCompanyModelIdAndStatus(companyId, status);
    }

    public Optional<TireSensorModel> getById(Long id) {
        return tireSensorRepository.findById(id);
    }

    public Optional<TireSensorModel> findByIdentificationCode(String code) {
        return tireSensorRepository.findByIdentificationCode(code);
    }

    public List<TireSensorModel> findTiresByVehicleId(Long vehicleId) {
        List<TireSensorModel> tires = tireSensorRepository.findByVehicleModelId(vehicleId);
        tires.sort(Comparator.comparing(tire -> tire.getPositioning().getId()));
        return tires;
    }

    public Page<TireSensorModel> findByVehicleModelId(Long vehicle, Pageable pageable) {
        return tireSensorRepository.findByVehicleModelId(vehicle, pageable);
    }

    public List<TireSensorModel> findTiresByVehicleAndPositioning(Long vehicle, String positioning) {
        return tireSensorRepository.findByVehicleModelIdAndPositioningLocationCode(vehicle, positioning);
    }

    public Page<TireSensorModel> findByVehicleModelIdAndStatus(Long vehicleId, Boolean status, Pageable pageable) {
        return tireSensorRepository.findByVehicleModelIdAndStatus(vehicleId, status, pageable);
    }

    public Page<TireSensorModel> findByCompanyId(Long companyId, Pageable pageable) {
        return tireSensorRepository.findByCompanyModelId(companyId, pageable);
    }

    public Optional<TireSensorModel> findByVehicleModelIdAndPositioningId(Long vehicleModelId, Long positioningId) {
        return tireSensorRepository.findByVehicleModelIdAndPositioningId(vehicleModelId, positioningId);
    }

    public Optional<TireSensorModel> findByIdAndCompanyModelId(Long id, Long companyModelId) {
        return tireSensorRepository.findByIdAndCompanyModelIdAndStatus(id, companyModelId, false);
    }

    public TireSensorModel createTire(TireSensorModel tireSensorModel) {
        return tireSensorRepository.save(tireSensorModel);
    }

    public TireSensorModel updateTireSensor(TireSensorModel tireSensor, Long id) {
        // Verificar si el sensor a actualizar existe
        TireSensorModel existingTireSensor = tireSensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor no encontrado con id: " + id));

        // Verificar si hay un sensor anterior en la misma posición y vehículo que deba ser liberado
        if (tireSensor.getVehicleModel() != null && tireSensor.getPositioning() != null) {
            Optional<TireSensorModel> oldTireSensor = tireSensorRepository.findByVehicleModelIdAndPositioningId(
                    tireSensor.getVehicleModel().getId(), tireSensor.getPositioning().getId());
            if (oldTireSensor.isPresent()) {
                // Liberar el sensor anterior
                TireSensorModel sensorToFree = oldTireSensor.get();
                sensorToFree.setPositioning(null);
                sensorToFree.setVehicleModel(null);
                sensorToFree.setStatus(false); // Asegúrate de tener una enumeración o valor adecuado para el estado 'libre'
                tireSensorRepository.save(sensorToFree);
            }
        }

        // Actualizar el sensor existente con los nuevos detalles
        existingTireSensor.setIdentificationCode(tireSensor.getIdentificationCode());
        existingTireSensor.setStatus(tireSensor.getStatus());
        existingTireSensor.setPositioning(tireSensor.getPositioning());
        existingTireSensor.setVehicleModel(tireSensor.getVehicleModel());
        existingTireSensor.setCompanyModel(tireSensor.getCompanyModel());

        return tireSensorRepository.save(existingTireSensor);
    }

    public TireSensorModel changeSensor(Long id1, Long id2, String pos, Long v){
        Optional<TireSensorModel> result1 = tireSensorRepository.findById(id1).map(
                n1 -> {
                    n1.setStatus(false);
                    n1.setVehicleModel(null);
                    n1.setPositioning(null);
                    return tireSensorRepository.save(n1);
                });
        if (result1.isPresent()) {
            VehicleModel vehicle = new VehicleModel();
            vehicle.setId(v);

            PositioningModel positioningModel =  positioningRepository.findByLocationCode(pos);
            Optional<TireSensorModel> result2 = tireSensorRepository.findById(id2).map(
                    n2 -> {
                        n2.setStatus(true);
                        n2.setVehicleModel(vehicle);
                        n2.setPositioning(positioningModel);
                        return tireSensorRepository.save(n2);
                    });
            return result2.orElse(null);
        } else {
            // Si el primer neumático no se pudo actualizar, devolvemos null o lanzamos una excepción según la lógica del negocio.
            return null;
        }
    }

    public void deleteTire(Long id) {
        tireSensorRepository.deleteById(id);
    }

    public TireSensorModel updateProperties(Double temperature, Double pressure, Integer battery, Long idvehicle, Long idtire) {
        VehicleModel vehicle = vehicleRepository.findById(idvehicle)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        TireSensorModel sensor = tireSensorRepository.findById(idtire)
                .orElseThrow(() -> new RuntimeException("Neumático no encontrado"));

        // Ejecuta las verificaciones consolidadas y decide sobre la creación de irregularidades
        CheckResult checkResult = checkAllConditions(temperature, pressure, battery, vehicle, sensor);
        if (checkResult.isShouldCreateIrregularity()) {
            createIrregularity(checkResult, sensor);
        }

        // Actualiza las propiedades del neumático según los datos recibidos
        sensor.setTemperature(temperature);
        sensor.setBatteryLevel(battery.doubleValue());
        sensor.setPressure(pressure);

        // Guarda el neumático actualizado en la base de datos
        return tireSensorRepository.save(sensor);
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

    private void createIrregularity(CheckResult checkResult, TireSensorModel sensor) {
        ZonedDateTime tenMinutesAgo = ZonedDateTime.now().minusMinutes(10);
        List<IrregularitiesTireModel> recentIrregularities = irregularitiesTireRepository.findByNameIrregularityAndTireSensorModelIdAndCreatedAtGreaterThanEqual(checkResult.getIrregularityName(), sensor.getId(), tenMinutesAgo);

        if (!recentIrregularities.isEmpty()) {
            // Existe al menos una incidencia en los últimos 10 minutos con el mismo nombre
            return; // No crear una nueva irregularidad
        }

        // No se encontraron incidencias recientes, proceder a crear una nueva

        IrregularitiesTireModel irregularity = new IrregularitiesTireModel();
        irregularity.setNameIrregularity(checkResult.getIrregularityName());
        irregularity.setDetailsIrregularity(checkResult.getIrregularityDetail());
        irregularity.setVehicleModel(sensor.getVehicleModel());
        irregularity.setCompany(sensor.getCompanyModel());
        irregularity.setStatus(true); // Asumiendo que true indica una irregularidad activa
        irregularity.setRecordedTemperature(checkResult.getRecordedTemperature());
        irregularity.setRecordedPressure(checkResult.getRecordedPressure());
        irregularity.setRecordedBatteryLevel(checkResult.getRecordedBatteryLevel());
        irregularity.setTireSensorModel(sensor);
        irregularity.setTireModel(sensor.getTireModel());
        IrregularitiesTireModel data = irregularitiesTireRepository.save(irregularity);

        String directoryPath = basePath + File.separator + data.getCompany().getName() + File.separator + "irregularidades" + File.separator + data.getId();
        // Crear el directorio
        File directory = new File(directoryPath);
        boolean isDirectoryCreated = directory.mkdirs();
        sendEmailForIrregularity(irregularity);
    }

    private void sendEmailForIrregularity(IrregularitiesTireModel irregularity) {
        // Construir el cuerpo del correo electrónico
        String emailBody = "Se ha creado una nueva irregularidad con el siguiente detalle:\n" +
                "Nombre de la irregularidad: " + irregularity.getNameIrregularity() + "\n" +
                "Descripción: " + irregularity.getDetailsIrregularity();



        ResponseEntity<List<String>> response = userController.findByCompanyIdAndRoleIdIn(
                irregularity.getCompany().getId(), 3L, 4L);

        // Crear un objeto EmailDTO con la información necesaria
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setToUser(new String[]{"es123y123@gmail.com","eduardo.aguilar.ant@gmail.com" });
        emailDTO.setSubject("Nueva irregularidad detectada " + irregularity.getNameIrregularity() );
        emailDTO.setMessage(emailBody);

        // Enviar correo electrónico
        mailController.receiveRequestEmail(emailDTO);
    }
}
