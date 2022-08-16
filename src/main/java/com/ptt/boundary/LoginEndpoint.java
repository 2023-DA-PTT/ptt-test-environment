package com.ptt.boundary;

import com.ptt.entity.User;
import com.ptt.service.AuthService;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

@Path("/login")
public class LoginEndpoint {
    private final AuthService service;
    private static final Logger LOG = Logger.getLogger(LoginEndpoint.class);

    public LoginEndpoint(AuthService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> login(User user) {
        LOG.info("User tried to log in: " + user);
        return service.login(user.getUsername(), user.getPassword())
                .onItem()
                .transform(
                        token -> token.isPresent()
                        ? Response.ok(token.get()).build()
                        : Response.status(403).build()
                );
    }
}
