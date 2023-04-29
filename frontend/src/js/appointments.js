import {get, post, put} from "@/js/http-requests";
import {handleRequest} from "@/js/request_helpers";


export async function getAppointmentById(appointmentId) {
    return await get(`appointments/${appointmentId}`)
        .then(handleRequest);
}

export async function getUserAppointments(userId) {
    return await get(`appointments/patient/${userId}`)
        .then(handleRequest);
}

export async function getAvailableDoctors(startTime, endTime) {
    return await get(`doctors?startTime=${startTime}&endTime=${endTime}`)
        .then(handleRequest);
}

export async function bookAppointment(appointment) {
    let body = JSON.stringify({...appointment, cancelled: false});
    return await post("appointments", body)
        .then(handleRequest);
}

export async function getDoctorAppointments(userId) {
    return await get(`appointments/doctor/${userId}`)
        .then(handleRequest);
}

export async function updateAppointment(appointmentId, appointment) {
    let body = JSON.stringify(appointment);
    return await put(`appointments/${appointmentId}`, body)
        .then(handleRequest);
}