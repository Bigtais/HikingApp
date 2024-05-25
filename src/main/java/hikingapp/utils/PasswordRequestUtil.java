package hikingapp.utils;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Helper class for the password change operation.
 */
@Data
public class PasswordRequestUtil {
//    @NotEmpty
//    @Size(min = 1, message = "{member.email.bad_format}")
//    private String email;

    @NotEmpty(message = "{member.password.not_empty}")
    @Size(min = 8, message = "{member.password.too_short}")
    private String oldPassword;

    @NotEmpty(message = "{member.password.not_empty}")
    @Size(min = 8, message = "{member.password.too_short}")
    private String newPassword;

    @NotEmpty(message = "{member.password.not_empty}")
    @Size(min = 8, message = "{member.password.too_short}")
    private String confirmPassword;
}
