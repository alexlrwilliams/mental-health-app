package com.com3014.appointmentservice.model.json;

import org.springframework.data.annotation.Id;
import java.util.UUID;
import java.sql.Time;

public class AppointmentJson 
{

    @Id
     private UUID id;
     private UUID DOC_ID;
     private Time StartTime;
     private Time EndTime;
     private UUID Patient_ID;
     private String summary;
     private String Type;
     private String Status;

     public AppointmentJson(UUID id,UUID DOC_ID,Time StartTime, Time EndTime,UUID Patient_ID,String summary,String Type,String Status)
     {  
        this.id=UUID.randomUUID();
        this.DOC_ID=UUID.randomUUID();
        this.StartTime = StartTime;
        this.EndTime = EndTime;
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
     public Time getStartTime()
     {
        return StartTime;
     }
     public void setStartTime(Time StartTime)
     {
        this.StartTime= StartTime;
     }
     public Time getEndTime(){
        return EndTime;
     }
     public void setEndTime(Time EndTime)
     {
        this.EndTime = EndTime;
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
