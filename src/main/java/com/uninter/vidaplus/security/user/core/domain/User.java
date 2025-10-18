package com.uninter.vidaplus.security.user.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "password")
public class User implements Serializable {
    private Long id;
    private String email;
    private String password;
    private List<Role> roles;

    public User(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public List<String> getRolesFormattedAsString() {
        return roles.stream().map(role -> role.getName().name()).toList();
    }
}
