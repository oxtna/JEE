package pokemon.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import pokemon.dto.*;
import pokemon.entity.Pokemon;
import pokemon.entity.Trainer;
import pokemon.service.PokemonService;
import pokemon.service.TrainerService;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

@Path("")
public class PokemonController {
    private final PokemonService pokemonService;
    private final TrainerService trainerService;
    private final UriInfo uriInfo;
    @Context
    private SecurityContext securityContext;
    private HttpServletResponse response;

    @Inject
    public PokemonController(PokemonService pokemonService, TrainerService trainerService, UriInfo uriInfo) {
        this.pokemonService = pokemonService;
        this.trainerService = trainerService;
        this.uriInfo = uriInfo;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @GET
    @Path("/pokemon")
    @RolesAllowed({"ADMIN", "NORMAL"})
    @Produces(MediaType.APPLICATION_JSON)
    public GetPokemons getPokemons() {
        if (userIsAdmin()) {
            return new DtoConverter.GetPokemonsFunction().apply(pokemonService.findAll());
        }
        String login = securityContext.getUserPrincipal().getName();
        return new DtoConverter.GetPokemonsFunction().apply(pokemonService.findAllByLogin(login));
    }

    @GET
    @Path("/regions/{regionId}/pokemon")
    @RolesAllowed({"ADMIN", "NORMAL"})
    @Produces(MediaType.APPLICATION_JSON)
    public GetPokemons getPokemonsOfRegion(@PathParam("regionId") UUID regionId) {
        try {
            if (userIsAdmin()) {
                return new DtoConverter.GetPokemonsFunction().apply(pokemonService.findAllByRegion(regionId));
            }
            String login = securityContext.getUserPrincipal().getName();
            return new DtoConverter.GetPokemonsFunction().apply(pokemonService.findAllInRegionByLogin(regionId, login));
        } catch (NoSuchElementException exc) {
            throw new NotFoundException();
        }
    }

    @PUT
    @Path("/regions/{regionId}/pokemon")
    @RolesAllowed({"ADMIN", "NORMAL"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void putPokemon(@PathParam("regionId") UUID regionId, PutPokemon putPokemon) {
        try {
            UUID pokemonId = UUID.randomUUID();
            Pokemon pokemon = new DtoConverter.PutPokemonFunction().apply(pokemonId, putPokemon);
            Trainer trainer = getUserTrainer();
            pokemon.setTrainer(trainer);
            trainer.setTeam(Stream.concat(trainer.getTeam().stream(), Stream.of(pokemon)).toList());
            pokemonService.createInRegion(regionId, pokemon);
            trainerService.update(trainer);
            response.setHeader(
                    "Location",
                    uriInfo.getAbsolutePathBuilder().path(pokemonId.toString()).build().toString()
            );
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (TransactionalException exc) {
            throw new BadRequestException(exc);
        }
    }

    @GET
    @Path("/regions/{regionId}/pokemon/{pokemonId}")
    @RolesAllowed({"ADMIN", "NORMAL"})
    @Produces(MediaType.APPLICATION_JSON)
    public GetPokemon getPokemon(@PathParam("regionId") UUID regionId, @PathParam("pokemonId") UUID pokemonId) {
        if (userIsAdminOrOwner(pokemonId)) {
            return new DtoConverter.GetPokemonFunction().apply(
                    pokemonService.findInRegion(regionId, pokemonId).orElseThrow());
        }
        throw new ForbiddenException();
    }

    @PATCH
    @Path("/regions/{regionId}/pokemon/{pokemonId}")
    @RolesAllowed({"ADMIN", "NORMAL"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchPokemon(@PathParam("regionId") UUID regionId, @PathParam("pokemonId") UUID pokemonId,
                             PatchPokemon patchPokemon) {
        try {
            if (userIsAdminOrOwner(pokemonId)) {
                pokemonService.find(pokemonId).ifPresentOrElse(
                        region -> pokemonService.updateInRegion(
                                regionId,
                                new DtoConverter.PatchPokemonFunction().apply(region, patchPokemon)
                        ),
                        () -> {
                            throw new NotFoundException();
                        }
                );
                response.setHeader("Location", uriInfo.getAbsolutePathBuilder().build().toString());
                throw new WebApplicationException(Response.Status.OK);
            }
            throw new ForbiddenException();
        } catch (NoSuchElementException exc) {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("/regions/{regionId}/pokemon/{pokemonId}")
    @RolesAllowed({"ADMIN", "NORMAL"})
    public void deletePokemon(@PathParam("regionId") UUID regionId, @PathParam("pokemonId") UUID pokemonId) {
        try {
            if (userIsAdminOrOwner(pokemonId)) {
                Trainer trainer = getUserTrainer();
                trainer.setTeam(trainer.getTeam().stream().filter(p -> p.getId() != pokemonId).toList());
                pokemonService.deleteFromRegion(regionId, pokemonId);
                trainerService.update(trainer);
                response.setHeader("Location", uriInfo.getAbsolutePathBuilder().build().toString());
                throw new WebApplicationException(Response.Status.OK);
            }
            throw new ForbiddenException();
        } catch (NoSuchElementException exc) {
            throw new NotFoundException();
        }
    }

    private boolean userIsAdminOrOwner(UUID pokemonId) {
        String login = securityContext.getUserPrincipal().getName();
        return securityContext.isUserInRole("ADMIN")
                || pokemonService.find(pokemonId).orElseThrow().getTrainer().getLogin().equals(login);
    }

    private Trainer getUserTrainer() {
        String login = securityContext.getUserPrincipal().getName();
        return trainerService.findByLogin(login).orElseThrow();
    }

    private boolean userIsAdmin() {
        return securityContext.isUserInRole("ADMIN");
    }
}
