package com.com3014.appointmentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.com3014.appointmentservice.model.Appointment;
import java.util.*;
import java.sql.Time;

public interface AppointmentRepository extends MongoRepository<Appointment,UUID>
{
    Optional<Appointment> findAppointmentByid(UUID id);

    List<Appointment> findByType(String Type);

    List<Appointment> findByStartTimeAfterAndEndTimeBefore(Time StartTime,Time EndTime);
}
