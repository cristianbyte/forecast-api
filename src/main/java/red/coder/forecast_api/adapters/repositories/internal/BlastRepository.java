package red.coder.forecast_api.adapters.repositories.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import red.coder.forecast_api.domain.entity.Blast;
import red.coder.forecast_api.domain.enums.BlastStatus;

public interface BlastRepository extends JpaRepository<Blast, String> {

    Optional<Blast> findByBlastCode(String blastCode);

    @Query("""
            SELECT blast
            FROM Blast blast
            WHERE (:location IS NULL OR LOWER(blast.location) = LOWER(:location))
              AND (:status IS NULL OR blast.status = :status)
              AND (:period IS NULL OR blast.period = :period)
            """)
    List<Blast> findAllByFilters(
            @Param("location") String location,
            @Param("status") BlastStatus status,
            @Param("period") String period);
}
