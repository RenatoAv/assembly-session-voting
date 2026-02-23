package br.com.exam.assembly.voter.service;

import br.com.exam.assembly.voter.service.repository.VoterEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public record VoterCreateResponse(@JsonProperty Long id, @JsonProperty String message, @JsonProperty int statusCode) {
    public static VoterCreateResponse from(VoterEntity voterEntity) {
        return new VoterCreateResponse(voterEntity.getId(), "", 201);
    }

    public static VoterCreateResponse error(String message) {
        return new VoterCreateResponse(null, message, 422);
    }
}
