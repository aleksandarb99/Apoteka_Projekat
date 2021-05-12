package com.team11.PharmacyProject.pharmacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyERecipeDTO;
import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.ERecipeState;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.inquiry.Inquiry;
import com.team11.PharmacyProject.inquiry.InquiryRepository;
import com.team11.PharmacyProject.inquiry.InquiryService;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemRepository;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.myOrder.MyOrder;
import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    MedicineService medicineService;

    @Autowired
    PharmacyWorkerRepository workerRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    InquiryService inquiryService;

    @Autowired
    MedicineItemRepository medicineItemRepository;

    @Autowired
    InquiryRepository inquiryRepository;


    @Override
    public void addMedicineToStock(MyOrder order1) {
        Pharmacy pharmacy = order1.getPharmacy();
        for (OrderItem item : order1.getOrderItem()) {
            for (MedicineItem mitem : pharmacy.getPriceList().getMedicineItems()) {
                if (item.getMedicine().getId().equals(mitem.getMedicine().getId())) {
                    mitem.setAmount(mitem.getAmount() + item.getAmount());
                    break;
                }
            }
        }

        pharmacyRepository.save(pharmacy);

        List<Inquiry> list = inquiryService.getInquiriesByPharmacyId(pharmacy.getId());
        for (Inquiry i:list) {
            for (OrderItem item : order1.getOrderItem()) {
                if(item.getMedicine().getId().equals(i.getMedicineItems().getMedicine().getId())) {
                    i.setActive(false);
                    inquiryService.save(i);
                }
            }
        }

    }

    @Override
    public Pharmacy getPharmacyIdByAdminId(Long id) {
        List<Pharmacy> list = pharmacyRepository.findAllWithAdmins();
        for (Pharmacy p:
             list) {
            for (MyUser admin:
                 p.getAdmins()) {
                if(id.equals(admin.getId()))
                    return p;
            }
        }
        return null;
    }

    @Override
    public boolean subscribe(long pharmacyId, long patientId) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findPharmacyByIdFetchSubscribed(pharmacyId);
        if (pharmacy.isEmpty())
            return false;
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty())
            return false;
        if (pharmacy.get().getSubscribers().stream().anyMatch(p -> p.getId() == patientId))
            return false;
        pharmacy.get().getSubscribers().add(patient.get());
        pharmacyRepository.save(pharmacy.get());
        return true;
    }

    @Override
    public boolean unsubscribe(long pharmacyId, long patientId) {
        try {
            int num = pharmacyRepository.removeSubscription(pharmacyId, patientId);
            return num != 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isSubscribed(long pharmacyId, long patientId) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findPharmacyByIdFetchSubscribed(pharmacyId);
        if (pharmacy.isEmpty())
            return false;
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty())
            return false;
        return pharmacy.get().getSubscribers().stream().anyMatch(p -> p.getId() == patientId);
    }

    @Override
    public void save(Pharmacy p) {
        pharmacyRepository.save(p);
    }

    @Override
    public Pharmacy getPharmacyByIdWithWorkplaces(Long id) {
        return pharmacyRepository.getPharmacyByIdAndFetchWorkplaces(id);
    }

    @Override
    public boolean insertMedicine(Long pharmacyId, Long medicineId) {
        Pharmacy p = getPharmacyById(pharmacyId);
        if (p == null) {
            return false;
        }

        Medicine medicine = medicineService.findOne(medicineId);
        if (medicine == null) {
            return false;
        }

        MedicinePrice medicinePrice = new MedicinePrice(0, new Date().getTime(), new ArrayList<>());
        List<MedicinePrice> list = new ArrayList<>();
        list.add(medicinePrice);

        MedicineItem medicineItem = new MedicineItem(0, list, medicine);

        p.getPriceList().getMedicineItems().add(medicineItem);
        pharmacyRepository.save(p);
        return true;
    }

    public Pharmacy getPharmacyById(Long id) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(id);
