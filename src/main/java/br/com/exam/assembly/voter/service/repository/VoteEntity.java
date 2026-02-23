package br.com.exam.assembly.voter.service.repository;

import br.com.exam.assembly.session.service.repository.AssemblySessionEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assembly_id", referencedColumnName = "id")
    private AssemblySessionEntity assemblySession;

    @ManyToOne
    @JoinColumn(name = "voter_id", referencedColumnName = "id")
    private VoterEntity voter;

    private boolean approved;

    public VoteEntity() {
    }

    public VoteEntity(AssemblySessionEntity assemblySession, VoterEntity voter, boolean approved) {
        this.assemblySession = assemblySession;
        this.voter = voter;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssemblySessionEntity getAssemblySession() {
        return assemblySession;
    }

    public void setAssemblySession(AssemblySessionEntity assemblySession) {
        this.assemblySession = assemblySession;
    }

    public VoterEntity getVoter() {
        return voter;
    }

    public void setVoter(VoterEntity voter) {
        this.voter = voter;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
