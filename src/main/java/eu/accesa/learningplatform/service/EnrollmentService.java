package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.EnrollmentDto;

import java.util.List;

public interface EnrollmentService {

    EnrollmentDto enroll(EnrollmentDto enrollmentDto);

    void undoEnroll(EnrollmentDto enrollmentDto);

    List<EnrollmentDto> getAllEnrollments();
}
