package com.com3014.appointmentservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;
import java.sql.Time;



@Document("appointment")
public class Appointment {
    @Id
    private UUID id;
    private UUID DOC_ID;
    private Time appointmentStartTime;
    private Time appointmentEndTime;
    private UUID Patient_ID;
    private String summary;
    private String Type;
    private String Status;

    public Appointment(UUID id,UUID DOC_ID,Time appointmentStartTime, Time appointmentEndTime,UUID Patient_ID,String summary,String Type,String Status)
    {  
       
       this.id=UUID.randomUUID();
       this.DOC_ID=UUID.randomUUID();
       this.appointmentStartTime = appointmentStartTime;
       this.appointmentEndTime = appointmentEndTime;
       this.Patient_ID=UUID.randomUUID();
       this.summary = summary;
       this.Type = Type;
       this.Status = Status;

    }
    public UUID getid(){
        return id;
     }
     public void setid(UUID id){
        this.id = id;

     }
     public UUID getDOC_ID(){
        return DOC_ID;
     }
     public void setDOC_ID(UUID DOC_ID)
     {
        this.DOC_ID =DOC_ID;
     }
     public Time getappointmentStartTime()
     {
        return appointmentStartTime;
     }
     public void setappointmentStartTime(Time appointmentStartTime)
     {
        this.appointmentStartTime= appointmentStartTime;
     }
     public Time getappointmentEndTime(){
        return appointmentEndTime;
     }
     public void setappointmentEndTime(Time appointmentEndTime)
     {
        this.appointmentEndTime = appointmentEndTime;
     }
     public UUID getPatient_ID(){
        return Patient_ID;
     }
     public void setPatient_ID(UUID Patient_ID)
     {
        this.Patient_ID = Patient_ID;
     }
     public String getsummary()
     {
        return summary;
     }
     public void setsummary(String summary)
     {
        this.summary = summary;
     }
     public String getType()
     {
        return Type;
     }
     public void setType(String Type)
     {
        this.Type = Type;
     }
     public String getStatus()
     {
        return Status;
     }
     public void setStatus(String Status)
     {
        this.Status = Status;
     }
}