//        Check this
        return pharmacy.orElse(null);
    }

    public List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue) {
        return pharmacyRepository.searchPharmaciesByNameOrCity(searchValue);
    }

    public List<Pharmacy> filterPharmacies(String gradeValue, String distanceValue, double longitude, double latitude) {
        List<Pharmacy> pharmacies = pharmacyRepository.findAll();
        if (!gradeValue.equals("")) {
            pharmacies = pharmacies.stream().filter(p -> doFilteringByGrade(p.getAvgGrade(), gradeValue)).collect(Collectors.toList());
        }
        if (!distanceValue.equals("")) {
            pharmacies = pharmacies.stream().filter(p -> doFilteringByDistance(p.getAddress(), distanceValue, longitude, latitude)).collect(Collectors.toList());
        }
        return pharmacies;
    }

    public boolean doFilteringByGrade(double avgGrade, String gradeValue) {
        if (gradeValue.equals("HIGH") && avgGrade > 3.0) return true;
        if (gradeValue.equals("MEDIUM") && avgGrade == 3.0) return true;
        return gradeValue.equals("LOW") && avgGrade < 3.0;
    }

    public boolean doFilteringByDistance(Address address, String distanceValue, double longitude, double latitude) {
        if (distanceValue.equals("5LESS") && calculateDistance(address, longitude, latitude) <= 5) return true;
        if (distanceValue.equals("10LESS") && calculateDistance(address, longitude, latitude) <= 10) return true;
        return distanceValue.equals("10HIGHER") && calculateDistance(address, longitude, latitude) > 10;
    }

    public double calculateDistance(Address address, double lon2, double lat2) {
        double lat1 = address.getLocation().getLatitude();
        double lon1 = address.getLocation().getLongitude();

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist * 1.609344);
        }
    }

    public boolean insertPharmacy(Pharmacy pharmacy) {
        if (pharmacy != null) {
            pharmacyRepository.save(pharmacy);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(long id) {
        if (pharmacyRepository.findById(id).isPresent()) {
            pharmacyRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(long id, Pharmacy pharmacy) {
        Optional<Pharmacy> p = pharmacyRepository.findById(id);
        if (p.isPresent()) {
            Pharmacy p2 = p.get();
            p2.setDescription(pharmacy.getDescription());
            p2.setAddress(pharmacy.getAddress());
            p2.setName(pharmacy.getName());
            p2.setAddress(pharmacy.getAddress());
            pharmacyRepository.save(p2);
            return true;
        } else {
            return false;
        }
    }

    public List<Pharmacy> getAll() {
        return pharmacyRepository.findAll();
    }

    @Override
    public List<Pharmacy> getPharmaciesByMedicineId(Long id) {
        return pharmacyRepository.findPharmaciesByMedicineId(id);
    }

    @Override
    public Pharmacy getPharmacyByIdAndPriceList(Long id) {
        return pharmacyRepository.getPharmacyByIdAndPriceList(id);
    }

    @Override
    public List<Pharmacy> getPharmaciesByFreePharmacists(long date, Sort sorter) {

        List<Pharmacy> pharmacies = pharmacyRepository.findPharmaciesFetchWorkplaces(sorter);

        if (pharmacies.size() == 0) return null;

        Date requestedDateAndTime = new Date(date);
        Date requestedDateAndTimeEnd = new Date(date + 15 * 60000L);
        Date today = new Date();
        if (requestedDateAndTime.before(today)) return null;

        Calendar c = Calendar.getInstance();
        c.setTime(requestedDateAndTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        List<Pharmacy> chosenPharmacies = new ArrayList<>();
        for (Pharmacy p : pharmacies) {
            boolean pharmacyIsChosen = false;
            for (Workplace wp : p.getWorkplaces()) {
                if (pharmacyIsChosen) break;
                if (wp.getWorker().getUserType() != UserType.PHARMACIST) continue;

                for (WorkDay wd : wp.getWorkDays()) {
                    if (wd.getWeekday().ordinal() + 1 == dayOfWeek) {
                        int requestedTime = c.get(Calendar.HOUR_OF_DAY);

                        if (requestedTime < wd.getStartTime() || requestedTime > wd.getEndTime()) continue;

                        boolean isPharmacistFree = true;
                        // Kad prebacimo u lazi, prepravi da kad gore dobavim apoteke, fetchujem i workere i njihove appointemnte
                        for (Appointment a : workerRepository.getPharmacyWorkerForCalendar(wp.getWorker().getId()).getAppointmentList()) {
                            if (p.getId().equals(a.getPharmacy().getId()) && a.getAppointmentState() == AppointmentState.CANCELLED)
                                continue;
                            Date startTime = new Date(a.getStartTime());
                            Date endTime = new Date(a.getEndTime());
                            if (startTime.compareTo(requestedDateAndTime) == 0) {
                                isPharmacistFree = false;
                                break;
                            }
                            if (requestedDateAndTime.after(startTime) && requestedDateAndTime.before(endTime)) {
                                isPharmacistFree = false;
                                break;
                            }
                            if (requestedDateAndTimeEnd.after(startTime) && requestedDateAndTimeEnd.before(endTime)) {
                                isPharmacistFree = false;
                                break;
                            }
                        }

                        if (!isPharmacistFree) continue;

                        chosenPharmacies.add(p);
                        pharmacyIsChosen = true;
                        break;
                    }
                }
            }
        }

        return chosenPharmacies;
    }

    @Override
    public Pharmacy getPharmacyWithMedicineNoAllergies(Long pharmid, Long patientid) {
        return pharmacyRepository.getPharmacyMedicineWithoutAllergies(pharmid, patientid);
    }

    @Override
    public Pharmacy getPharmacyWithAlternativeForMedicineNoAllergies(Long pharmid, Long patientID, Long medicineID) {
        return pharmacyRepository.getPharmacyWithAlternativeForMedicineNoAllergies(pharmid, patientID, medicineID);
    }

    @Override
    public List<Pharmacy> getSubscribedPharmaciesByPatientId(Long id) {
        List<Pharmacy> chosenPharmacies = new ArrayList<>();

        List<Pharmacy> pharmacies = pharmacyRepository.findPharmaciesFetchSubscribed();

        for (Pharmacy p : pharmacies) {
            for (Patient patient : p.getSubscribers()) {
                if (patient.getId().equals(id)) {
                    chosenPharmacies.add(p);
                    break;
                }
            }
        }

        return chosenPharmacies;
    }

    @Override
    public List<Pharmacy> getPharmaciesByPatientId(Long id) {
        Patient patient = patientRepository.findByIdFetchReceivedMedicinesAndPharmacy(id);
        if (patient == null) return null;

        List<Pharmacy> pharmacies = pharmacyRepository.findPharmaciesFetchFinishedCheckupsAndConsultations();
        if (pharmacies == null) return null;

        List<Pharmacy> chosenPharmacies = new ArrayList<>();

        for (Pharmacy p : pharmacies) {
            for (Appointment a : p.getAppointments()) {
                if (a.getPatient().getId().equals(id)) {
                    addPharmacy(chosenPharmacies, a.getPharmacy());
                    break;
                }
            }
        }

        for (MedicineReservation mr : patient.getMedicineReservation()) {
            if (!mr.getState().equals(ReservationState.RECEIVED)) continue;
            addPharmacy(chosenPharmacies, mr.getPharmacy());
        }

        return chosenPharmacies;
    }

    private List<Pharmacy> addPharmacy(List<Pharmacy> pharmacies, Pharmacy p) {

        if (pharmacies.size() == 0) {
            pharmacies.add(p);
            return pharmacies;
        }

        for (Pharmacy p1 : pharmacies) {
            if (p1.getId().equals(p.getId())) {
                return pharmacies;
            }
        }

        pharmacies.add(p);
        return pharmacies;

    }

    public boolean createInquiry(Long workerID, Long medicineItemID, Pharmacy pharmacy){
        Optional<PharmacyWorker> workerOptional = workerRepository.findById(workerID);
        Optional<MedicineItem> medicineItemOptional = medicineItemRepository.findById(medicineItemID);
        if (workerOptional.isEmpty() || medicineItemOptional.isEmpty()){
            return false;
        }
        PharmacyWorker pharmacyWorker = workerOptional.get();
        MedicineItem medicineItem = medicineItemOptional.get();
        Inquiry inquiry = new Inquiry(pharmacy, pharmacyWorker, medicineItem, Instant.now().toEpochMilli());
        inquiryRepository.save(inquiry);
        return true;
    }

    @Override
    public List<PharmacyERecipeDTO> getAllWithMedicineInStock(ERecipeDTO eRecipeDTO) {
        // TODO provera alergena, podataka i tako to
        if (eRecipeDTO.getState() == ERecipeState.REJECTED) {
            throw new RuntimeException("This prescription in not valid");
        }
        if (eRecipeDTO.getState() == ERecipeState.PROCESSED) {
            throw new RuntimeException("This prescription has already been processes");
        }
        for (var er: eRecipeDTO.geteRecipeItems()) {
            if (er.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be grater than 0");
            }
        }
        if (eRecipeDTO.getCode() == null) {
            throw new RuntimeException("Prescription code can not be null");
        }
        if (eRecipeDTO.getPrescriptionDate() > System.currentTimeMillis()) {
            throw new RuntimeException("Prescription date is not valid");
        }
        Optional<Patient> pat = patientRepository.findById(eRecipeDTO.getPatientId());
        if (pat.isEmpty()) {
            throw new RuntimeException("Invalid patient's ID");
        }
        // get pharmacies with required medicine
        List<Pharmacy> pharmaciesWithMedInStock = new ArrayList<>();
        for (ERecipeItem eRecipeItem : eRecipeDTO.geteRecipeItems()) {
            List<Pharmacy> p = pharmacyRepository
                    .findPharmacyByIdWithMedOnStock(eRecipeItem.getMedicineCode(), eRecipeItem.getQuantity());
            if (pharmaciesWithMedInStock.isEmpty()) {
                pharmaciesWithMedInStock.addAll(p);
            } else {
                // intersection
                pharmaciesWithMedInStock = pharmacyIntersection(pharmaciesWithMedInStock, p);
            }
        }
        // calculate total price for every pharmacy
        List<PharmacyERecipeDTO> pharmacyERecipeDTOS = new ArrayList<>();
        for (var p : pharmaciesWithMedInStock) {
            Optional<Pharmacy> optionalPharmacy = pharmacyRepository.getPharmacyByIdFetchPriceList(p.getId());
            if (optionalPharmacy.isEmpty()) {
                return new ArrayList<>();
            }
            double totalPrice = calculateTotalPrice(optionalPharmacy.get(), eRecipeDTO);
            PharmacyERecipeDTO pharmacyERecipeDTO = modelMapper.map(optionalPharmacy.get(), PharmacyERecipeDTO.class);
            pharmacyERecipeDTO.setTotalPrice(totalPrice);
            pharmacyERecipeDTOS.add(pharmacyERecipeDTO);
        }

        return pharmacyERecipeDTOS;
    }

    private double calculateTotalPrice(Pharmacy pharmacy, ERecipeDTO eRecipeDTO) {
        double price = 0;
        for (var ri: eRecipeDTO.geteRecipeItems()) {
            Optional<MedicineItem> mi = pharmacy.getPriceList().getMedicineItems().stream().filter(medicineItem -> medicineItem.getMedicine().getCode().equals(ri.getMedicineCode())).findFirst();
            if (mi.isPresent()) {
                price += mi.get().getMedicinePrices().get(mi.get().getMedicinePrices().size() - 1).getPrice() * ri.getQuantity();
            }
        }
        return price;
    }

    private List<Pharmacy> pharmacyIntersection(List<Pharmacy> list1, List<Pharmacy> list2) {
        List<Pharmacy> list = new ArrayList<Pharmacy>();

        for (Pharmacy p : list1) {
            if(list2.stream().anyMatch(pharmacy -> pharmacy.getId().equals(p.getId()))) {
                list.add(p);
            }
        }
        return list;
    }

}
