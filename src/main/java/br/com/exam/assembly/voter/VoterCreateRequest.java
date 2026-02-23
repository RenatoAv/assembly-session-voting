package br.com.exam.assembly.voter;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CPF;

public record VoterCreateRequest(@JsonProperty String name,@JsonProperty @CPF(message = "Invalid CPF") String document) {
}
