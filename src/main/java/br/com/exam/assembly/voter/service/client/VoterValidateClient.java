package br.com.exam.assembly.voter.service.client;


import br.com.exam.assembly.voter.service.repository.VoterEntity;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static br.com.exam.assembly.voter.service.client.ValidateVoterResponse.ok;
import static java.lang.Boolean.parseBoolean;

@Repository
public class VoterValidateClient {

    private final RestClient restClient;
    private final Environment environment;

    public VoterValidateClient(Environment environment) {
        this.restClient = RestClient.create();
        this.environment = environment;
    }

    public ValidateVoterResponse call(VoterEntity voter){
        String uriBase = environment.getProperty("votervalidate.api.uri");
        boolean integrationEnabled = parseBoolean(environment.getProperty("votervalidate.api.enabled"));

        if(integrationEnabled){
            try {
                return restClient.get()
                        .uri(uriBase + "/users/" + voter.getDocument())
                        .retrieve()
                        .body(ValidateVoterResponse.class);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                    throw new RuntimeException("API de integração do desafio está fora!");
                throw e;
            }
        }

        return ok();
    }
}
