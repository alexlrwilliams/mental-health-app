package com.com3014.appointmentservice.controller;

import com.com3014.appointmentservice.model.Appointment;
import com.com3014.appointmentservice.model.json.AppointmentJson;
import com.com3014.appointmentservice.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final  AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments(@RequestParam(name = "startTime", required = false) Instant startTime, @RequestParam(name = "endTime", required = false) Instant endTime) {
        if (startTime == null || endTime == null) {
            return appointmentService.getAllAppointments();
        } else {
            return appointmentService.getAppointmentsBetween(startTime, endTime);
        }
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentJson json, BindingResult result){
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(error -> {
                        var defaultMessage = error.getDefaultMessage();
                        if (error instanceof FieldError fieldError) {
                            return String.format("%s %s", fieldError.getField(), defaultMessage);
                        } else {
                            return defaultMessage;
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        } else {
            Appointment appointment = appointmentService.createAppointment(json);
            return ResponseEntity.ok(appointment);
        }
    }

    @GetMapping("/{id}")
     public Optional<Appointment> getAppointmentById(@PathVariable UUID id){
        return appointmentService.getAppointmentById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable UUID id, @Valid @RequestBody AppointmentJson json, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                .stream()
                .map(error -> {
                    var defaultMessage = error.getDefaultMessage();
                    if (error instanceof FieldError fieldError) {
                        return String.format("%s %s", fieldError.getField(), defaultMessage);
                    } else {
                        return defaultMessage;
                    }
                })
                .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        } else {
            Appointment appointment = appointmentService.updateAppointment(id,json);
            return ResponseEntity.ok(appointment);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable UUID id){
        appointmentService.deleteAppointment(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentByPatientId(@PathVariable UUID patientId) {
        return appointmentService.getAppointmentByPatientId(patientId);
    }

     @GetMapping("/doctor/{doctorId}")
     public List<Appointment> getAppointmentByDoctorId(@PathVariable UUID doctorId) {
         return appointmentService.getAppointmentByDoctorId(doctorId);
     }

    @GetMapping("/type")
    public List<Appointment> findByType(@RequestParam("type")String type){
        return appointmentService.findByType(type);
    }
}