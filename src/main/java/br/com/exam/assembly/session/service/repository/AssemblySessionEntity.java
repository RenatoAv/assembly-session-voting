package br.com.exam.assembly.session.service.repository;

import br.com.exam.assembly.agenda.service.repository.AgendaEntity;
import br.com.exam.assembly.voter.service.repository.VoteEntity;
import jakarta.persistence.*;

import java.util.Set;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

@Entity
@Table(name = "assemblies")
public class AssemblySessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Integer duration;
    private String startedAt;
    private String endAt;

    @OneToOne
    @JoinColumn(name = "agenda_id", referencedColumnName = "id")
    private AgendaEntity agenda;

    @OneToMany(mappedBy = "assemblySession", fetch = FetchType.LAZY)
    private Set<VoteEntity> votes;

    public boolean isAbleToVote() {
        return now().isBefore(parse(endAt, ofPattern("dd-MM-yyyy HH:mm")));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public AgendaEntity getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaEntity agenda) {
        this.agenda = agenda;
    }

    public Set<VoteEntity> getVotes() {
        return votes;
    }

    public void setVotes(Set<VoteEntity> votes) {
        this.votes = votes;
    }
}
