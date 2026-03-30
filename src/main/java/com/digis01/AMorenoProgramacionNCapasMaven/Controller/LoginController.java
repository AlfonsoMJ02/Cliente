package com.digis01.AMorenoProgramacionNCapasMaven.Controller;

import com.digis01.AMorenoProgramacionNCapasMaven.Util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    private static String rutaBase = "http://localhost:8080";

    @GetMapping("/Login")
    public String login() {
        return "Login";
    }

    @PostMapping("/Login")
    public String loginPost(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", username);
        loginRequest.put("password", password);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    rutaBase + "/Auth/Login",
                    loginRequest,
                    Map.class
            );

            String token = (String) response.getBody().get("Key");

            session.setAttribute("token", token);

            String rol = JwtUtil.getRolFromToken(token);

            if ("Administrador".equals(rol)) {
                return "redirect:/Usuario";
            } else {
                return "redirect:/Usuario/Perfil";
            }

        } catch (Exception ex) {
            model.addAttribute("error", true);
            return "Login";
        }
    }
}
