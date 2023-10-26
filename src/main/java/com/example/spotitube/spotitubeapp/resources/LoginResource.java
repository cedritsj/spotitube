package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.resources.interfaces.ILoginService;
import com.example.spotitube.spotitubeapp.services.LoginService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

    private ILoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        return Response
                .status(200)
                .entity(loginService.login(loginRequestDTO))
                .build();
    }

    @Inject
    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }
}