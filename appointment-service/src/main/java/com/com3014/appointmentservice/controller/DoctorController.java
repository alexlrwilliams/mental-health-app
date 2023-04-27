package com.com3014.appointmentservice.controller;

import com.com3014.appointmentservice.model.Appointment;
import com.com3014.appointmentservice.model.json.JsonUser;
import com.com3014.appointmentservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final  AppointmentController appointmentController;
    private final UserService userService;
    @Autowired
    public DoctorController(AppointmentController appointmentService, UserService userService) {
        this.appointmentController = appointmentService;
        this.userService = userService;
    }

    @GetMapping
    public List<JsonUser> getAvailableDoctors(@RequestParam Instant startTime, @RequestParam Instant endTime) {
        List<UUID> unAvailableDoctors = appointmentController
                .getAllAppointments(startTime, endTime)
                .stream()
                .map(Appointment::getdocId)
                .toList();
        var allDoctors = userService.getAllDoctors();

        return allDoctors
                .stream()
                .filter(doctor -> !unAvailableDoctors.contains(doctor.getId()))
                .toList();
    }
}