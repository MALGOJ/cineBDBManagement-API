package com.example.cineBDB_managment.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {

    private final SesClient sesClient;

    public EmailService() {
        this.sesClient = SesClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    public void sendReservationConfirmation(
            String toEmail,
            String movieTitle,
            String roomName,
            LocalDateTime schedule,
            List<String> seats
    ) {
        String subject = "Confirmación de reserva";
        String body = String.format(
                "Reserva confirmada:\n" +
                        "Película: %s\n" +
                        "Sala: %s\n" +
                        "Horario: %s\n" +
                        "Asientos: %s",
                movieTitle, roomName, schedule, String.join(", ", seats)
        );

        SendEmailRequest request = SendEmailRequest.builder()
                .source("no-reply@tucine.com")
                .destination(d -> d.toAddresses(toEmail))
                .message(msg -> msg
                        .subject(s -> s.data(subject))
                        .body(b -> b.text(t -> t.data(body)))
                )
                .build();

        sesClient.sendEmail(request);
    }
}