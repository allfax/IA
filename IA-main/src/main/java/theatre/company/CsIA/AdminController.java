package theatre.company.CsIA;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Validated
@Controller
public class AdminController {

    private final TicketService ticketService;

    public AdminController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPanel(Model model) {
        model.addAttribute("ticket", ticketService.getTicketSettings());
        return "admin";
    }

    @PostMapping("/admin/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateSettings(
        @RequestParam @NotBlank String imageUrl,
        @RequestParam @DecimalMin("1.0") double price,
        RedirectAttributes redirectAttributes
    ) {
        try {
            ticketService.updateTicketSettings(imageUrl, price);
            redirectAttributes.addFlashAttribute("success", "Settings updated!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }
}