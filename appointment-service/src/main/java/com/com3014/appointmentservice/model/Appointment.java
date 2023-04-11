package com.com3014.appointmentservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;
import java.time.Instant;



@Document("appointment")
public class Appointment {
    @Id
    private UUID id;
    private UUID docId;
    private Instant startTime;
    private Instant endTime;
    private UUID patientId;
    private String summary;
    private String type;
    private Boolean cancelled;

    public Appointment(UUID docId,Instant startTime,Instant endTime,UUID patientId,String summary,String type,Boolean cancelled)
    {  
       
       
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