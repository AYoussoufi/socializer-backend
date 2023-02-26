package com.project.socializer.requests.registration.requestBody;

import com.project.socializer.requests.registration.requestBody.validation.ValidName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotNull
    @NotEmpty
    @ValidName
    String pseudo;

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
