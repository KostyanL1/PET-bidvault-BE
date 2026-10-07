package com.legenkiy.user;

import com.legenkiy.common.BaseApi;
import com.legenkiy.user.model.auth.LoginRq;
import com.legenkiy.user.model.auth.RegistrationRq;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.ValidationException;

public interface AuthResource extends BaseApi {

    String REGISTRATION_PATH = "/registration";
    String LOGIN_PATH = "/login";
    String LOGOUT_PATH = "/logout";
    String REFRESH_PATH = "/refresh";

    Response register(@Valid RegistrationRq rq);

    Response login(@Valid LoginRq rq);

    Response refresh(String refreshToken) throws ValidationException;

    Response logout(String refreshToken);

}
