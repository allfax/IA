package theatre.company.CsIA;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TicketSettings {
    @Id
    private Long id = 1L; // Hardcoded ID since only one settings entry exists
    private String imageUrl;
    private double price;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}