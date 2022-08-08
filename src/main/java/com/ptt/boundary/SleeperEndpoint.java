package com.ptt.boundary;

import com.ptt.service.AuthService;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sleep")
public class SleeperEndpoint {
    private final AuthService service;

    public SleeperEndpoint(AuthService service) {
        this.service = service;
    }

    @GET()
    @Path("/{sessionToken}/{seconds}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> sleepForSeconds(@PathParam("sessionToken") String sessionToken,
                                         @PathParam("seconds") int seconds) {
        return this.service
                .isLoggedIn(sessionToken)
                .onItem()
                .transform(loggedIn -> {
                    if (!loggedIn)
                        return Response.status(403).build();

                    try {
                        Thread.sleep(seconds * 1000);
                    } catch (InterruptedException e) {
                        return Response.status(500).build();
                    }

                    return Response.ok().build();
                }).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }
}
