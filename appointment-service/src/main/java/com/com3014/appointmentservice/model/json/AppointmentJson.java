package com.com3014.appointmentservice.model.json;


import java.util.UUID;
import java.sql.Time;

public class AppointmentJson 
{

    
     private UUID id;
     private UUID docId;
     private Time startTime;
     private Time endTime;
     private UUID patientId;
     private String summary;
     private String type;
     private String status;

     public AppointmentJson(UUID docId,Time startTime, Time endTime,UUID patientId,String summary,String type,String status) {  
        
        this.docId= docId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientId= patientId;
        this.summary = summary;
        this.type = type;
        this.status = status;

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
     public Time getstartTime(){
        return startTime;
     }
     public void setstartTime(Time startTime){
       this.startTime= startTime;
     }
     public Time getendTime(){
        return endTime;
     }
     public void setendTime(Time endTime){
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
     public String getstatus(){
        return status;
     }
     public void setstatus(String status){
        this.status = status;
     }
}
