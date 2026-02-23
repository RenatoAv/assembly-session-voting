package br.com.exam.assembly.voter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VoteResponse(@JsonProperty String message, @JsonProperty int statusCode) {

    public static VoteResponse error(String errorMessage) {
        return new VoteResponse(errorMessage, 422);
    }

    public static VoteResponse ok() {
        return new VoteResponse("Vote confirmed!", 201);
    }
}
