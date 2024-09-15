package pl.javastart.dianaart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", " Nie odnaleziono podanego konta lub dane sa niewłasciwe. Nie masz konta? Zarejestruj się.");
        }
        return "login"; // Nazwa pliku HTML odpowiadającego za stronę logowania
     // Strona logowania
    }
}
