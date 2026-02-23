package br.com.exam.assembly.voter.service;

import br.com.exam.assembly.session.service.AssemblySessionService;
import br.com.exam.assembly.session.service.repository.AssemblySessionEntity;
import br.com.exam.assembly.voter.VoteRequest;
import br.com.exam.assembly.voter.VoteResponse;
import br.com.exam.assembly.voter.VoterCreateRequest;
import br.com.exam.assembly.voter.VoterListResponse;
import br.com.exam.assembly.voter.service.client.ValidateVoterResponse;
import br.com.exam.assembly.voter.service.client.VoterValidateClient;
import br.com.exam.assembly.voter.service.repository.VoteEntity;
import br.com.exam.assembly.voter.service.repository.VoteRepository;
import br.com.exam.assembly.voter.service.repository.VoterEntity;
import br.com.exam.assembly.voter.service.repository.VoterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.exam.assembly.voter.VoteResponse.error;
import static br.com.exam.assembly.voter.VoteResponse.ok;
import static br.com.exam.assembly.voter.service.VoterCreateResponse.from;

@Service
public class VoterService {

    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final AssemblySessionService assemblySessionService;
    private final VoterValidateClient voterValidateClient;

    public VoterService(VoteRepository voteRepository, VoterRepository voterRepository, AssemblySessionService assemblySessionService, VoterValidateClient voterValidateClient) {
        this.voteRepository = voteRepository;
        this.voterRepository = voterRepository;
        this.assemblySessionService = assemblySessionService;
        this.voterValidateClient = voterValidateClient;
    }

    public VoterCreateResponse createVoter(VoterCreateRequest request) {
        if(voterRepository.existsByDocument(request.document()))
            return VoterCreateResponse.error("this document is already registered!");

        VoterEntity voter = new VoterEntity(request.name(), request.document());
        return from(voterRepository.saveAndFlush(voter));
    }

    public VoterListResponse listAllVoters() {
        return VoterListResponse.from(voterRepository.findAll());
    }

    public VoteResponse vote(VoteRequest voteRequest, Long voterId) {
        if(isInvalidVoter(voterId)) return error("voter is not valid!");
        if(hasAlreadyVoted(voteRequest.assemblyId(), voterId)) return error("the given voterId has already voted for this assembly!");
        if(isVotingClosed(voteRequest.assemblyId())) return error("assembly session is closed!");
        confirmVote(voterId, voteRequest);
        return ok();
    }

    public boolean isInvalidVoter(Long voterId) {
        Optional<VoterEntity> voter = voterRepository.findById(voterId);
        return !voter.map(voterValidateClient::call)
                .map(ValidateVoterResponse::isAbleToVote)
                .orElse(false);
    }

    public boolean isVotingClosed(Long assemblySessionId) {
        return !assemblySessionService.checkOngoingStatus(assemblySessionId);
    }

    public boolean hasAlreadyVoted(Long assemblySessionId, Long voterId) {
        return voteRepository.existsByAssemblySessionIdAndVoterId(assemblySessionId, voterId);
    }

    public void confirmVote(Long voterId, VoteRequest voteRequest) {
        VoterEntity voterEntity = new VoterEntity();
        AssemblySessionEntity assemblySession = new AssemblySessionEntity();
        voterEntity.setId(voterId);
        assemblySession.setId(voteRequest.assemblyId());
        voteRepository.saveAndFlush(new VoteEntity(assemblySession, voterEntity, voteRequest.approved()));
    }
}
