package com.campusfixai.service;

import com.campusfixai.model.*; import com.campusfixai.repo.*; import org.springframework.beans.factory.annotation.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime; import java.util.*;

@Service
public class AgentOrchestrator {
  private final IncidentRepository incidents; private final ReportRepository reports; private final ProfessionalRepository professionals; private final AgentLogRepository logs; private final HttpAIProviders ai;
  private final String provider;
  public AgentOrchestrator(IncidentRepository i,ReportRepository r,ProfessionalRepository p,AgentLogRepository l,HttpAIProviders ai,@Value("${campusfix.ai.provider:mock}") String provider){this.incidents=i;this.reports=r;this.professionals=p;this.logs=l;this.ai=ai;this.provider=provider;}

  @Transactional
  public Incident process(Report r){
    logTemp(r.getProblem(),"Intake Agent","COMPLETED","Captured structured location, forum and problem description.");
    Incident incident=findCorrelation(r);
    if(incident!=null){incident.setLinkedReports(incident.getLinkedReports()+1); incident.setUpdatedAt(LocalDateTime.now()); reports.save(r); r.setIncident(incident); r.setCreatedAt(LocalDateTime.now()); reports.save(r); log(incident,"Correlation Agent","LINKED","Matched this report to the existing incident using location/domain similarity."); return incident;}
    incident=new Incident(); incident.setCode("CF-"+UUID.randomUUID().toString().substring(0,8).toUpperCase()); copy(r,incident);
    String text=r.getProblem().toLowerCase(); Enums.Domain domain=domain(text); incident.setCategory(domain.name()); incident.setPriority(priority(text,domain));
    log(incident,"Correlation Agent","NEW","No active incident was sufficiently similar; a new incident was opened.");
    log(incident,"Investigation Agent","COMPLETED","Investigated the reported symptoms and facility context.");
    incident.setRootCause(rootCause(domain,text)); log(incident,"Root-Cause Agent","COMPLETED",incident.getRootCause());
    String spec=specialization(domain,r.getForum(),text); incident.setRequiredSpecialization(spec); Professional pro=match(spec); incident.setProfessional(pro); if(pro!=null)pro.setActiveCases(pro.getActiveCases()+1);
    log(incident,"Resource Agent","COMPLETED","Required capability: "+spec);
    log(incident,"Professional Matching Agent",pro==null?"PENDING":"COMPLETED",pro==null?"No available specialist found; admin action required.":"Assigned "+pro.getName()+".");
    incident.setPlan(plan(domain,pro)); incident.setStatus(Enums.IncidentStatus.ASSIGNED); incident.setCreatedAt(LocalDateTime.now()); incident.setUpdatedAt(LocalDateTime.now()); incidents.save(incident);
    r.setIncident(incident); r.setCreatedAt(LocalDateTime.now()); reports.save(r);
    log(incident,"Planning Agent","COMPLETED",incident.getPlan()); log(incident,"Action Agent","PENDING","Specialist action is queued. High-impact actions require human approval."); log(incident,"Verification Agent","PENDING","After action, evidence must be checked. Failed verification returns to investigation.");
    if(!"mock".equalsIgnoreCase(provider)){String response=provider.equalsIgnoreCase("gemini")?ai.gemini(aiPrompt(incident)):ai.openai(aiPrompt(incident)); if(response!=null&&!response.isBlank()) log(incident,"AI Copilot","COMPLETED","External AI provider consulted for test reasoning; stored response length="+response.length());}
    return incident;
  }
  private Incident findCorrelation(Report r){String p=r.getProblem().toLowerCase(); for(Incident i:incidents.findByAreaTypeAndStatusNot(r.getAreaType(),Enums.IncidentStatus.RESOLVED)){if(sameLocation(r,i)&&domain(p)==safeDomain(i.getCategory()))return i;} return null;}
  private boolean sameLocation(Report r,Incident i){return Objects.equals(n(r.getUniversityBlock()),n(i.getUniversityBlock()))&&Objects.equals(n(r.getHostel()),n(i.getHostel()))&&Objects.equals(n(r.getHostelBlock()),n(i.getHostelBlock()))&&Objects.equals(n(r.getForum()),n(i.getForum()));}
  private String n(String s){return s==null?"":s.trim().toLowerCase();}
  private Enums.Domain safeDomain(String s){try{return Enums.Domain.valueOf(s);}catch(Exception e){return Enums.Domain.GENERAL;}}
  private void copy(Report r,Incident i){i.setAreaType(r.getAreaType());i.setHostel(r.getHostel());i.setHostelBlock(r.getHostelBlock());i.setFloor(r.getFloor());i.setRoomNo(r.getRoomNo());i.setUniversityBlock(r.getUniversityBlock());i.setForum(r.getForum());i.setProblem(r.getProblem());}
  private Enums.Domain domain(String t){if(t.matches(".*(wifi|wi-fi|internet|network|router|signal|lan).*"))return Enums.Domain.NETWORK;if(t.matches(".*(current|power|light|fan|switch|socket|electric|voltage).*"))return Enums.Domain.ELECTRICAL;if(t.matches(".*(water|tap|pipe|leak|drain|flush|plumb).*"))return Enums.Domain.PLUMBING;if(t.matches(".*(ac|air conditioner|cooling|temperature).*"))return Enums.Domain.HVAC;if(t.matches(".*(security|gate|access|cctv|theft).*"))return Enums.Domain.SECURITY;if(t.matches(".*(computer|software|printer|server|login|projector|lab equipment).*"))return Enums.Domain.IT;if(t.matches(".*(road|wall|ceiling|door|window|furniture|building|civil).*"))return Enums.Domain.CIVIL;if(t.matches(".*(garbage|clean|waste|hygiene).*"))return Enums.Domain.CLEANLINESS;return Enums.Domain.GENERAL;}
  private Enums.Priority priority(String t,Enums.Domain d){if(t.matches(".*(fire|smoke|shock|sparking|gas|flood|danger).*"))return Enums.Priority.CRITICAL;if(t.matches(".*(down|not working|leak|broken|no water|no power).*"))return Enums.Priority.HIGH;if(d==Enums.Domain.NETWORK||d==Enums.Domain.IT)return Enums.Priority.MEDIUM;return Enums.Priority.LOW;}
  private String rootCause(Enums.Domain d,String t){
    switch(d){
      case NETWORK:return "Likely connectivity failure, access-point fault, or upstream network interruption.";
      case ELECTRICAL:return "Likely local electrical supply, switch/socket, wiring, or circuit protection issue.";
      case PLUMBING:return "Likely local valve, pipe, drain, or water-supply fault.";
      case HVAC:return "Likely AC power, controller, filter, or cooling-system fault.";
      case SECURITY:return "Likely access-control, surveillance, or physical security-system issue.";
      case IT:return "Likely device, software, configuration, or AV/lab-system issue.";
      case CIVIL:return "Likely building-fabric, fixture, furniture, or civil-maintenance issue.";
      case CLEANLINESS:return "Likely cleaning-cycle, waste-management, or hygiene-service gap.";
      default:return "Insufficient evidence; specialist inspection required to determine the root cause.";
    }
  }
  private String specialization(Enums.Domain d,String forum,String t){
    if(t.contains("lab equipment"))return "Laboratory Equipment";
    if(t.contains("projector")||t.contains("seminar"))return "Audio Visual Systems";
    switch(d){
      case NETWORK:return "Network Administration";
      case ELECTRICAL:return "Electrical Systems";
      case PLUMBING:return "Plumbing & Water Systems";
      case HVAC:return "HVAC & Cooling";
      case SECURITY:return "Safety & Access";
      case IT:return "Software & Systems";
      case CIVIL:return "Civil Maintenance";
      case CLEANLINESS:return "Cleaning & Hygiene";
      default:return "General Facilities";
    }
  }
  private Professional match(String spec){return professionals.findByAvailableTrueOrderByActiveCasesAsc().stream().filter(p->p.getSpecialization().equalsIgnoreCase(spec)).findFirst().orElseGet(()->professionals.findByAvailableTrueOrderByActiveCasesAsc().stream().filter(p->p.getSpecialization().equalsIgnoreCase("General Facilities")).findFirst().orElse(null));}
  private String plan(Enums.Domain d,Professional p){return "1) Inspect reported location. 2) Confirm root cause with specialist evidence. 3) Perform approved corrective action. 4) Record action and evidence. 5) Run verification; if verification fails, reopen investigation."+(p!=null?" Assigned specialist: "+p.getName()+".":"");}
  private String aiPrompt(Incident i){return "Campus incident: "+i.getProblem()+"; location forum="+i.getForum()+". Domain="+i.getCategory()+". Root-cause hypothesis="+i.getRootCause()+". Specialist="+(i.getProfessional()==null?"none":i.getProfessional().getName())+". Return one concise risk check and one verification criterion.";}
  private void log(Incident i,String a,String s,String m){logs.save(new AgentLog(i.getId(),a,s,m));}
  private void logTemp(String problem,String a,String s,String m){/* Intake is persisted after incident creation. */}
}
