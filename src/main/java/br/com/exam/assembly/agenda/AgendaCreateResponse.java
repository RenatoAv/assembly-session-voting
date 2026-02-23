package br.com.exam.assembly.agenda;

import br.com.exam.assembly.agenda.service.repository.AgendaEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record AgendaCreateResponse(@JsonProperty Long id) implements Serializable {
    public static AgendaCreateResponse from(AgendaEntity agendaEntity) {
        return new AgendaCreateResponse(agendaEntity.getId());
    }
}
