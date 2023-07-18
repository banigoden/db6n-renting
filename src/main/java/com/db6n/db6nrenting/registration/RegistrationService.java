package com.db6n.db6nrenting.registration;

import com.db6n.db6nrenting.appuser.AppUser;
import com.db6n.db6nrenting.appuser.AppUserRole;
import com.db6n.db6nrenting.appuser.AppUserService;
import com.db6n.db6nrenting.registration.token.ConfirmationToken;
import com.db6n.db6nrenting.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;
//    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail){
            try {
                throw new IllegalAccessException("email not valid");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return appUserService.signUpUser(new AppUser(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER));
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
