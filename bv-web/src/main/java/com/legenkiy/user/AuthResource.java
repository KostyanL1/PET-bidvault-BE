package com.legenkiy.user;

import com.legenkiy.common.BaseApi;
import com.legenkiy.user.model.auth.LoginRq;
import com.legenkiy.user.model.auth.RegistrationRq;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static com.legenkiy.common.BaseApi.BASE_PATH;

@Path(BASE_PATH + "/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuthResource extends BaseApi {

    String REGISTRATION_PATH = "/registration";
    String LOGIN_PATH = "/login";
    String LOGOUT_PATH = "/logout";

    @POST
    @Path(REGISTRATION_PATH)
    @PermitAll
    Response register(@Valid RegistrationRq rq);

    @POST
    @Path(LOGIN_PATH)
    @PermitAll
    Response login(@Valid LoginRq rq);

    @POST
    @Path(LOGOUT_PATH)
    @PermitAll
    Response logout(@HeaderParam("Authorization") String authorization);

}
