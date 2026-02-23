package br.com.exam.assembly.session;

import br.com.exam.assembly.agenda.service.repository.AgendaEntity;
import br.com.exam.assembly.session.service.AssemblySessionService;
import br.com.exam.assembly.session.service.repository.AssemblySessionEntity;
import br.com.exam.assembly.session.service.repository.AssemblySessionRepository;
import br.com.exam.assembly.voter.service.repository.VoteEntity;
import br.com.exam.assembly.voter.service.repository.VoterEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AssemblySessionServiceTest {

    @Mock
    private AssemblySessionRepository assemblySessionRepository;

    private AssemblySessionService assemblySessionService;

    @BeforeEach
    public void setup() {
        openMocks(this);
        assemblySessionService = new AssemblySessionService(assemblySessionRepository);
    }

    @Test
    public void shouldReturnResultVoteApprovedWithAllVotes() {
        var mock = mocknAssemblySessionEntity(true);
        when(assemblySessionRepository.findById(any())).thenReturn(Optional.of(mock));
        var response = assemblySessionService.votingResult(1L);
        Assertions.assertEquals("Approved", response.result());
    }

    @Test
    public void shouldReturnResultVoteDeclinedWithAllVotes() {
        var mock = mocknAssemblySessionEntity(false);
        when(assemblySessionRepository.findById(1L)).thenReturn(Optional.of(mock));
        var response = assemblySessionService.votingResult(1L);
        Assertions.assertEquals("Declined", response.result());
    }

    private AssemblySessionEntity mocknAssemblySessionEntity(boolean approved) {
        var agenda = new AgendaEntity();
        var assemblySession = new AssemblySessionEntity();
        var voter = new VoterEntity();
        var vote = new VoteEntity(assemblySession,voter, approved);
        assemblySession.setVotes(Set.of(vote));
        assemblySession.setAgenda(agenda);
        return assemblySession;
    }
}
