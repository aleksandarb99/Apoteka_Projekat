package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.OfferState;
import com.team11.PharmacyProject.enums.OrderState;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.inquiry.Inquiry;
import com.team11.PharmacyProject.inquiry.InquiryService;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineRepository;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.myOrder.MyOrder;
import com.team11.PharmacyProject.myOrder.MyOrderRepository;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.offer.OfferRepository;
import com.team11.PharmacyProject.offer.OfferService;
import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.priceList.PriceListRepository;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import com.team11.PharmacyProject.supplierItem.SupplierItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    InquiryService inquiryService;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private MyOrderRepository myOrderRepository;
    @Autowired
    private PriceListRepository priceListRepository;
    @Autowired
    private OfferService offerService;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SupplierItemRepository supplierItemRepository;
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public List<SupplierItem> getStockForId(long id) {
        Optional<Supplier> supplier = supplierRepository.findSupplierWithSupplierItemsUsingId(id);
        if (supplier.isPresent()) {
            return supplier.get().getSupplierItems();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void insertStockItem(long supplierId, SupplierStockItemDTO stockItemDTO) throws CustomException {
        if (stockItemDTO == null) {
            throw new CustomException("Oops");
        }

        Optional<Supplier> s = supplierRepository.findSupplierWithSupplierItemsUsingId(supplierId);
        if (s.isEmpty()) {
            throw new CustomException("Supplier not found");
        }
        Supplier supp = s.get();

        if (stockItemDTO.getAmount() < 0 || stockItemDTO.getAmount() > 1000) {
            throw new CustomException("Amount must be between 1 and 1000");
        }

        SupplierItem supplierItem = new SupplierItem();
        Optional<Medicine> medicine = medicineRepository.findById(stockItemDTO.getMedicineId());
        // Ako lek postoji i nije vec dodat
        if (medicine.isEmpty()) {
            throw new CustomException("Medicine not found");
        }

        Optional<SupplierItem> optionalSupplierItem = supplierItemRepository.getSupplierItemBySupplierIdCode(supp.getId(), stockItemDTO.getMedicineId());
        if (optionalSupplierItem.isEmpty()) {
            supplierItem.setAmount(stockItemDTO.getAmount());
            supplierItem.setMedicine(medicine.get());
            supplierItem.setSupplier(supp);
            supplierItemRepository.save(supplierItem);
        } else {
            optionalSupplierItem.get().setAmount(optionalSupplierItem.get().getAmount() + stockItemDTO.getAmount());
            supplierItemRepository.save(optionalSupplierItem.get());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void acceptOffer(Long selectedOfferId, Long orderId, Long adminId) {
        Map<String, List<Offer>> offersForOrder = getOffersByOrderId(orderId);

        Optional<MyOrder> order = myOrderRepository.getMyOrderById(orderId);
        if (order.isEmpty())
            throw new RuntimeException("Order with id " + orderId + " does not exist!");
        if (!order.get().getAdmin().getId().equals(adminId)) {
            throw new RuntimeException("You have no permissions for this order!");
        }

        String email = "abuljevic8@gmail.com";


        for (String key : offersForOrder.keySet()) {
            List<Offer> offers = offersForOrder.get(key);

            for (Offer o : offers) {
                if (o.getId().equals(selectedOfferId)) {
                    o.setOfferState(OfferState.ACCEPTED);
                    try {
                        emailService.notifySupplierThatHisOfferIsAccepted(email, order.get(), key);
                    } catch (Exception e) {
                        throw new RuntimeException("Sending email failed");
                    }
                    offerService.save(o);
                } else {
                    o.setOfferState(OfferState.DENIED);
                    try {
                        emailService.notifySupplierThatHisOfferIsRefused(email, order.get(), key);
                    } catch (Exception e) {
                        throw new RuntimeException("Sending email failed");
                    }
                    offerService.save(o);
                }
            }
        }

        MyOrder order1 = order.get();
        order1.setOrderState(OrderState.ENDED);
        myOrderRepository.save(order1);

        PriceList priceList = priceListRepository.findPriceListForTransaction(order1.getPharmacy().getPriceList().getId());
        for (OrderItem item : order1.getOrderItem()) {
            for (MedicineItem mitem : priceList.getMedicineItems()) {
                if (item.getMedicine().getId().equals(mitem.getMedicine().getId())) {
                    mitem.setAmount(mitem.getAmount() + item.getAmount());
                    break;
                }
            }
        }

        priceListRepository.save(priceList);

        List<Inquiry> list = inquiryService.getInquiriesByPharmacyId(order1.getPharmacy().getId());
        for (Inquiry i : list) {
            for (OrderItem item : order1.getOrderItem()) {
                if (item.getMedicine().getId().equals(i.getMedicineItems().getMedicine().getId())) {
                    i.setActive(false);
                    inquiryService.save(i);
                }
            }
        }
    }

    @Override
    public Map<String, List<Offer>> getOffersByOrderId(long orderId) {
        List<Supplier> supplierList = supplierRepository.findAllWithOffers();
        Map<String, List<Offer>> offerMap = new HashMap();

        for (Supplier s : supplierList) {
            String key = s.getFirstName() + " " + s.getLastName();
            for (Offer o : s.getOffers()) {
                if (o.getOrder().getId().equals(orderId) && o.getOfferState().equals(OfferState.PENDING)) {
                    if (offerMap.containsKey(key))
                        offerMap.get(key).add(o);
                    else
                        offerMap.put(key, new ArrayList<Offer>());
                    offerMap.get(key).add(o);
                }
            }
        }
        return offerMap;
    }

    @Override
    public List<OfferListDTO> getOffersForId(long supplierId) {
        Optional<Supplier> supplier = supplierRepository.findSupplierWithOffersUsingId(supplierId);
        return supplier.map(value -> value.getOffers().stream()
                .map(o -> new OfferListDTO(o.getId(), o.getPrice(), o.getDeliveryDate(), o.getOfferState(), o.getOrder().getId()))
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    @Transactional(
            rollbackFor = {CustomException.class}
    )
    public void insertOffer(long suppId, OfferListDTO offerDTO) throws CustomException {
        if (offerDTO == null) {
            throw new CustomException("Oops");
        }
        Optional<Supplier> s = supplierRepository.findSupplierWithOffersUsingId(suppId);
        if (s.isEmpty()) {
            throw new CustomException("Supplier not found");
        }
        Supplier supp = s.get();

        // Ako je cena negativna, onda nije okej
        if (offerDTO.getPrice() < 0) {
            throw new CustomException("Price must be greater than 0");
        }
        // Dobavi order za offer
        Optional<MyOrder> order = myOrderRepository.getMyOrderById(offerDTO.getOrderId());
        if (order.isEmpty())
            throw new CustomException("Order not found");
        // Ako je orderId vec u offerima, onda nemoj da dodajes
        boolean contains = supp.getOffers().stream().anyMatch(o -> o.getOrder().getId().equals(order.get().getId()));
        if (contains)
            throw new CustomException("Offer already added");
        // Ako je prosao, onda ne moze da se doda (150s ogranicenja)
        if (order.get().getDeadline() < (System.currentTimeMillis() + 150000))
            throw new CustomException("You are past the deadline");

        // Da li je svaka stavka iz ordera prisutna kod suppliera
        for (OrderItem oi : order.get().getOrderItem()) {
            // Proveri da li ima na stanju (bice zakljucano)
            Optional<SupplierItem> optionalSupplierItem = supplierItemRepository.getSupplierItemBySupplierIdCodeAmount(supp.getId(), oi.getMedicine().getCode(), oi.getAmount());
            if (optionalSupplierItem.isEmpty()) {
                throw new CustomException("Item(s) not in stock");
            }
            SupplierItem supplierItem = optionalSupplierItem.get();
            supplierItem.setAmount(supplierItem.getAmount() - oi.getAmount());
            supplierItemRepository.save(supplierItem);
        }

        Offer offer = new Offer();
        offer.setOfferState(offerDTO.getOfferState());
        offer.setDeliveryDate(offerDTO.getDeliveryDate());
        offer.setPrice(offerDTO.getPrice());
        offer.setOrder(order.get());
        supp.getOffers().add(offer);
        supplierRepository.save(supp);
    }

    @Override
    @Transactional
    public void updateOffer(long suppId, OfferListDTO offerDTO) throws CustomException {
        if (offerDTO == null) {
            throw new CustomException("Oops");
        }
        Optional<Supplier> s = supplierRepository.findSupplierWithOffersUsingId(suppId);
        if (s.isEmpty()) {
            throw new CustomException("Supplier not found");
        }
        Supplier supp = s.get();

        // Ako je cena negativna, onda nije okej
        if (offerDTO.getPrice() < 0) {
            throw new CustomException("Price must be greater than 0");
        }
        // Dobavi order za offer
        Optional<MyOrder> order = myOrderRepository.getMyOrderById(offerDTO.getOrderId());
        if (order.isEmpty())
            throw new CustomException("Order not found");
        // Mora da bude ista porudzbina
        if (order.get().getId() != offerDTO.getOrderId())
            throw new CustomException("Offer does not belong to this order");
        // Ako je prosao, onda ne moze da se menja
        if (order.get().getDeadline() < (System.currentTimeMillis()))
            throw new CustomException("You are past the deadline");

        Optional<Offer> offerToUpdate = supp.getOffers().stream()
                .filter(o -> o.getOrder().getId().equals(offerDTO.getOrderId()))
                .collect(Collectors.toList()).stream().findFirst();

        if (offerToUpdate.isEmpty())
            throw new CustomException("Offer to update not found");
        offerToUpdate.get().setPrice(offerDTO.getPrice());
        offerToUpdate.get().setDeliveryDate(offerDTO.getDeliveryDate());
        offerToUpdate.get().setOfferState(offerDTO.getOfferState());
        offerRepository.save(offerToUpdate.get());
    }
}
