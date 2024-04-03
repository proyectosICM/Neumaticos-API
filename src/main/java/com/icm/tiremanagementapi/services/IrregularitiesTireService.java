package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import com.icm.tiremanagementapi.repositories.IrregularitiesTireRepository;
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

@Service
public class IrregularitiesTireService {
    @Autowired
    private IrregularitiesTireRepository irregularitiesTireRepository;

    @Value("${file.image}")
    private String basePath;

    public Optional<IrregularitiesTireModel> findById(Long id) {
        return irregularitiesTireRepository.findById(id);
    }

    public List<IrregularitiesTireModel> findByCompanyIdAndVehicleModelId(Long companyId, Long vehicleId) {
        List<IrregularitiesTireModel> irregularities = irregularitiesTireRepository.findByCompanyIdAndVehicleModelId(companyId, vehicleId);
        irregularities.sort((i1, i2) -> i2.getId().compareTo(i1.getId()));
        return irregularities;
    }

    public Page<IrregularitiesTireModel> findAll(Pageable pageable) {
        return irregularitiesTireRepository.findAll(pageable);
    }

    public Page<IrregularitiesTireModel> findByCompanyId(Long companyId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return irregularitiesTireRepository.findByCompanyId(companyId, pageable);
    }

    public Page<IrregularitiesTireModel> findByVehicleModelIdOrderByCreatedAtDesc(Long vehicleModelId) {
        Pageable topSix = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "createdAt"));
        return irregularitiesTireRepository.findByVehicleModelIdOrderByCreatedAtDesc(vehicleModelId, topSix);
    }

    public Page<IrregularitiesTireModel> findByCompanyIdAndVehicleModelId(Long companyId, Long vehicleId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return irregularitiesTireRepository.findByCompanyIdAndVehicleModelId(companyId, vehicleId, pageable);
    }

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

    public void deleteIrregularity(Long id) {
        irregularitiesTireRepository.deleteById(id);
    }
}
