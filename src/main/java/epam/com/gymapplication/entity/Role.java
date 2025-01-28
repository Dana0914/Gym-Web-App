package epam.com.gymapplication.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    TRAINER("TRAINER"),
    TRAINEE("TRAINEE");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}
