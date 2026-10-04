package com.campusfixai.model;

public final class Enums {
  private Enums() {}
  public enum AreaType { UNIVERSITY, HOSTEL }
  public enum Domain { NETWORK, ELECTRICAL, PLUMBING, HVAC, SECURITY, IT, CIVIL, CLEANLINESS, GENERAL }
  public enum Priority { LOW, MEDIUM, HIGH, CRITICAL }
  public enum IncidentStatus { ASSIGNED, INVESTIGATING, ACTION_REQUIRED, RESOLVED, REOPENED }
}
