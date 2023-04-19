import {get, put} from "@/js/http-requests";
import {handleRequest} from "@/js/request_helpers";

export async function getUserAppointments(userId) {
    return await get(`appointments/patient/${userId}`)
        .then(handleRequest);
}

export async function updateAppointment(appointmentId, appointment) {
    let body = JSON.stringify(appointment);
    return await put(`appointments/${appointmentId}`, body)
        .then(handleRequest);
}