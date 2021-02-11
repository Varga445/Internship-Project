package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.ApplicationDto;
import eu.accesa.learningplatform.model.entity.ApplicationStatusEnum;

import java.util.List;

public interface ApplicationService {

    ApplicationDto createApplication(ApplicationDto applicationDto);

    List<ApplicationDto> getAllApplications();

    ApplicationDto getApplicationById(Long Id);

    List<ApplicationDto> getApplicationsByStatus(ApplicationStatusEnum status);

    List<ApplicationDto> getApplicationsByUserId(Long Id);

    ApplicationDto updateStatus(Long Id, ApplicationStatusEnum applicationStatusEnum);

    void deleteApplication(Long Id);

}
