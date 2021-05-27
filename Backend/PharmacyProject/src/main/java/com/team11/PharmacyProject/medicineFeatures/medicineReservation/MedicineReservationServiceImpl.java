package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.advertisement.Advertisement;
import com.team11.PharmacyProject.advertisement.AdvertismentService;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationWorkerDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemRepository;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.rankingCategory.RankingCategory;
import com.team11.PharmacyProject.rankingCategory.RankingCategoryService;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly=true)
public class MedicineReservationServiceImpl implements MedicineReservationService  {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    MedicineReservationRepository reservationRepository;

    @Autowired
    MedicineItemRepository itemRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    WorkplaceService workplaceService;

    @Autowired
    AdvertismentService advertismentService;

    @Autowired
    RankingCategoryService categoryService;

    @Override
    public boolean isMedicineItemReserved(Long id) {
        MedicineReservation reservation =  reservationRepository.findReservationByMedicineItemId(id);
        
        if(reservation != null)
            return true;
        return false;
    }

    @Override
    public List<MedicineReservation> getReservedMedicinesByPatientId(Long id) {

        Patient patient = patientRepository.findPatientFetchReservedMedicines(id, System.currentTimeMillis());

        if(patient == null) return null;

        return patient.getMedicineReservation();
    }

    @Override
    public double calculateProfit(long start, long end, long pharmacyId) {
        double sum = 0;
        List<MedicineReservation> list = reservationRepository.getReservationsBeetwenTwoTimestamps(start, end, pharmacyId);
        for (MedicineReservation a:list) {
            List<MedicinePrice> prices =  a.getMedicineItem().getMedicinePrices();
            double price = 0;
            long time = 0;
            for (MedicinePrice p:
                 prices) {
                if(p.getStartDate() < a.getReservationDate() && p.getStartDate() > time){
                    price = p.getPrice();
                    time = p.getStartDate();
                }
            }

            sum += (int)price;
        }
        return sum;
    }

