package br.com.exam.assembly.voter;


import br.com.exam.assembly.voter.service.VoterCreateResponse;
import br.com.exam.assembly.voter.service.VoterService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.exam.assembly.voter.VoteResponse.error;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/v1/voters")
public class VoterController {

    private static Logger logger = LoggerFactory.getLogger(VoterController.class);

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @GetMapping
    public ResponseEntity<VoterListResponse> list() {
        try {
            return ok(voterService.listAllVoters());
        } catch (Exception e) {
            logger.error("Error when listing voters", e);
            return internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<VoterCreateResponse> create(@Valid @RequestBody VoterCreateRequest voterCreateRequest) {
        try {
            var response = voterService.createVoter(voterCreateRequest);
            return ok(response);
        } catch (Exception e) {
            logger.error("Error when creating voter");
            return internalServerError().build();
        }
    }

    @PostMapping("/{voterId}/vote")
    public ResponseEntity<VoteResponse> vote(@PathVariable Long voterId, @RequestBody VoteRequest voteRequest) {
        try {
            var voteResponse = voterService.vote(voteRequest, voterId);
            return status(voteResponse.statusCode()).body(voteResponse);
        } catch (Exception e) {
            logger.error("Error when voting for voterId: {} for assemblySessionId: {}", voterId, voteRequest.assemblyId(), e);
            return internalServerError().body(error(e.getMessage()));
        }
    }


}
