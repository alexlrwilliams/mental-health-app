package com.com3014.appointmentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.com3014.appointmentservice.model.Appointment;
import com.com3014.appointmentservice.model.json.AppointmentJson;
import com.com3014.appointmentservice.repository.AppointmentRepository;
import com.com3014.appointmentservice.exceptions.AppointmentIdnotFoundException;
import java.sql.Time;
import java.util.*;

@Service
public class AppointmentService {

 @Autowired
 AppointmentRepository appointmentrepo ;



    public List<Appointment> getAllAppointments()
    {
        return appointmentrepo.findAll();
    }

    public Optional<Appointment> getAppointmentById(UUID id)
    {
        return appointmentrepo.findById(id);
    }

    public Appointment createAppointment(AppointmentJson json)
    {
        var appointment = new Appointment(
            json.getid(),
            json.getDOC_ID(),
            json.getappointmentStartTime(),
            json.getappointmentEndTime(),
            json.getPatient_ID(),
            json.getsummary(),
            json.getType(),
            json.getStatus()
        );
        return appointmentrepo.save(appointment);
    }


    public Appointment updateappointment(UUID id,AppointmentJson json) {
        var appointment = getappointmentByIdorThrow(id);
        
        ((Appointment) appointment).setappointmentStartTime(json.getappointmentStartTime());
        ((Appointment) appointment).setappointmentEndTime(json.getappointmentEndTime());
        ((Appointment) appointment).setsummary(json.getsummary());
        ((Appointment) appointment).setType(json.getType());
        ((Appointment) appointment).setStatus(json.getStatus());

        return appointmentrepo.save(appointment);
    }
    public void  deleteappointment(UUID id) {
        appointmentrepo.delete(deleteappointmentByIdorThrow(id));
    }

    public Appointment deleteappointmentByIdorThrow(UUID id)
     {
        return getAppointmentById(id).orElseThrow(
            () -> new AppointmentIdnotFoundException(
                "Could not the appointment id'%s'".formatted(id)));
    }
    public Appointment getappointmentByIdorThrow(UUID id)
     {
        return getAppointmentById(id).orElseThrow(
            () -> new AppointmentIdnotFoundException(
                "Could not the appointment id'%s'".formatted(id)));
    }

    

    public Optional<Appointment> getAppointmentByPatient_ID(UUID Patient_ID)
    {
        return appointmentrepo.findById(Patient_ID);
    }

   

    public List<Appointment> getAppointmentByType(String Type)
    {
        return appointmentrepo.getAllAppointments(Type);
    }

    public List<Appointment> getfindByStartTimeAfterAndEndTimeBefore(Time appointmentStartTime,Time appointmentEndTime)
    {
       return appointmentrepo.findByStartTimeAfterAndEndTimeBefore(appointmentStartTime,appointmentEndTime);
    } 
}