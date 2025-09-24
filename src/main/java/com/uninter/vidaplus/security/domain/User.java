package com.uninter.vidaplus.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "password")
public class User implements Serializable {

    private Long id;

    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    private List<Role> roles;

}
