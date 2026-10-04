package com.campusfixai.repo;
import com.campusfixai.model.Report; import org.springframework.data.jpa.repository.JpaRepository; public interface ReportRepository extends JpaRepository<Report,Long>{ long countByIncidentId(Long id); }
