package br.com.exam.assembly.session.service;

import br.com.exam.assembly.session.AssemblySessionCreateRequest;
import br.com.exam.assembly.session.AssemblySessionCreateResponse;
import br.com.exam.assembly.session.AssemblySessionListResponse;
import br.com.exam.assembly.session.AssemblyVotingResponse;
import br.com.exam.assembly.session.service.repository.AssemblySessionEntity;
import br.com.exam.assembly.session.service.repository.AssemblySessionRepository;
import org.springframework.stereotype.Service;

import static br.com.exam.assembly.session.AssemblySessionCreateResponse.errorResponse;
import static br.com.exam.assembly.session.AssemblySessionCreateResponse.from;

@Service
public class AssemblySessionService {

    private final AssemblySessionRepository assemblySessionRepository;

    public AssemblySessionService(AssemblySessionRepository assemblySessionRepository) {
        this.assemblySessionRepository = assemblySessionRepository;
    }

    public AssemblySessionCreateResponse create(AssemblySessionCreateRequest assemblyRequest) {
        boolean assemblyForAgendaAlreadyExists = assemblySessionRepository.existsByAgendaId(assemblyRequest.agendaId());
        if(assemblyForAgendaAlreadyExists) return errorResponse("An assembly session with this agendaId already exists!");
        return from(assemblySessionRepository.saveAndFlush(assemblyRequest.toAssemblySessionEntity()));
    }

    public AssemblySessionListResponse listAllAssemblies() {
        return AssemblySessionListResponse.from(assemblySessionRepository.findAll());
    }

    public AssemblyVotingResponse votingResult(Long assemblySessionId) {
        return assemblySessionRepository.findById(assemblySessionId)
                .map(AssemblyVotingResponse::from)
                .orElse(null);
    }

    public boolean checkOngoingStatus(Long assemblySessionId) {
        return assemblySessionRepository.findById(assemblySessionId).map(AssemblySessionEntity::isAbleToVote).orElse(false);
    }
}
