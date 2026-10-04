package com.campusfixai.model;

import jakarta.validation.constraints.*;

public final class ApiDtos {
  private ApiDtos(){}
  public record ReportRequest(@NotBlank @Pattern(regexp="(?i)VTU[0-9]{5}", message="VTU No must be VTU followed by exactly five digits, for example VTU33598.") String vtuNo,@NotBlank String studentName,@NotNull Enums.AreaType areaType,String hostel,String hostelBlock,String floor,String roomNo,String universityBlock,@NotBlank String forum,@NotBlank @Size(max=3000) String problem) {}
  public record StatusRequest(@NotNull Enums.IncidentStatus status, Boolean verified) {}
}
