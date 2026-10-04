package com.campusfixai.repo;
import com.campusfixai.model.Professional; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*; public interface ProfessionalRepository extends JpaRepository<Professional,Long>{ List<Professional> findByAvailableTrueOrderByActiveCasesAsc(); }
