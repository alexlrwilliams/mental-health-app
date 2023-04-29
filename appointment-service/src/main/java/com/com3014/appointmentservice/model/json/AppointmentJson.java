package com.com3014.appointmentservice.model.json;


import java.util.UUID;
import java.time.Instant;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class AppointmentJson 
{
     @NotNull
     private UUID docId;
     @FutureOrPresent
     private Instant startTime;
     @FutureOrPresent
     private Instant endTime;
     @NotNull
     private UUID patientId;
     @NotNull
     private String summary;
     @NotNull
     private String type;
     @NotNull
     private Boolean cancelled;

     public AppointmentJson(UUID docId,Instant startTime,Instant endTime,UUID patientId,String summary,String type,Boolean cancelled) {  
        
        this.docId= docId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientId= patientId;
        this.summary = summary;
        this.type = type;
        this.cancelled = cancelled;

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
     public void setendTime(Instant endTime){
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
     public boolean getcancelled(){
        return cancelled;
     }
     public void setcancelled(Boolean cancelled){
        this.cancelled = cancelled;
     }
}
