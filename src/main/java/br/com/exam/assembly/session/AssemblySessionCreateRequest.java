package br.com.exam.assembly.session;

import br.com.exam.assembly.agenda.service.repository.AgendaEntity;
import br.com.exam.assembly.session.service.repository.AssemblySessionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public record AssemblySessionCreateRequest(@JsonProperty Long agendaId,
                                           @JsonProperty Integer duration) {
    
    public AssemblySessionEntity toAssemblySessionEntity() {
        final int DEFAULT_DURATION = 1;
        int duration = duration() == null || duration() == 0 ? DEFAULT_DURATION : duration();
        DateTimeFormatter pattern = ofPattern("dd-MM-yyyy HH:mm");

        AgendaEntity agendaEntity = new AgendaEntity();
        agendaEntity.setId(this.agendaId());

        AssemblySessionEntity assemblySessionEntity = new AssemblySessionEntity();
        assemblySessionEntity.setAgenda(agendaEntity);
        assemblySessionEntity.setDuration(duration);
        assemblySessionEntity.setStartedAt(now().format(pattern));
        assemblySessionEntity.setEndAt(now().plusMinutes(duration).format(pattern));

        return assemblySessionEntity;
    }
}