    @Override
    public Map<String, Integer> getInfoForReport(String period, Long pharmacyId) {
        String[] monthNames = {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};

        Map<String, Integer> data = new LinkedHashMap<>();
        List<MedicineReservation> list;

        Calendar calendar = Calendar.getInstance();

        long currTime = Instant.now().toEpochMilli();

        calendar.setTime(new Date(currTime));
        calendar.set(calendar.get(Calendar.YEAR)-1, calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        long yearAgo = calendar.getTimeInMillis();

        if(period.equals("Monthly")){
            int count = 0;
            for(int i = calendar.get(Calendar.MONTH)+1; i<12; i++) {
                String key = monthNames[i]+"-"+calendar.get(Calendar.YEAR);
                data.put(key, 0);
                count++;
            }
            for(int i = 0; i<12-count; i++) {
                String key = monthNames[i]+"-"+(calendar.get(Calendar.YEAR)+1);
                data.put(key, 0);
            }

            list = reservationRepository.getReservationsBeetwenTwoTimestamps(yearAgo, currTime, pharmacyId);

            for (MedicineReservation a:list) {
                calendar.setTime(new Date(a.getReservationDate()));
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String key = monthNames[month]+"-"+year;
                if(data.containsKey(key))
                    data.put(key, data.get(key) + 1);
            }
        }
        else if(period.equals("Quarterly")) {
            if(calendar.get(Calendar.MONTH)<=2) {
                data.put(2+"-"+calendar.get(Calendar.YEAR),0);
                data.put(3+"-"+calendar.get(Calendar.YEAR),0);
                data.put(4+"-"+calendar.get(Calendar.YEAR),0);
                data.put(1+"-"+(calendar.get(Calendar.YEAR)+1),0);
            } else if(calendar.get(Calendar.MONTH)<=5) {
                data.put(3+"-"+calendar.get(Calendar.YEAR),0);
                data.put(4+"-"+calendar.get(Calendar.YEAR),0);
                data.put(1+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(2+"-"+(calendar.get(Calendar.YEAR)+1),0);
            } else if(calendar.get(Calendar.MONTH)<=8) {
                data.put(4+"-"+calendar.get(Calendar.YEAR),0);
                data.put(1+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(2+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(3+"-"+(calendar.get(Calendar.YEAR)+1),0);
            } else {
                data.put(1+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(2+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(3+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(4+"-"+(calendar.get(Calendar.YEAR)+1),0);
            }

            calendar.setTime(new Date(currTime));

            if(calendar.get(Calendar.MONTH)<=2) {
                calendar.setTime(new Date(yearAgo));
                calendar.set(calendar.get(Calendar.YEAR), Calendar.APRIL, 1, 0, 0, 0);
                yearAgo = calendar.getTimeInMillis();
            } else if(calendar.get(Calendar.MONTH)<=5) {
                calendar.setTime(new Date(yearAgo));
                calendar.set(calendar.get(Calendar.YEAR), Calendar.JULY, 1, 0, 0, 0);
                yearAgo = calendar.getTimeInMillis();
            } else if(calendar.get(Calendar.MONTH)<=8) {
                calendar.setTime(new Date(yearAgo));
                calendar.set(calendar.get(Calendar.YEAR), Calendar.OCTOBER, 1, 0, 0, 0);
                yearAgo = calendar.getTimeInMillis();
            } else {
                calendar.setTime(new Date(yearAgo));
                calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
                yearAgo = calendar.getTimeInMillis();
            }

            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
            yearAgo = calendar.getTimeInMillis();

            list = reservationRepository.getReservationsBeetwenTwoTimestamps(yearAgo, currTime, pharmacyId);

            for (MedicineReservation a:list) {
                calendar.setTime(new Date(a.getReservationDate()));
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int quarter;

                if(month<=2) {
                    quarter = 1;
                } else if(month<=5) {
                    quarter = 2;
                } else if(month<=8) {
                    quarter = 3;
                } else {
                    quarter = 4;
                }

                String key = quarter+"-"+year;
                if(data.containsKey(key))  data.put(key, data.get(key) + 1);
            }
        } else {
            calendar.setTime(new Date(currTime));
            calendar.set(calendar.get(Calendar.YEAR)-9, Calendar.JANUARY, 1, 0, 0, 0);
            yearAgo = calendar.getTimeInMillis();

            data.put(calendar.get(Calendar.YEAR)+"",0);
            data.put((calendar.get(Calendar.YEAR)+1)+"",0);
            data.put((calendar.get(Calendar.YEAR)+2)+"",0);
            data.put((calendar.get(Calendar.YEAR)+3)+"",0);
            data.put((calendar.get(Calendar.YEAR)+4)+"",0);
            data.put((calendar.get(Calendar.YEAR)+5)+"",0);
            data.put((calendar.get(Calendar.YEAR)+6)+"",0);
            data.put((calendar.get(Calendar.YEAR)+7)+"",0);
            data.put((calendar.get(Calendar.YEAR)+8)+"",0);
            data.put((calendar.get(Calendar.YEAR)+9)+"",0);

            list = reservationRepository.getReservationsBeetwenTwoTimestamps(yearAgo, currTime, pharmacyId);

            for (MedicineReservation a:list) {
                calendar.setTime(new Date(a.getReservationDate()));
                int year = calendar.get(Calendar.YEAR);
                String key = year+"";
                if(data.containsKey(key))  data.put(key, data.get(key) + 1);
            }
        }
        return data;
    }


    @Override
    @Transactional(readOnly = false)
    public MedicineReservationNotifyPatientDTO insertMedicineReservation(MedicineReservationInsertDTO dto) {

        Optional<Patient> p = patientRepository.findById(dto.getUserId());
        if(p.isPresent()) {
            if(p.get().getPenalties() >= 3) throw new RuntimeException("This functionality is not available, you've got 3 penalties!");
        }

        Pharmacy pharmacy = pharmacyRepository.findPharmacyByPharmacyAndMedicineId(dto.getPharmacyId(), dto.getMedicineId());
        if (pharmacy == null) throw new RuntimeException("Selected pharmacy doesn't have required medicine!");

        MedicineItem item = null;

        for(MedicineItem mi : pharmacy.getPriceList().getMedicineItems()) {
            if(mi.getMedicine().getId().equals(dto.getMedicineId())) {
                item = mi;
                break;
            }
        }

        if(item == null) throw new RuntimeException("Selected pharmacy doesn't have required medicine!");

        item = itemRepository.findByIdForTransaction(item.getId());
        if(!item.setAmountLessOne()) throw new RuntimeException("There's no required medicine right now in the stock!");

        MedicineReservation reservation = new MedicineReservation();
        reservation.setReservationID(UUID.randomUUID().toString());
        reservation.setPharmacy(pharmacy);
        reservation.setPickupDate(dto.getPickupDate());
        reservation.setReservationDate(System.currentTimeMillis());
        reservation.setState(ReservationState.RESERVED);
        reservation.setMedicineItem(item);
        reservation.setPrice(dto.getPrice());

        RankingCategory c = categoryService.getCategoryByPoints(p.get().getPoints());   // Ako korisnik pripada nekoj kategoriji, lupi popust
        if (c != null) {
            reservation.setPriceWithDiscout(c.getDiscount());
        }

        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByIdAndFetchReservationsEagerly(dto.getUserId()));
        if(patient.isEmpty()) {
            patient = patientRepository.findById(dto.getUserId());
            if(patient.isEmpty()) throw new RuntimeException("You are not registered in the database!");
            patient.get().setMedicineReservation(new ArrayList<>());
        }

        if(!patient.get().addReservation(reservation)) throw new RuntimeException("You have already reservation with that id!");

        itemRepository.save(item);
        patientRepository.save(patient.get());

        return new MedicineReservationNotifyPatientDTO(patient.get(), pharmacy, reservation);
    }

    @Override
    @Transactional(readOnly = false)
    public void cancelReservation(Long id) {
        MedicineReservation reservation = reservationRepository.findByIdForCanceling(id);

        if (reservation == null) throw new RuntimeException("Reservation does not exist!");

        if (reservation.getState() != ReservationState.RESERVED) throw new RuntimeException("Only reserved medicines can be canceled!");
        if (reservation.getPickupDate() < System.currentTimeMillis()) throw new RuntimeException("Reservation is in the past, can't cancel it!"); // Provera da ipak nije rezervacija iz proslosti

        long differenceInMinutes = ((reservation.getPickupDate() - System.currentTimeMillis()) / (1000 * 60));
        if(differenceInMinutes < 1440) throw new RuntimeException("There's less then 24h to picking up the reservation, can not cancel it!");

        reservation.setState(ReservationState.CANCELLED);
        reservation.getMedicineItem().setAmountPlusOne();

        reservationRepository.save(reservation);
    }

    @Override
    public MedicineReservation getMedicineReservationFromPharmacy(Long workerID, String resID){
        Workplace workplace = workplaceService.getWorkplaceOfPharmacist(workerID);
        if (workplace == null){
            return null;
        }
        return reservationRepository.getMedicineReservationFromPharmacy(workplace.getPharmacy().getId(),resID);
    }

    @Override
    public MedicineReservationWorkerDTO issueMedicine(Long workerID, String resID){
        MedicineReservation medicineReservation = getMedicineReservationFromPharmacy(workerID, resID);
        if (medicineReservation == null) {
            return null;
        }
        Long dueDate = medicineReservation.getPickupDate();
        Long currTime = Instant.now().toEpochMilli();
        if (dueDate - currTime <= 0){
            return null;
        }

        Patient pat = patientRepository.findByReservationID(medicineReservation.getId());
        if (pat == null){
            return null;
        }
        pat.setPoints(pat.getPoints() + medicineReservation.getMedicineItem().getMedicine().getPoints());
        medicineReservation.setState(ReservationState.RECEIVED);
        reservationRepository.save(medicineReservation);

        return new MedicineReservationWorkerDTO(medicineReservation, pat);
    }
}
