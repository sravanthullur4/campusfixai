package com.campusfixai.model;

import jakarta.persistence.*;

@Entity
@Table(name="professionals")
public class Professional {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String name;
  @Column(nullable=false) private String department;
  @Column(nullable=false) private String specialization;
  private String assignedZone;
  private boolean available = true;
  private int activeCases = 0;
  public Professional() {}
  public Professional(String name,String department,String specialization,String assignedZone){this.name=name;this.department=department;this.specialization=specialization;this.assignedZone=assignedZone;}
  public Long getId(){return id;} public String getName(){return name;} public String getDepartment(){return department;} public String getSpecialization(){return specialization;} public String getAssignedZone(){return assignedZone;} public boolean isAvailable(){return available;} public int getActiveCases(){return activeCases;}
  public void setAvailable(boolean v){available=v;} public void setActiveCases(int v){activeCases=v;}
}
