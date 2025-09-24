package com.uninter.vidaplus.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    private Long id;
    @Getter
    private RoleEnum name;

}
