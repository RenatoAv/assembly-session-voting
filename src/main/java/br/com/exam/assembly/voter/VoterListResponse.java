package br.com.exam.assembly.voter;

import br.com.exam.assembly.voter.service.repository.VoterEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record VoterListResponse(@JsonProperty Set<Voter> voters) implements Serializable {


    public static VoterListResponse from(List<VoterEntity> voters) {
        return new VoterListResponse(voters.stream().map(Voter::from).collect(Collectors.toSet()));
    }

    public record Voter(@JsonProperty Long id, @JsonProperty String name) {

        public static Voter from(VoterEntity entityVoter) {
            return new Voter(entityVoter.getId(), entityVoter.getName());
        }
    }
}
