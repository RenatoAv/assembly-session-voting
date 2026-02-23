package br.com.exam.assembly.voter.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    boolean existsByAssemblySessionIdAndVoterId(Long assemblySessionId, Long voterId);
}
