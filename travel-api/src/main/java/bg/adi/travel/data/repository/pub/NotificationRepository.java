package bg.adi.travel.data.repository.pub;

import bg.adi.travel.data.entity.pub.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>, JpaSpecificationExecutor<Notification> {
    Optional<Notification> findByEmail(String email);
    List<Notification> findByIsActive(Boolean active);
}
