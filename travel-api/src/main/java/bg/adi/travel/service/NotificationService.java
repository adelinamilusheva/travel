package bg.adi.travel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import bg.adi.travel.data.entity.pub.Notification;
import bg.adi.travel.data.repository.pub.NotificationRepository;
import bg.adi.travel.dto.ShortPublicationDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MailingService mailingService;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void subscribe(String email) {
        Optional<Notification> notification = notificationRepository.findByEmail(email);

        if (notification.isPresent()) {
            notification.get().setIsActive(true);
        } else {
            notificationRepository.save(
                    Notification.builder()
                                .email(email)
                                .isActive(true)
                                .build()
            );
        }
    }

    @Transactional
    public void unsubscribe(String email) {
        Optional<Notification> notification = notificationRepository.findByEmail(email);

        if (notification.isPresent()) {
            notification.get().setIsActive(false);
        } else {
            // TODO: error code
        }
    }

    public void sendNewPublication(ShortPublicationDTO publication) {
        List<Notification> notifications = notificationRepository.findByIsActive(true);

        mailingService.send(notifications.stream().map(n -> n.getEmail()).collect(Collectors.toList()),
            getNotificationSubject(publication), getNotificationContent(publication)
        );
    }

    private String getNotificationSubject(ShortPublicationDTO publication) {
        return "Нова статия в Travel.com - ${publication.title}";
    }

    private String getNotificationContent(ShortPublicationDTO publication) {
        return "<p style=\"color: #5e9ca0;\"><span style=\"color: #000000;\"><strong>Публикувана е <a href=\"http://www.localhost:8080/api/publications/${publication.id}\">нова статия</a> в Travel.com</strong></span></p>";
    }
}
