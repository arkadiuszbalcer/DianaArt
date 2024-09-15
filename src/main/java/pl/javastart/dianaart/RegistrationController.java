package pl.javastart.dianaart;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.javastart.dianaart.user.EmailService;
import pl.javastart.dianaart.user.UserService;
import pl.javastart.dianaart.user.dto.UserRegistrationDto;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final EmailService emailService;

    public RegistrationController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(UserRegistrationDto userRegistrationDto) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration"; // Return to the registration form with errors
        }

        try {
            userService.registerUserWithDefaultRole(userRegistrationDto);
            emailService.sendEmail(userRegistrationDto.getEmail(), "Welcome!", "Thank you for registering with us.");
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful! Please log in.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("registrationError", "Użytkownik o podanym mailu istnieje.");
            return "redirect:/registration"; // Redirect to the registration page on error
        }

        return "redirect:/login"; // Redirect to login page after successful registration

    }
}
