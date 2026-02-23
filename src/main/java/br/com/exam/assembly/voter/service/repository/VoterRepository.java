package br.com.exam.assembly.voter.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<VoterEntity, Long> {
    boolean existsByDocument(String document);
}
