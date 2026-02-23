package br.com.exam.assembly.session;

import br.com.exam.assembly.session.service.repository.AssemblySessionEntity;
import br.com.exam.assembly.voter.service.repository.VoteEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;

public record AssemblyVotingResponse(@JsonProperty String title, @JsonProperty String result, @JsonProperty Set<Vote> votes) {
    public static AssemblyVotingResponse from(AssemblySessionEntity entity) {
        return new AssemblyVotingResponse(entity.getAgenda().getTitle(),
                voteResult(entity.getVotes()),
                entity.getVotes().stream().map(Vote::from).collect(toSet()));
    }

    public static String voteResult(Set<VoteEntity> votes) {
        return votes.stream().collect(groupingBy(VoteEntity::isApproved, counting())).entrySet().stream()
                .max(comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .map(result -> result ? "Approved": "Declined")
                .orElseThrow();
    }

    public record Vote(@JsonProperty String name, @JsonProperty String document, @JsonProperty boolean approved) {
        public static Vote from(VoteEntity entity) {
            return new Vote(entity.getVoter().getName(), entity.getVoter().getDocument(), entity.isApproved());
        }
    }
}
