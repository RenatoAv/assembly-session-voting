package br.com.exam.assembly.agenda.service;

import br.com.exam.assembly.agenda.AgendaCreateRequest;
import br.com.exam.assembly.agenda.AgendaCreateResponse;
import br.com.exam.assembly.agenda.AgendaListResponse;
import br.com.exam.assembly.agenda.service.repository.AgendaEntity;
import br.com.exam.assembly.agenda.service.repository.AgendaRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import static br.com.exam.assembly.agenda.AgendaCreateResponse.from;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public AgendaCreateResponse create(AgendaCreateRequest request) {
        AgendaEntity agendaEntity = new AgendaEntity(request.title(), request.description());
        return from(agendaRepository.save(agendaEntity));
    }

    public AgendaListResponse listAllAgendas() {
       return AgendaListResponse.from(this.agendaRepository.findAll());
    }
}
