package eu.jitpay.testtask.repository;

import eu.jitpay.testtask.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
