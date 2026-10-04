package com.campusfixai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="reports")
public class Report {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String vtuNo; private String studentName;
  @Enumerated(EnumType.STRING) private Enums.AreaType areaType;
  private String hostel; private String hostelBlock; private String floor; private String roomNo; private String universityBlock; private String forum;
  @Column(nullable=false,length=3000) private String problem;
  @ManyToOne private Incident incident;
  private LocalDateTime createdAt;
  public Report() {}
  public Long getId(){return id;} public String getVtuNo(){return vtuNo;} public String getStudentName(){return studentName;} public Enums.AreaType getAreaType(){return areaType;} public String getHostel(){return hostel;} public String getHostelBlock(){return hostelBlock;} public String getFloor(){return floor;} public String getRoomNo(){return roomNo;} public String getUniversityBlock(){return universityBlock;} public String getForum(){return forum;} public String getProblem(){return problem;} public Incident getIncident(){return incident;} public LocalDateTime getCreatedAt(){return createdAt;}
  public void setVtuNo(String v){vtuNo=v;} public void setStudentName(String v){studentName=v;} public void setAreaType(Enums.AreaType v){areaType=v;} public void setHostel(String v){hostel=v;} public void setHostelBlock(String v){hostelBlock=v;} public void setFloor(String v){floor=v;} public void setRoomNo(String v){roomNo=v;} public void setUniversityBlock(String v){universityBlock=v;} public void setForum(String v){forum=v;} public void setProblem(String v){problem=v;} public void setIncident(Incident v){incident=v;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
