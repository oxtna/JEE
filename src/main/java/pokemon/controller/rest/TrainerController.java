package pokemon.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import pokemon.dto.*;
import pokemon.entity.Trainer;
import pokemon.service.TrainerService;

import java.util.UUID;

@Path("")
public class TrainerController {
    private final TrainerService trainerService;
    private final UriInfo uriInfo;
    private HttpServletResponse response;
    @Context
    private SecurityContext securityContext;

    @Inject
    public TrainerController(TrainerService trainerService, UriInfo uriInfo) {
        this.trainerService = trainerService;
        this.uriInfo = uriInfo;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @GET
    @Path("/trainer")
    @Produces(MediaType.APPLICATION_JSON)
    public GetTrainer getTrainers() {
        Trainer trainer = getUserTrainer();
        return new DtoConverter.GetTrainerFunction().apply(trainer);
    }

    @PUT
    @Path("/trainer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GetPassword putTrainers(PutTrainer putTrainer) {
        try {
            UUID id = UUID.randomUUID();
            trainerService.create(new DtoConverter.PutTrainerFunction().apply(id, putTrainer));
            Trainer trainer = trainerService.find(id).orElseThrow();
            return new GetPassword(trainer.getLogin(), trainer.getPassword());
        } catch (TransactionalException exc) {
            throw new BadRequestException(exc);
        }
    }

    private Trainer getUserTrainer() {
        String login = securityContext.getUserPrincipal().getName();
        return trainerService.findByLogin(login).orElseThrow();
    }
}
