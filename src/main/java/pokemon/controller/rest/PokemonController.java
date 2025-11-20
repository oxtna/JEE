package pokemon.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pokemon.dto.*;
import pokemon.entity.Pokemon;
import pokemon.service.PokemonService;

import java.util.NoSuchElementException;
import java.util.UUID;

@Path("")
public class PokemonController {
    private final PokemonService pokemonService;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Inject
    public PokemonController(PokemonService pokemonService, UriInfo uriInfo) {
        this.pokemonService = pokemonService;
        this.uriInfo = uriInfo;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @GET
    @Path("/pokemon")
    @Produces(MediaType.APPLICATION_JSON)
    public GetPokemons getPokemons() {
        return new DtoConverter.GetPokemonsFunction().apply(pokemonService.findAll());
    }

    @GET
    @Path("/regions/{regionId}/pokemon")
    @Produces(MediaType.APPLICATION_JSON)
    public GetPokemons getPokemonsOfRegion(@PathParam("regionId") UUID regionId) {
        try {
            return new DtoConverter.GetPokemonsFunction().apply(pokemonService.findAllInRegion(regionId));
        } catch (NoSuchElementException exc) {
            throw new NotFoundException();
        }
    }

    @PUT
    @Path("/regions/{regionId}/pokemon")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putPokemon(@PathParam("regionId") UUID regionId, PutPokemon putPokemon) {
        try {
            UUID pokemonId = UUID.randomUUID();
            Pokemon pokemon = new DtoConverter.PutPokemonFunction().apply(pokemonId, putPokemon);
            pokemonService.createInRegion(regionId, pokemon);
            response.setHeader(
                    "Location",
                    uriInfo.getAbsolutePathBuilder().path(pokemonId.toString()).build().toString()
            );
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException exc) {
            throw new BadRequestException(exc);
        }
    }

    @GET
    @Path("/regions/{regionId}/pokemon/{pokemonId}")
    @Produces(MediaType.APPLICATION_JSON)
    public GetPokemon getPokemon(@PathParam("regionId") UUID regionId, @PathParam("pokemonId") UUID pokemonId) {
        return pokemonService.findInRegion(regionId, pokemonId)
                .map(new DtoConverter.GetPokemonFunction())
                .orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("/regions/{regionId}/pokemon/{pokemonId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putPokemon(@PathParam("regionId") UUID regionId, @PathParam("pokemonId") UUID pokemonId,
                           PutPokemon putPokemon) {
        try {
            Pokemon pokemon = new DtoConverter.PutPokemonFunction().apply(pokemonId, putPokemon);
            pokemonService.createInRegion(regionId, pokemon);
            response.setHeader("Location", uriInfo.getAbsolutePathBuilder().build().toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException exc) {
            throw new BadRequestException(exc);
        }
    }

    @PATCH
    @Path("/regions/{regionId}/pokemon/{pokemonId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchPokemon(@PathParam("regionId") UUID regionId, @PathParam("pokemonId") UUID pokemonId,
                             PatchPokemon patchPokemon) {
        try {
            pokemonService.find(pokemonId).ifPresentOrElse(
                    region -> pokemonService.updateInRegion(
                            regionId,
                            new DtoConverter.PatchPokemonFunction().apply(region, patchPokemon)
                    ),
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (NoSuchElementException exc) {
            throw new NotFoundException();
        }
        response.setHeader("Location", uriInfo.getAbsolutePathBuilder().build().toString());
        throw new WebApplicationException(Response.Status.OK);
    }

    @DELETE
    @Path("/regions/{regionId}/pokemon/{pokemonId}")
    public void deletePokemon(@PathParam("regionId") UUID regionId, @PathParam("pokemonId") UUID pokemonId) {
        try {
            pokemonService.deleteFromRegion(regionId, pokemonId);
        } catch (NoSuchElementException exc) {
            throw new NotFoundException();
        }
    }
}
