package com.campusfixai.repo;
import com.campusfixai.model.Incident; import com.campusfixai.model.Enums; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface IncidentRepository extends JpaRepository<Incident,Long>{ List<Incident> findTop50ByOrderByCreatedAtDesc(); List<Incident> findByAreaTypeAndStatusNot(Enums.AreaType area,Enums.IncidentStatus status); }
