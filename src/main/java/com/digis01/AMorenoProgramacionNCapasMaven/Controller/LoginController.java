package com.digis01.AMorenoProgramacionNCapasMaven.Controller;

import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

@Controller
public class LoginController {

    private static String rutaBase = "http://localhost:8080";

    @GetMapping("/Login")
    public String login() {
        return "Login";
    }

    @PostMapping("/Login")
    public String loginPost(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {

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

            String rol = getRolFromToken(token);

            if (rol.equals("Administrador")) {
                return "redirect:/Usuario";
            } else {
                return "redirect:/Usuario/Perfil";
            }

        } catch (Exception ex) {
            model.addAttribute("error", true);
            ex.printStackTrace();
            return "Login";
        }
    }

    public String getRolFromToken(String token) {

        String[] chunks = token.split("\\.");
        String payload = new String(Base64.getDecoder().decode(chunks[1]));

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(payload, Map.class);
            
            var roles = (java.util.List<Map<String, String>>) map.get("role");
            if (roles != null && !roles.isEmpty()) {
                return roles.get(0).get("authority").replace("ROLE_", "");
            }
            
            return (String) map.get("role");

        } catch (Exception e) {                                                                                                                                                                                
            return null;
        }
    }
    
    @GetMapping("/token")
    @ResponseBody
    public String getToken(HttpSession session){
        return (String) session.getAttribute("token");
    }

}
