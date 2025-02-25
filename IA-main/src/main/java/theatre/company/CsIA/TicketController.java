package theatre.company.CsIA;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final EmailService emailService;

    public TicketController(TicketService ticketService, EmailService emailService) {
        this.ticketService = ticketService;
        this.emailService = emailService;
    }

    @GetMapping("/buy-ticket")
    public String buyTicket(Model model) {
        model.addAttribute("ticket", ticketService.getTicketSettings());
        return "buy-ticket";
    }

    @PostMapping("/purchase")
    public String purchaseTicket(
        @AuthenticationPrincipal OidcUser user, 
        @RequestParam Integer quantity // Fixed parameter binding
    ) {
        if (user == null) return "redirect:/";
        
        Purchase purchase = ticketService.createPurchase(user.getEmail(), quantity);
        emailService.sendConfirmationEmails(user.getEmail(), purchase);
        
        return "redirect:/success";
    }
}
