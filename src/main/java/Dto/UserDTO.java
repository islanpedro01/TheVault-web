package Dto;

import FormValidations.*;

public class UserDTO {
    @NotNull(message = "O nome é obrigatório.")
    @MinLength(value = 3, message = "O nome deve ter pelo menos 3 caracteres.")
    private String name;

    @NotNull(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    public UserDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
