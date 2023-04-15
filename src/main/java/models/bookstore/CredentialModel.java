package models.bookstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class CredentialModel {
    String userName;
    String password;

    public CredentialModel() {}

    public CredentialModel(String password) {
        this.password = password;
    }
}
