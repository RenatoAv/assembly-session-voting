package br.com.exam.assembly.session;

import br.com.exam.assembly.session.service.repository.AssemblySessionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AssemblySessionCreateResponse(@JsonProperty Long assemblyId,
                                            @JsonProperty String errorMessage,
                                            @JsonProperty int statusCode) {

    public static AssemblySessionCreateResponse from(AssemblySessionEntity entity) {
        return new AssemblySessionCreateResponse(entity.getId(), null, 201);
    }

    public static AssemblySessionCreateResponse errorResponse(String errorMessage) {
        return new AssemblySessionCreateResponse(null, errorMessage, 422);
    }

}
