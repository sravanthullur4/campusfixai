package com.campusfixai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="agent_logs")
public class AgentLog {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private Long incidentId; private String agent; private String status;
  @Column(length=4000) private String message; private LocalDateTime createdAt;
  public AgentLog(){}
  public AgentLog(Long incidentId,String agent,String status,String message){this.incidentId=incidentId;this.agent=agent;this.status=status;this.message=message;this.createdAt=LocalDateTime.now();}
  public Long getId(){return id;} public Long getIncidentId(){return incidentId;} public String getAgent(){return agent;} public String getStatus(){return status;} public String getMessage(){return message;} public LocalDateTime getCreatedAt(){return createdAt;}
}
