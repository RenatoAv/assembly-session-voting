package br.com.exam.assembly.agenda;

import br.com.exam.assembly.agenda.service.repository.AgendaEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public record AgendaListResponse(@JsonProperty Set<Agenda> agendas) {
    public static AgendaListResponse from(List<AgendaEntity> all) {
        return new AgendaListResponse(all.stream().map(Agenda::from).collect(toSet()));
    }

    public record Agenda(@JsonProperty Long id, @JsonProperty String title, @JsonProperty String description) {
        public static Agenda from(AgendaEntity entity) {
            return new Agenda(entity.getId(), entity.getTitle(), entity.getDescription());
        }
    }
}
