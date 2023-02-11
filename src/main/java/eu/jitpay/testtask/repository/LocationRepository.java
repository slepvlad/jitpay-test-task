package eu.jitpay.testtask.repository;


import eu.jitpay.testtask.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByUserIdAndCreatedOnBetween(UUID userId, LocalDateTime fromDateTime, LocalDateTime toDateTime);

    @Query("SELECT l FROM Location l WHERE l.userId = :userId ORDER BY l.createdOn DESC LIMIT 1")
    Optional<Location> findLastByUserId(@Param("userId") UUID userId);

}
