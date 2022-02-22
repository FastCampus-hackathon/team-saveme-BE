package com.saveme.comparator.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;

    private String password;

    private String role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private final List<Wish> wishList = new ArrayList<>();
}
