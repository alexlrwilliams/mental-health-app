package com.com3014.appointmentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.UUID;
import java.util.Optional;


import com.com3014.appointmentservice.model.Appointment;
import com.com3014.appointmentservice.model.json.AppointmentJson;

import com.com3014.appointmentservice.service.AppointmentService;





@RestController
@RequestMapping("/api/appointments")
public class AppointmentController
 {
    
   
    private final  AppointmentService appointmentService;
    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
   
    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentJson json){
        return appointmentService.createAppointment(json);
    }

    @GetMapping("/{id}")
     public Optional<Appointment> getAppointmentById(@PathVariable UUID id){
            return appointmentService.getAppointmentById(id);

        }
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable UUID id,@RequestBody AppointmentJson json){
        return appointmentService.updateAppointment(id,json);
    }
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable UUID id){
        appointmentService.deleteAppointment(id);
    }

    @GetMapping("/{patientId}")
    public List<Appointment> getAppointmentByPatientId( UUID patientId) {
        return appointmentService.getAppointmentByPatientId(patientId);

    }
    @GetMapping("/type")
    public List<Appointment> findByType(@RequestParam("type")String type){
        return appointmentService.findByType(type);
    }

    @GetMapping()
    public List<Appointment> getAllAppointments(@RequestParam(name = "startTime", required = false) Time startTime, @RequestParam(name = "endTime", required = false) Time endTime) {
    if (startTime == null || endTime == null) {
        return appointmentService.getAllAppointments();
    } else {
        return appointmentService.getAppointmentsBetween(startTime, endTime);
    }
}
    
}