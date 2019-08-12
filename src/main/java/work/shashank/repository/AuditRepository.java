package work.shashank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.shashank.entity.Audit;

/**
 * @author Shashank Sharma
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

}
