package br.com.exam.assembly.agenda;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AgendaCreateRequest(@JsonProperty String title, @JsonProperty String description) {
}
