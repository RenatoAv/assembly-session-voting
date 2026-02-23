package br.com.exam.assembly.agenda;

import br.com.exam.assembly.agenda.service.AgendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/agendas")
public class AgendaController {
    private static Logger logger = LoggerFactory.getLogger(AgendaController.class);

    private AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public ResponseEntity<AgendaCreateResponse> create(@RequestBody AgendaCreateRequest agendaCreateRequest) {
        try {
            ;
            return ok(agendaService.create(agendaCreateRequest));
        } catch (Exception e) {
            logger.error("Error when creating agenda", e);
            return internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<AgendaListResponse> list() {
        try {
            return ok(agendaService.listAllAgendas());
        } catch (Exception e) {
            logger.error("Error when listing agendas", e);
            return internalServerError().build();
        }
    }
}
