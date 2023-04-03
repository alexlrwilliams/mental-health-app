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
public class appointmentservicecontroller
 {
    
    @Autowired
    private final  AppointmentService appointmentservice;
    public appointmentservicecontroller(AppointmentService appointmentservice)
    {
        this.appointmentservice = appointmentservice;
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentJson json)
    {
        return appointmentservice.createAppointment(json);
    }

    @GetMapping("/{id}")
    
        public Optional<Appointment> getAppointmentById(@PathVariable UUID id)
        {
            return appointmentservice.getAppointmentById(id);

        }
    @PutMapping("/{id}")
    public Appointment updateappointment(@PathVariable UUID id,@RequestBody AppointmentJson json)
    {
        return appointmentservice.updateappointment(id,json);
    }
    @DeleteMapping("/{id}")
    public void deleteappointment(@PathVariable UUID id)
    {
        appointmentservice.deleteappointment(id);
    }

    @GetMapping("/{Patient_ID}")

    public Optional<Appointment> getAppointmentByPatient_ID(@PathVariable UUID Patient_ID)
    {
        return appointmentservice.getAppointmentByPatient_ID(Patient_ID);

    }
    @GetMapping("/Type")

    public List<Appointment> findByType(String Type)
    {
        return appointmentservice.findByType(Type);
    }
    public List<Appointment> getfindByStartTimeAfterAndEndTimeBefore(Time StartTime,Time EndTime)
    {
        return appointmentservice.getfindByStartTimeAfterAndEndTimeBefore(StartTime,EndTime);
    }
}