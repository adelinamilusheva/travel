package bg.adi.travel.controller;

import bg.adi.travel.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/subscribe")
    @ApiOperation(value = "Subscribe", notes = "Subscribe user to the email notifications list.")
    public ResponseEntity<Void> subscribe(@RequestBody String email) {
        notificationService.subscribe(email);
        return ResponseEntity.status(202).build();
    }

    @PostMapping("/unsubscribe")
    @ApiOperation(value = "Unsubscribe", notes = "Unsbscribe user from the email notifications list.")
    public ResponseEntity<Void> unsubscribe(@RequestBody String email) {
        notificationService.unsubscribe(email);
        return ResponseEntity.status(202).build();
    }
}
