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

@Path("/sign-up")
public class SignUpEndpoint {
    private final AuthService service;
    private static final Logger LOG = Logger.getLogger(SignUpEndpoint.class);

    public SignUpEndpoint(AuthService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> signUp(User dto) {
        LOG.info("User tried to sign up: " + dto);
        return this.service.signUp(dto.getUsername(), dto.getPassword())
                .onItem().transform(user -> Response.ok(user).build());
    }
}
