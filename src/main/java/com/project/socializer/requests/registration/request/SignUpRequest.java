package com.project.socializer.requests.registration.request;

import com.project.socializer.requests.registration.request.validation.ValidName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {

    @NotNull
    @NotEmpty
    @ValidName
 String firstName;

    @NotNull
    @NotEmpty
    @ValidName
 String lastName;

    @NotNull
    @NotEmpty
    @Email
 String email;

    @NotNull
    @NotEmpty
 String password;

    @NotNull
    @NotEmpty
 String birthDay;
}
