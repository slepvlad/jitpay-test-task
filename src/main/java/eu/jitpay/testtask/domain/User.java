package eu.jitpay.testtask.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    private UUID userId;
    private String email;
    private String firstName;
    private String secondName;
    @OneToMany
    @JoinColumn(name = "userId")
    private List<Location> locations;
}
