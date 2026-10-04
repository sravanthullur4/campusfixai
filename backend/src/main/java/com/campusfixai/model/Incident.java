package com.campusfixai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="incidents")
public class Incident {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false,unique=true) private String code;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Enums.AreaType areaType;
  private String hostel; private String hostelBlock; private String floor; private String roomNo; private String universityBlock; private String forum;
  @Column(nullable=false,length=3000) private String problem;
  private String category; private String rootCause; private String requiredSpecialization;
  @Enumerated(EnumType.STRING) private Enums.Priority priority;
  @Enumerated(EnumType.STRING) private Enums.IncidentStatus status;
  @ManyToOne private Professional professional;
  private int linkedReports = 1;
  @Column(length=4000) private String plan;
  private boolean verified;
  private LocalDateTime createdAt; private LocalDateTime updatedAt;
  public Incident() {}
  public Long getId(){return id;} public String getCode(){return code;} public Enums.AreaType getAreaType(){return areaType;} public String getHostel(){return hostel;} public String getHostelBlock(){return hostelBlock;} public String getFloor(){return floor;} public String getRoomNo(){return roomNo;} public String getUniversityBlock(){return universityBlock;} public String getForum(){return forum;} public String getProblem(){return problem;} public String getCategory(){return category;} public String getRootCause(){return rootCause;} public String getRequiredSpecialization(){return requiredSpecialization;} public Enums.Priority getPriority(){return priority;} public Enums.IncidentStatus getStatus(){return status;} public Professional getProfessional(){return professional;} public int getLinkedReports(){return linkedReports;} public String getPlan(){return plan;} public boolean isVerified(){return verified;} public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
  public void setCode(String v){code=v;} public void setAreaType(Enums.AreaType v){areaType=v;} public void setHostel(String v){hostel=v;} public void setHostelBlock(String v){hostelBlock=v;} public void setFloor(String v){floor=v;} public void setRoomNo(String v){roomNo=v;} public void setUniversityBlock(String v){universityBlock=v;} public void setForum(String v){forum=v;} public void setProblem(String v){problem=v;} public void setCategory(String v){category=v;} public void setRootCause(String v){rootCause=v;} public void setRequiredSpecialization(String v){requiredSpecialization=v;} public void setPriority(Enums.Priority v){priority=v;} public void setStatus(Enums.IncidentStatus v){status=v;} public void setProfessional(Professional v){professional=v;} public void setLinkedReports(int v){linkedReports=v;} public void setPlan(String v){plan=v;} public void setVerified(boolean v){verified=v;} public void setCreatedAt(LocalDateTime v){createdAt=v;} public void setUpdatedAt(LocalDateTime v){updatedAt=v;}
}
