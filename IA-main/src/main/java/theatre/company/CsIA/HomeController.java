package theatre.company.CsIA;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            model.addAttribute("name", user.getFullName());
            model.addAttribute("email", user.getEmail());
        }
        return "index";
    }
}