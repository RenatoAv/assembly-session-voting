package br.com.exam.assembly.session;

import br.com.exam.assembly.agenda.AgendaController;
import br.com.exam.assembly.session.service.AssemblySessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/assemblies")
public class AssemblySessionController {

    private static Logger logger = LoggerFactory.getLogger(AgendaController.class);

    private AssemblySessionService assemblySessionService;

    public AssemblySessionController(AssemblySessionService assemblySessionService) {
        this.assemblySessionService = assemblySessionService;
    }

    @PostMapping
    public ResponseEntity<AssemblySessionCreateResponse> create(@RequestBody AssemblySessionCreateRequest assemblyRequest) {
        try {
            var response = assemblySessionService.create(assemblyRequest);
            return ResponseEntity.status(response.statusCode()).body(response);
        } catch (Exception e) {
            logger.error("Error when creating assembly session", e);
            return internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<AssemblySessionListResponse> list() {
        try {
            return ok(assemblySessionService.listAllAssemblies());
        } catch (Exception e) {
            logger.error("Error when listing assemblies", e);
            return internalServerError().build();
        }
    }

    @GetMapping("/{assemblyId}/result")
    public ResponseEntity<AssemblyVotingResponse> votingResult(@PathVariable Long assemblyId) {
        try {
            return ok(assemblySessionService.votingResult(assemblyId));
        } catch (Exception e) {
            logger.error("Error when listing assemblies", e);
            return internalServerError().build();
        }
    }
}
