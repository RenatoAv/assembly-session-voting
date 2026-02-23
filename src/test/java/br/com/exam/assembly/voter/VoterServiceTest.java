package br.com.exam.assembly.voter;

import br.com.exam.assembly.session.service.AssemblySessionService;
import br.com.exam.assembly.voter.service.VoterService;
import br.com.exam.assembly.voter.service.client.ValidateVoterResponse;
import br.com.exam.assembly.voter.service.client.VoterValidateClient;
import br.com.exam.assembly.voter.service.repository.VoteRepository;
import br.com.exam.assembly.voter.service.repository.VoterEntity;
import br.com.exam.assembly.voter.service.repository.VoterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.MockitoAnnotations.openMocks;

public class VoterServiceTest {

    @Mock
    private VoterRepository voterRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AssemblySessionService assemblySessionService;

    @Mock
    private VoterValidateClient voterValidateClient;

    private VoterService voterService;

    @BeforeEach
    public void setup() {
        openMocks(this);
        voterService = new VoterService(voteRepository, voterRepository, assemblySessionService, voterValidateClient);
    }

    @Test
    public void shouldSuccessfullyVoteMessage() {
        Mockito.when(voterRepository.findById(anyLong())).thenReturn(Optional.of(new VoterEntity()));
        Mockito.when(voterValidateClient.call(any())).thenReturn(new ValidateVoterResponse("ABLE_TO_VOTE"));
        Mockito.when(voteRepository.existsByAssemblySessionIdAndVoterId(any(), any())).thenReturn(false);
        Mockito.when(assemblySessionService.checkOngoingStatus(any())).thenReturn(true);
        VoteRequest request = new VoteRequest(1L, true);
        var result = voterService.vote(request,1L);
        assertEquals("Vote confirmed!", result.message());

    }

    @Test
    public void shouldReturnNotAbleToVoteErrorMessage() {
        Mockito.when(voterRepository.findById(anyLong())).thenReturn(Optional.of(new VoterEntity()));
        Mockito.when(voterValidateClient.call(any())).thenReturn(new ValidateVoterResponse("UNABLE_TO_VOTE"));
        Mockito.when(voteRepository.existsByAssemblySessionIdAndVoterId(any(), any())).thenReturn(false);
        Mockito.when(assemblySessionService.checkOngoingStatus(any())).thenReturn(true);
        VoteRequest request = new VoteRequest(1L, true);
        var result = voterService.vote(request,1L);
        assertEquals("voter is not valid!", result.message());

    }

    @Test
    public void shouldReturnHasAlreadyVotedErrorMessage() {
        Mockito.when(voterRepository.findById(anyLong())).thenReturn(Optional.of(new VoterEntity()));
        Mockito.when(voterValidateClient.call(any())).thenReturn(new ValidateVoterResponse("ABLE_TO_VOTE"));
        Mockito.when(voteRepository.existsByAssemblySessionIdAndVoterId(any(), any())).thenReturn(true);
        Mockito.when(assemblySessionService.checkOngoingStatus(any())).thenReturn(true);
        VoteRequest request = new VoteRequest(1L, true);
        var result = voterService.vote(request,1L);
        assertEquals("the given voterId has already voted for this assembly!", result.message());

    }

    @Test
    public void shouldReturnVotingClosedErrorMessage() {
        Mockito.when(voterRepository.findById(anyLong())).thenReturn(Optional.of(new VoterEntity()));
        Mockito.when(voterValidateClient.call(any())).thenReturn(new ValidateVoterResponse("ABLE_TO_VOTE"));
        Mockito.when(voteRepository.existsByAssemblySessionIdAndVoterId(any(), any())).thenReturn(false);
        Mockito.when(assemblySessionService.checkOngoingStatus(any())).thenReturn(false);
        VoteRequest request = new VoteRequest(1L, true);
        var result = voterService.vote(request,1L);
        assertEquals("assembly session is closed!", result.message());

    }
}
