package br.com.exam.assembly.session.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssemblySessionRepository extends JpaRepository<AssemblySessionEntity, Long> {
    boolean existsByAgendaId(Long id);


}
