package br.com.exam.assembly.voter.service.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidateVoterResponse(@JsonProperty String status) {
    public boolean isAbleToVote() {
        return "ABLE_TO_VOTE".equals(status);
    }

    public static ValidateVoterResponse ok() {
        return new ValidateVoterResponse("ABLE_TO_VOTE");
    }
}
