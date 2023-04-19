package com.com3014.appointmentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.com3014.appointmentservice.model.Appointment;
import com.com3014.appointmentservice.model.json.AppointmentJson;
import com.com3014.appointmentservice.repository.AppointmentRepository;

import com.com3014.appointmentservice.exceptions.AppointmentIdNotFoundException;
import java.time.Instant;
import java.util.*;

@Service
public class AppointmentService {

 

 private final AppointmentRepository appointmentRepository;
 @Autowired
 public AppointmentService(AppointmentRepository appointmentRepository){
    this.appointmentRepository = appointmentRepository;
 }
 public Optional<Appointment> getAppointmentById(UUID id){
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(AppointmentJson json){
        var appointment = new Appointment(
            json.getdocId(),
            json.getstartTime(),
            json.getendTime(),
            json.getpatientId(),
            json.getsummary(),
            json.gettype(),
            json.getcancelled()
        );
        return appointmentRepository.save(appointment);
    }


    public Appointment updateAppointment(UUID id,AppointmentJson json) {
        var appointment = getAppointmentByIdOrThrow(id);
        
         appointment.setstartTime(json.getstartTime());
         appointment.setendTime(json.getendTime());
         appointment.setsummary(json.getsummary());
         appointment.settype(json.gettype());
         appointment.setcancelled(json.getcancelled());
         appointment.setpatientId(json.getpatientId());
         appointment.setdocId(json.getdocId());

        return appointmentRepository.save(appointment);
    }
    public void  deleteAppointment(UUID id) {
        appointmentRepository.delete(getAppointmentByIdOrThrow(id));
    }

    
    public Appointment getAppointmentByIdOrThrow(UUID id){
        return getAppointmentById(id).orElseThrow(
            () -> new AppointmentIdNotFoundException(
                "Could not the appointment id'%s'".formatted(id)));
    }

    

    public List<Appointment> getAppointmentByPatientId(UUID patientId){
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentByDoctorId(UUID doctorId){
        return appointmentRepository.findBydocId(doctorId);
    }

   public List<Appointment> findByType(String type){
    return appointmentRepository.findByType(type);
   }

   public List<Appointment> getAllAppointments(){
    return appointmentRepository.findAll();
   }
   
    public List<Appointment> getAppointmentsBetween(Instant startTime,Instant endTime) {
       return appointmentRepository.findByStartTimeAfterAndEndTimeBefore(startTime,endTime);
    } 
}