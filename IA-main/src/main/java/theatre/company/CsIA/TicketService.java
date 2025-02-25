package theatre.company.CsIA;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketSettingsRepository settingsRepository;
    private final PurchaseRepository purchaseRepository;

    public TicketService(
        TicketSettingsRepository settingsRepository, 
        PurchaseRepository purchaseRepository
    ) {
        this.settingsRepository = settingsRepository;
        this.purchaseRepository = purchaseRepository;
        initializeSettings();
    }

    private void initializeSettings() {
        if (!settingsRepository.existsById(1L)) {
            TicketSettings settings = new TicketSettings();
            settings.setImageUrl("/default-image.jpg");
            settings.setPrice(50.0);
            settingsRepository.save(settings);
        }
    }

    public TicketSettings getTicketSettings() {
        return settingsRepository.findById(1L)
                .orElseThrow(() -> new NoSuchElementException("Ticket settings not found"));
    }

    public Purchase createPurchase(String email, int quantity) {
        TicketSettings settings = getTicketSettings();
        Purchase purchase = new Purchase();
        purchase.setUserEmail(email);
        purchase.setQuantity(quantity);
        purchase.setTotalPrice(quantity * settings.getPrice());
        purchase.setPurchaseDate(LocalDateTime.now());
        return purchaseRepository.save(purchase);
    }

    public void updateTicketSettings(String imageUrl, double price) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTicketSettings'");
    }
}