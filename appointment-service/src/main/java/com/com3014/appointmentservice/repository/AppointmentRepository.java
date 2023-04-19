package com.com3014.appointmentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.com3014.appointmentservice.model.Appointment;
import java.util.*;
import java.time.Instant;

public interface AppointmentRepository extends MongoRepository<Appointment,UUID>{

    List<Appointment> findByPatientId(UUID id);
    List<Appointment> findBydocId(UUID id);

    List<Appointment> findByType(String type);

    List<Appointment> findByStartTimeAfterAndEndTimeBefore(Instant startTime,Instant endTime);
}
