package com.team11.PharmacyProject.email;

import com.team11.PharmacyProject.dto.appointment.AppointmentReservationDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayDTO;
import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayWithWorkerDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void notifyPatientAboutReservedAppointment(AppointmentReservationDTO reservationDTO, String type) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(reservationDTO.getEmail());
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject(type + " - Potvrda rezervacije");

        mail.setText("Pozdrav " + reservationDTO.getFirstName() + " " + reservationDTO.getLastName() + ",\n\n"
                + "Samo da Vas obavestimo da smo primili rezervaciju.\n"
                + "Vreme pocetka: " + reservationDTO.getStartTime() + "\nVreme kraja: " + reservationDTO.getEndTime() + "\n"
                + "Cena Vaseg pregleda iznosi: " + reservationDTO.getPrice() + "\n\n"
                + "Hvala Vam na poverenju, nadamo se daljoj zajednickoj saradnji!");

        javaMailSender.send(mail);
    }

    @Async
    public void notifyWorkerAboutRequestForHoliday(String email, RequestForHolidayWithWorkerDetailsDTO dto, String reason) throws MailException {


        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Zahtev za odmor");

        String message;
        if(reason.equals(""))
            message = "odobren";
        else
            message = "odbijen";

        String append;
        if(reason.equals(""))
            append = "";
        else
            append = "Razlog je: "+ reason;

        mail.setText("Pozdrav, \n Zelimo da Vas obavestimo da je zahtev za odmorom tipa " + dto.getAbsenceType() + " od " + dto.getWorkerDetails() + " koji je trazen za " +sdf.format(new Date(dto.getStart())) + " je " + message + "! " + append + "\nPozdrav");

        javaMailSender.send(mail);
    }


}
