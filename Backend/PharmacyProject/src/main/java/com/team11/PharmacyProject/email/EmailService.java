package com.team11.PharmacyProject.email;

import com.team11.PharmacyProject.dto.appointment.AppointmentReservationDTO;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationWorkerDTO;
import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayDTO;
import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayWithWorkerDetailsDTO;
import com.team11.PharmacyProject.eRecipe.ERecipe;
import com.team11.PharmacyProject.myOrder.MyOrder;
import com.team11.PharmacyProject.orderItem.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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


    public void notifySupplierThatHisOfferIsAccepted(String email, MyOrder myOrder, String name) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Ponuda za porudzbinu");

        StringBuilder text = new StringBuilder();
        for (OrderItem item:
                myOrder.getOrderItem()) {
            text.append(item.getMedicine().getName()).append(", ");
        }

        String line = text.toString();

        mail.setText("Pozdrav " + name+ ", \n Zelimo da Vas obavestimo da Vasa ponuda za porudzbinom koja potrazuje " + line + " je prihvacena. \nPozdrav");

        javaMailSender.send(mail);
    }

    public void notifySupplierThatHisOfferIsRefused(String email, MyOrder myOrder, String name) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Ponuda za porudzbinu");

        StringBuilder text = new StringBuilder();
        for (OrderItem item:
                myOrder.getOrderItem()) {
            text.append(item.getMedicine().getName()).append(", ");
        }

        String line = text.toString();

        mail.setText("Pozdrav " + name+", \n Zelimo da Vas obavestimo da Vasa ponuda za porudzbinom koja potrazuje " + line + " je odbijena. \nPozdrav");

        javaMailSender.send(mail);
    }

    @Async
    public void notifyPatientAboutPickingUpMedicine(MedicineReservationWorkerDTO reservationDTO) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(reservationDTO.getEmail()); //todo izmeniti
        mail.setTo("darko1705@gmail.com");
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Potvrda izdavanja leka");

        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(reservationDTO.getReservationDate());

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(reservationDTO.getPickupDate());

        SimpleDateFormat sdf = new SimpleDateFormat("dd. M. yyyy. : HH:mm");

        mail.setText("Pozdrav " + reservationDTO.getFirstName() + " " + reservationDTO.getLastName() + ",\n\n"
                + "Dobili ste ovaj mail kao potvrdu izdavanja leka.\nID rezervacije - " + reservationDTO.getReservationID() + "\n"
                + "Datum rezervacije: " +  sdf.format(cal1.getTime()) + "\nRok za podizanje rezervacije: " + sdf.format(cal2.getTime()) + "\n"
                + "Naziv leka: " + reservationDTO.getMedicineName() + "\nKod leka: " + reservationDTO.getMedicineID() + "\n\n"
                + "Hvala Vam na poverenju, nadamo se daljoj zajednickoj saradnji!");

        javaMailSender.send(mail);
    }

    @Async
    public void notifyPatientAboutERecipe(ERecipe eRecipe) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(eRecipe.getPatient().getEmail()); //todo promeni kada se usklade adrese
        mail.setTo("deja99@live.com");
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Potvrda izdavanja lekova putem e-recepta");

        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(eRecipe.getPrescriptionDate());

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(eRecipe.getDispensingDate());

        SimpleDateFormat sdf = new SimpleDateFormat("dd. M. yyyy.  HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("Poštovani ").append(eRecipe.getPatient().getFirstName()).append(" ").append(eRecipe.getPatient().getLastName())
                .append(",\nIzvršeno je izdavanje lekova putem e-recepta u apoteci \"").append(eRecipe.getPharmacy().getName()).append("\"\n\n")
                .append("Datum nastanka e-recepta: ").append(sdf.format(cal1.getTime())).append("\n")
                .append("Datum izdavanja lekova: ").append(sdf.format(cal2.getTime())).append("\n\n")
                .append("Izdati lekovi: \n");

        for (var eri : eRecipe.geteRecipeItems()) {
            sb.append("Lek: ")
                    .append(eri.getMedicineName()).append(" -- Količina: ").append(eri.getQuantity()).append("\n");
        }

        sb.append(String.format("Ukupna cena lekova: %.2f\n", eRecipe.getTotalPrice()));
        sb.append(String.format("Ukupna cena lekova sa popustom: %.2f\n\n", eRecipe.getTotalPriceWithDiscount()));
        sb.append(String.format("Ovom kupovinom ste ostvarili %d bodova!\n\nPozdrav, Vaša %s", eRecipe.getPoints(), eRecipe.getPharmacy().getName()));

        mail.setText(sb.toString());
        javaMailSender.send(mail);
    }
}
