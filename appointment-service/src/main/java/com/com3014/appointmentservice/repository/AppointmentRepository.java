package com.com3014.appointmentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.com3014.appointmentservice.model.Appointment;
import java.util.*;
import java.sql.Time;

public interface AppointmentRepository extends MongoRepository<Appointment,UUID>{
    
    List<Appointment> findByPatientId(UUID id);

    List<Appointment> findByType(String type);

    List<Appointment> findByStartTimeAfterAndEndTimeBefore(Time startTime,Time endTime);
}
