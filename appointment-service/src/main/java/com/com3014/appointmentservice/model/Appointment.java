package com.com3014.appointmentservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;



@Document("appointment")
public class Appointment {
    @Id
    private UUID id;
    @NotNull
    private UUID docId;
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    @NotNull
    private UUID patientId;
    @NotBlank
    private String summary;
    @NotBlank
    private String type;
    @NotNull
    private Boolean cancelled;

    public Appointment(UUID docId,Instant startTime,Instant endTime,UUID patientId,String summary,String type,Boolean cancelled) {
        this.id = UUID.randomUUID();
        this.docId=docId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientId= patientId;
        this.summary = summary;
        this.type = type;
        this.cancelled = cancelled;
    }
    public UUID getid(){
        return id;
     }
     public void setid(UUID id){
        this.id = id;

     }
     public UUID getdocId(){
        return docId;
     }
     public void setdocId(UUID docId){
        this.docId =docId;
     }
     public Instant getstartTime(){
        return startTime;
     }
     public void setstartTime(Instant startTime){
        this.startTime= startTime;
     }
     public Instant getendTime(){
        return endTime;
     }
     public void setendTime(Instant endTime)
     {
        this.endTime = endTime;
     }
     public UUID getpatientId(){
        return patientId;
     }
     public void setpatientId(UUID patientId){
        this.patientId = patientId;
     }
     public String getsummary(){
        return summary;
     }
     public void setsummary(String summary){
        this.summary = summary;
     }
     public String gettype(){
        return type;
     }
     public void settype(String type){
        this.type = type;
     }
     public Boolean getcancelled(){
        return cancelled;
     }
     public void setcancelled(Boolean cancelled) {
        this.cancelled = cancelled;
     }
}