package pokemon.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pokemon.dto.*;
import pokemon.service.RegionService;

import java.util.UUID;

@Path("")
public class RegionController {
    private final RegionService regionService;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Inject
    public RegionController(RegionService regionService, UriInfo uriInfo) {
        this.regionService = regionService;
        this.uriInfo = uriInfo;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @GET
    @Path("/regions")
    @RolesAllowed({"ADMIN", "NORMAL"})
    @Produces(MediaType.APPLICATION_JSON)
    public GetRegions getRegions() {
        return new DtoConverter.GetRegionsFunction().apply(regionService.findAll());
    }

    @PUT
    @Path("/regions")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putRegions(PutRegion putRegion) {
        try {
            UUID id = UUID.randomUUID();
            regionService.create(new DtoConverter.PutRegionFunction().apply(id, putRegion));
            response.setHeader("Location", uriInfo.getAbsolutePathBuilder().path(id.toString()).build().toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (TransactionalException exc) {
            throw new BadRequestException(exc);
        }
    }

    @GET
    @Path("/regions/{id}")
    @RolesAllowed({"ADMIN", "NORMAL"})
    @Produces(MediaType.APPLICATION_JSON)
    public GetRegion getRegion(@PathParam("id") UUID id) {
        return regionService.find(id).map(new DtoConverter.GetRegionFunction()).orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("/regions/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putRegion(@PathParam("id") UUID id, PutRegion putRegion) {
        try {
            regionService.create(new DtoConverter.PutRegionFunction().apply(id, putRegion));
            response.setHeader("Location", uriInfo.getAbsolutePathBuilder().build().toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (TransactionalException exc) {
            throw new BadRequestException(exc);
        }
    }

    @PATCH
    @Path("/regions/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchRegion(@PathParam("id") UUID id, PatchRegion patchRegion) {
        regionService.find(id).ifPresentOrElse(
                region -> regionService.update(
                        new DtoConverter.PatchRegionFunction().apply(region, patchRegion)
                ),
                () -> { throw new NotFoundException(); }
        );
        response.setHeader("Location", uriInfo.getAbsolutePathBuilder().build().toString());
        throw new WebApplicationException(Response.Status.OK);
    }

    @DELETE
    @Path("/regions/{id}")
    @RolesAllowed("ADMIN")
    public void deleteRegion(@PathParam("id") UUID id) {
        regionService.delete(id);
    }
}
