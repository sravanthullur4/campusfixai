package com.campusfixai.repo;
import com.campusfixai.model.AgentLog; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*; public interface AgentLogRepository extends JpaRepository<AgentLog,Long>{ List<AgentLog> findByIncidentIdOrderByCreatedAtAsc(Long id); }
