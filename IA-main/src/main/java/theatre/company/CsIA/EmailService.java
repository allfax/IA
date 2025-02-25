package theatre.company.CsIA;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final String adminEmail;

    public EmailService(
        JavaMailSender mailSender, 
        @Value("${admin.emails}") String[] adminEmails
    ) {
        this.mailSender = mailSender;
        // Safely get the first admin email or use a default
        this.adminEmail = (adminEmails != null && adminEmails.length > 0) 
            ? adminEmails[0] 
            : "admin@theatre.com";
    }
    public void sendConfirmationEmails(String customerEmail, Purchase purchase) {
        sendCustomerEmail(customerEmail, purchase);
        sendAdminNotification(purchase);
    }

    private void sendCustomerEmail(String email, Purchase purchase) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Ticket Purchase Confirmation");
        message.setText(
            "Thank you for purchasing " + purchase.getQuantity() + " tickets!\n" +
            "Total: $" + purchase.getTotalPrice() + "\n" +
            "Purchase Date: " + purchase.getPurchaseDate()
        );
        mailSender.send(message);
    }

    private void sendAdminNotification(Purchase purchase) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("New Ticket Purchase");
        message.setText(
            "New purchase made by: " + purchase.getUserEmail() + "\n" +
            "Quantity: " + purchase.getQuantity() + "\n" +
            "Total: $" + purchase.getTotalPrice()
        );
        mailSender.send(message);
    }
}
