package br.com.exam.assembly.voter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VoteRequest(@JsonProperty Long assemblyId, @JsonProperty boolean approved) {
}
