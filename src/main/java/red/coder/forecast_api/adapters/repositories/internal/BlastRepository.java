package red.coder.forecast_api.adapters.repositories.internal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import red.coder.forecast_api.domain.entity.Blast;

public interface BlastRepository extends JpaRepository<Blast, String> {

    Optional<Blast> findByBlastCode(String blastCode);
}
