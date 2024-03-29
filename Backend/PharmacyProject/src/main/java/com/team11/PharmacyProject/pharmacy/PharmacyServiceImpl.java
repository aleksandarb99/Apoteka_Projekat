package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.appointment.AppointmentService;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyERecipeDTO;
import com.team11.PharmacyProject.dto.user.PharmacyWorkerInfoDTO;
import com.team11.PharmacyProject.eRecipe.ERecipe;
import com.team11.PharmacyProject.eRecipe.ERecipeRepository;
import com.team11.PharmacyProject.eRecipe.ERecipeService;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.ERecipeState;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.inquiry.Inquiry;
import com.team11.PharmacyProject.inquiry.InquiryRepository;
import com.team11.PharmacyProject.inquiry.InquiryService;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemRepository;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservationService;
import com.team11.PharmacyProject.myOrder.MyOrder;
import com.team11.PharmacyProject.offer.OfferService;
import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.priceList.PriceListRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.users.user.UserRepository;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PriceListRepository priceListRepository;

    @Autowired
    UserRepository userRepository;

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

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    MedicineReservationService medicineReservationService;

    @Autowired
    OfferService offerService;

    @Autowired
    ERecipeService eRecipeService;

    @Autowired
    ERecipeRepository eRecipeRepository;


    private double calculateProfitBeetwenTimestamps(long start, long end, long pharmacyId) {
        double profitFromAppointments = appointmentService.calculateProfit(start, end, pharmacyId);
        double profitFromDrugSelling = medicineReservationService.calculateProfit(start, end, pharmacyId);
        double orderExpenses = offerService.calculateExpenses(start, end, pharmacyId);
        return profitFromAppointments + profitFromDrugSelling - orderExpenses;
    }

    @Override
    public Pharmacy getPharmacyWithSubsribers(Long pharmacyId) {
        Optional<Pharmacy> p = pharmacyRepository.getPharmacyWithSubribers(pharmacyId);
        if (p.isPresent())
            return p.get();
        return null;
    }

    @Override
    public Map<String, Double> getInfoForReport(String period, Long pharmacyId, int duration) {
        String[] monthNames = {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};

        Map<String, Double> data = new LinkedHashMap<>();
        Calendar calendar = Calendar.getInstance();

        long currTime = Instant.now().toEpochMilli();

        calendar.setTime(new Date(currTime));
        calendar.set(calendar.get(Calendar.YEAR) - 1, calendar.get(Calendar.MONTH), 1, 0, 0, 0);

        if (period.equals("Monthly")) {
            int count = 0;
            for (int i = calendar.get(Calendar.MONTH) + 1; i < 12; i++) {
                String key = monthNames[i] + "-" + calendar.get(Calendar.YEAR);
                data.put(key, 0.0);
                count++;
            }
            for (int i = 0; i < 12 - count; i++) {
                String key = monthNames[i] + "-" + (calendar.get(Calendar.YEAR) + 1);
                data.put(key, 0.0);
            }

            calendar.setTime(new Date(currTime));
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
            for (int i = 0; i < duration; i++) {
                long startOfMonth = calendar.getTimeInMillis();
                int next;
                if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
                    next = 0;
                } else {
                    next = calendar.get(Calendar.MONTH) + 1;
                }

                if (next != 0)
                    calendar.set(calendar.get(Calendar.YEAR), next, 1, 0, 0, 0);
                else
                    calendar.set(calendar.get(Calendar.YEAR) + 1, 0, 1, 0, 0, 0);

                long endOfMonth = Math.min(currTime, calendar.getTimeInMillis());

                calendar.setTime(new Date(startOfMonth));

                double profitOfThisMonth = calculateProfitBeetwenTimestamps(startOfMonth, endOfMonth, pharmacyId);

                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String key = monthNames[month] + "-" + year;
                if (data.containsKey(key))
                    data.put(key, data.get(key) + profitOfThisMonth);

                if (month == 0) {
                    calendar.set(calendar.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 1, 0, 0, 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), month - 1, 1, 0, 0, 0);

                }

            }
        } else if (period.equals("Quarterly")) {
            if (calendar.get(Calendar.MONTH) <= 2) {
                data.put(2 + "-" + calendar.get(Calendar.YEAR), 0.0);
                data.put(3 + "-" + calendar.get(Calendar.YEAR), 0.0);
                data.put(4 + "-" + calendar.get(Calendar.YEAR), 0.0);
                data.put(1 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
            } else if (calendar.get(Calendar.MONTH) <= 5) {
                data.put(3 + "-" + calendar.get(Calendar.YEAR), 0.0);
                data.put(4 + "-" + calendar.get(Calendar.YEAR), 0.0);
                data.put(1 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
                data.put(2 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
            } else if (calendar.get(Calendar.MONTH) <= 8) {
                data.put(4 + "-" + calendar.get(Calendar.YEAR), 0.0);
                data.put(1 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
                data.put(2 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
                data.put(3 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
            } else {
                data.put(1 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
                data.put(2 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
                data.put(3 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
                data.put(4 + "-" + (calendar.get(Calendar.YEAR) + 1), 0.0);
            }


            calendar.setTime(new Date(currTime));

            if (calendar.get(Calendar.MONTH) <= 2) {
                calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
            } else if (calendar.get(Calendar.MONTH) <= 5) {
                calendar.set(calendar.get(Calendar.YEAR), Calendar.APRIL, 1, 0, 0, 0);
            } else if (calendar.get(Calendar.MONTH) <= 8) {
                calendar.set(calendar.get(Calendar.YEAR), Calendar.JULY, 1, 0, 0, 0);
            } else {
                calendar.set(calendar.get(Calendar.YEAR), Calendar.OCTOBER, 1, 0, 0, 0);
            }

            for (int i = 0; i < duration; i++) {
                long startOfQ = calendar.getTimeInMillis();
                int next;
                next = (calendar.get(Calendar.MONTH) + 3) % 12;

                if (next > calendar.get(Calendar.MONTH))
                    calendar.set(calendar.get(Calendar.YEAR), next, 1, 0, 0, 0);
                else
                    calendar.set(calendar.get(Calendar.YEAR) + 1, 0, 1, 0, 0, 0);

                long endOfQ = Math.min(currTime, calendar.getTimeInMillis());

                calendar.setTime(new Date(startOfQ));

                double profitOfThisMonth = calculateProfitBeetwenTimestamps(startOfQ, endOfQ, pharmacyId);

                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int quarter;

                if (month <= 2) {
                    quarter = 1;
                } else if (month <= 5) {
                    quarter = 2;
                } else if (month <= 8) {
                    quarter = 3;
                } else {
                    quarter = 4;
                }

                String key = quarter + "-" + year;
                if (data.containsKey(key))
                    data.put(key, data.get(key) + profitOfThisMonth);

                if (month <= 2) {
                    calendar.set(calendar.get(Calendar.YEAR) - 1, 9, 1, 0, 0, 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), month - 3, 1, 0, 0, 0);

                }
            }
        } else {
            calendar.setTime(new Date(currTime));
            calendar.set(calendar.get(Calendar.YEAR) - 9, Calendar.JANUARY, 1, 0, 0, 0);

            data.put(calendar.get(Calendar.YEAR) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 1) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 2) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 3) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 4) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 5) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 6) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 7) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 8) + "", 0.0);
            data.put((calendar.get(Calendar.YEAR) + 9) + "", 0.0);

            calendar.setTime(new Date(currTime));
            calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
            for (int i = 0; i < duration; i++) {
                long startOfYear = calendar.getTimeInMillis();
                calendar.set(calendar.get(Calendar.YEAR) + 1, Calendar.JANUARY, 1, 0, 0, 0);
                long endOfYear = Math.min(currTime, calendar.getTimeInMillis());

                calendar.setTime(new Date(startOfYear));

                double profitOfThisMonth = calculateProfitBeetwenTimestamps(startOfYear, endOfYear, pharmacyId);
                int year = calendar.get(Calendar.YEAR);

                String key = year + "";
                if (data.containsKey(key))
                    data.put(key, data.get(key) + profitOfThisMonth);

                calendar.set(calendar.get(Calendar.YEAR) - 1, Calendar.JANUARY, 1, 0, 0, 0);
            }
        }
        return data;
    }

    @Override
    public Pharmacy getPharmacyIdByAdminId(Long id) {
        List<Pharmacy> list = pharmacyRepository.findAllWithAdmins();
        for (Pharmacy p :
                list) {
            for (MyUser admin :
                    p.getAdmins()) {
                if (id.equals(admin.getId()))
                    return p;
            }
        }
        return null;
    }

    @Override
    public boolean subscribe(long pharmacyId, long patientId) throws CustomException {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findPharmacyByIdFetchSubscribed(pharmacyId);
        if (pharmacy.isEmpty())
            throw new CustomException("Invalid pharmacy");
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty())
            throw new CustomException("Invalid patient");
        if (pharmacy.get().getSubscribers().stream().anyMatch(p -> p.getId() == patientId))
            throw new CustomException("Already subscribed");
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

    public void insertPharmacy(Pharmacy pharmacy, List<PharmacyWorkerInfoDTO> pharmacyAdmins) throws CustomException {
        if (pharmacy == null) {
            throw new CustomException("Null pharmacy?");
        }
        pharmacy.getAdmins().clear();
        for (var pwi: pharmacyAdmins) {
            var admin = userRepository.findFirstById(pwi.getId());
            if (admin == null) {
                throw new CustomException("Admin not found");
            }
            pharmacy.getAdmins().add(admin);
        }
        PriceList aa = new PriceList();
        aa.setMedicineItems(new ArrayList<MedicineItem>());
        aa.setPharmacy(pharmacy);
        pharmacy.setPriceList(aa);
        pharmacy.setConsultationPrice(500d);
        pharmacyRepository.save(pharmacy);
    }

    public void delete(long id) throws CustomException {
        if (pharmacyRepository.findById(id).isEmpty())
            throw new CustomException("Pharmacy not found");
        pharmacyRepository.deleteById(id);
    }

    public void update(long id, Pharmacy pharmacy) {
        Optional<Pharmacy> p = pharmacyRepository.findById(id);
        if (p.isPresent()) {
            Pharmacy p2 = p.get();
            p2.setDescription(pharmacy.getDescription());
            p2.setAddress(pharmacy.getAddress());
            p2.setName(pharmacy.getName());
            p2.setAddress(pharmacy.getAddress());
            p2.setPointsForAppointment(pharmacy.getPointsForAppointment());
            pharmacyRepository.save(p2);
        } else {
            throw new RuntimeException("Pharmacy with id " + id + " does not exist!");
        }
    }

    public List<Pharmacy> getAll() {
        return pharmacyRepository.findPharmacyFetchAdmins();
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

        if (pharmacies.size() == 0) throw new RuntimeException("There's no pharamcies in database!");

        Date requestedDateAndTime = new Date(date);
        Date requestedDateAndTimeEnd = new Date(date + 15 * 60000L);
        Date today = new Date();
        if (requestedDateAndTime.before(today)) throw new RuntimeException("Requested date is in the past!");

        Calendar c = Calendar.getInstance();
        c.setTime(requestedDateAndTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        List<Pharmacy> chosenPharmacies = new ArrayList<>();
        for (Pharmacy p : pharmacies) {
            boolean pharmacyIsChosen = false;
            for (Workplace wp : p.getWorkplaces()) {
                if (pharmacyIsChosen) break;
                if (!wp.getWorker().getRole().getName().equals("PHARMACIST")) continue;

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

        List<ERecipe> eRecipes = eRecipeRepository.findByPatientId(id);
        if (eRecipes != null) {
            for (ERecipe recipe : eRecipes) {
                addPharmacy(chosenPharmacies, recipe.getPharmacy());
            }
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

    public boolean createInquiry(Long workerID, Long medicineItemID, Pharmacy pharmacy) {
        if (inquiryRepository.isAlreadyQueried(workerID, medicineItemID,
                Instant.now().minus(15, ChronoUnit.MINUTES).toEpochMilli())) {
            return false; //vec je napravio query za taj lek isti radnik pre 15 minuta, da ne bi pravio svaki put
        }
        Optional<PharmacyWorker> workerOptional = workerRepository.findById(workerID);
        Optional<MedicineItem> medicineItemOptional = medicineItemRepository.findById(medicineItemID);
        if (workerOptional.isEmpty() || medicineItemOptional.isEmpty()) {
            return false;
        }
        PharmacyWorker pharmacyWorker = workerOptional.get();
        MedicineItem medicineItem = medicineItemOptional.get();
        Inquiry inquiry = new Inquiry(pharmacy, pharmacyWorker, medicineItem, Instant.now().toEpochMilli());
        inquiryRepository.save(inquiry);
        return true;
    }

    @Override
    public void checkIfRecipeIsInPharmacy(ERecipeDTO eRecipeDTO, Long pharmacyId) {
        List<Pharmacy> pharmaciesWithMedInStock = pharmacyRepository.findPharmacyWithMedOnStock(eRecipeDTO.geteRecipeItems());
        boolean flag = false;
        for (Pharmacy p :
                pharmaciesWithMedInStock) {
            if (pharmacyId.equals(p.getId())) {
                flag = true;
                break;
            }
        }
        if (!flag)
            throw new RuntimeException("E-Recipe is not in the pharmacy!");

    }

    @Override
    public List<PharmacyERecipeDTO> getAllWithMedicineInStock(ERecipeDTO eRecipeDTO, String sortBy, String order) throws CustomException {
        if (eRecipeDTO.getState() == ERecipeState.REJECTED) {
            throw new CustomException("This prescription in not valid");
        }
        if (eRecipeDTO.getState() == ERecipeState.PROCESSED) {
            throw new CustomException("This prescription has already been processes");
        }
        for (var er : eRecipeDTO.geteRecipeItems()) {
            if (er.getQuantity() <= 0) {
                throw new CustomException("Quantity must be grater than 0");
            }
        }
        if (eRecipeDTO.getCode() == null) {
            throw new CustomException("Prescription code can not be null");
        }
        if (eRecipeDTO.getPrescriptionDate() > System.currentTimeMillis()) {
            throw new CustomException("Prescription date is not valid");
        }
        Optional<Patient> pat = patientRepository.findById(eRecipeDTO.getPatientId());
        if (pat.isEmpty()) {
            throw new CustomException("Invalid patient's ID");
        }
        // get pharmacies with required medicine
        List<Pharmacy> pharmaciesWithMedInStock = pharmacyRepository.findPharmacyWithMedOnStock(eRecipeDTO.geteRecipeItems());

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

        return sorted(pharmacyERecipeDTOS, sortBy, order);
    }

    private List<PharmacyERecipeDTO> sorted(List<PharmacyERecipeDTO> toSort, String sortBy, String order) {
        Class<PharmacyERecipeDTO> c = PharmacyERecipeDTO.class;
        int direction = order.equals("ASC") ? 1 : -1;
        List<PharmacyERecipeDTO> pS = toSort;
        try {
            Field field = c.getDeclaredField(sortBy);
            field.setAccessible(true);
            Comparator<PharmacyERecipeDTO> comp = (o1, o2) -> {
                try {
                    if (field.getType() == String.class) {
                        String s1 = (String) field.get(o1);
                        String s2 = (String) field.get(o2);
                        return s1.compareToIgnoreCase(s2) * direction;
                    } else if (field.getType() == Integer.class || field.getType() == Double.class) {
                        return ((Double) field.get(o1)).compareTo((Double) field.get(o2)) * direction;
                    }
                } catch (IllegalAccessException e) {
                    return 0;
                }
                return 0;
            };

            pS = toSort.stream().sorted(comp).collect(Collectors.toList());
            return pS;
        } catch (NoSuchFieldException ignored) {
        }
        return toSort;
    }

    private double calculateTotalPrice(Pharmacy pharmacy, ERecipeDTO eRecipeDTO) {
        double price = 0;
        for (var ri : eRecipeDTO.geteRecipeItems()) {
            Optional<MedicineItem> mi = pharmacy.getPriceList().getMedicineItems().stream().filter(medicineItem -> medicineItem.getMedicine().getCode().equals(ri.getMedicineCode())).findFirst();
            if (mi.isPresent()) {
                price += mi.get().getMedicinePrices().get(mi.get().getMedicinePrices().size() - 1).getPrice() * ri.getQuantity();
            }
        }
        return price;
    }

}
