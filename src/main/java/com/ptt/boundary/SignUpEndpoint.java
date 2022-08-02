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

@Path("/sign-up")
public class SignUpEndpoint {
    private final AuthService service;

    public SignUpEndpoint(AuthService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> signUp(User dto) {
        return this.service.signUp(dto.getUsername(), dto.getPassword())
                .onItem().transform(user -> Response.ok(user).build());
    }
}
