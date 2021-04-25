package com.team11.PharmacyProject.email;

import com.team11.PharmacyProject.dto.appointment.AppointmentCheckupReservationDTO;
import com.team11.PharmacyProject.dto.appointment.AppointmentConsultationReservationDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Async
    public void notifyPatientAboutReservation(MedicineReservationNotifyPatientDTO reservationDTO) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(reservationDTO.getEmail());
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Potvrda rezervacije leka");

        mail.setText("Pozdrav " + reservationDTO.getFirstName() + " " + reservationDTO.getLastName() + ",\n\n"
        + "Samo da Vas obavestimo da smo primili rezervaciju.\nID rezervacije - " + reservationDTO.getReservationId() + "\n"
        + "Datum rezervacije: " + reservationDTO.getReservationDate() + "\nMolimo Vas da rezervaciju pokupite do " + reservationDTO.getPickupDate() + "\n"
        + "Mesto preuzimanja " + reservationDTO.getPharmacyAddress() + ", apoteka " + reservationDTO.getPharmacyName() + "\n\n"
        + "Hvala Vam na poverenju, nadamo se daljoj zajednickoj saradnji!");

        javaMailSender.send(mail);
    }

    @Async
    public void notifyPatientAboutReservedCheckup(AppointmentCheckupReservationDTO reservationDTO) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(reservationDTO.getEmail());
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Potvrda rezervacije leka");

        mail.setText("Pozdrav " + reservationDTO.getFirstName() + " " + reservationDTO.getLastName() + ",\n\n"
                + "Samo da Vas obavestimo da smo primili rezervaciju.\n"
                + "Vreme pocetka: " + reservationDTO.getStartTime() + "\nVreme kraja: " + reservationDTO.getEndTime() + "\n"
                + "Cena Vaseg pregleda iznosi: " + reservationDTO.getPrice() + "\n\n"
                + "Hvala Vam na poverenju, nadamo se daljoj zajednickoj saradnji!");

        javaMailSender.send(mail);
    }

    @Async
    public void notifyPatientAboutReservedConsultation(AppointmentConsultationReservationDTO reservationDTO) {
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(reservationDTO.getEmail());
//        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
//        mail.setSubject("Potvrda rezervacije leka");
//
//        mail.setText("Pozdrav " + reservationDTO.getFirstName() + " " + reservationDTO.getLastName() + ",\n\n"
//                + "Samo da Vas obavestimo da smo primili rezervaciju.\n"
//                + "Vreme pocetka: " + reservationDTO.getStartTime() + "\nVreme kraja: " + reservationDTO.getEndTime() + "\n"
//                + "Cena Vaseg pregleda iznosi: " + reservationDTO.getPrice() + "\n\n"
//                + "Hvala Vam na poverenju, nadamo se daljoj zajednickoj saradnji!");
//
//        javaMailSender.send(mail);
    }
}
