package br.com.exam.assembly.session;

import br.com.exam.assembly.session.service.repository.AssemblySessionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record AssemblySessionListResponse(@JsonProperty Set<Assembly> assemblies) {

    public static AssemblySessionListResponse from(List<AssemblySessionEntity> entities) {
        return new AssemblySessionListResponse(entities.stream().map(Assembly::from).collect(Collectors.toSet()));
    }

    public record Assembly(@JsonProperty int duration,
                           @JsonProperty String startedAt,
                           @JsonProperty String endAt) {


        public static Assembly from(AssemblySessionEntity entity) {
            return new Assembly(entity.getDuration(), entity.getStartedAt(), entity.getEndAt());
        }

    }
}
