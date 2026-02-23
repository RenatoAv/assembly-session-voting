package br.com.exam.assembly.agenda;

import br.com.exam.assembly.agenda.service.AgendaService;
import br.com.exam.assembly.agenda.service.repository.AgendaEntity;
import br.com.exam.assembly.agenda.service.repository.AgendaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    private AgendaService agendaService;

    @BeforeEach
    public void setup() {
        openMocks(this);
        agendaService = new AgendaService(agendaRepository);
    }

    @Test
    public void shouldListAllAgendas() {
        when(agendaRepository.findAll()).thenReturn(mockListAllAgendas());
        var result = agendaService.listAllAgendas();
        Assertions.assertNotNull(result);
    }

    public List<AgendaEntity> mockListAllAgendas() {
        List<AgendaEntity> agendas = new ArrayList<>();
        agendas.add(new AgendaEntity("title1", "description1"));
        agendas.add(new AgendaEntity("title2", "description2"));
        return agendas;
    }
}
