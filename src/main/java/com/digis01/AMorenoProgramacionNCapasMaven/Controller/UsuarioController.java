package com.digis01.AMorenoProgramacionNCapasMaven.Controller;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Estado;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Pais;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Rol;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/Usuario")
public class UsuarioController {

    private static String rutaBase = "http://localhost:8080";

    @GetMapping
    public String GetAll(Model model) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Result<Usuario>> responseEntity
                = restTemplate.exchange(
                        rutaBase + "/Api/Usuario/GetAll",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Usuario>>() {
                });

        Result<Usuario> result = responseEntity.getBody();

        model.addAttribute("usuarios", result.objects);

        return "Usuario";
    }
    
    
    @GetMapping("Form")
    public String Form(@PathVariable int idPais, Model model) {

        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Result<Pais>> responseEntityPais = 
                restTemplate.exchange(
                        rutaBase + "/Api/Usuario/Pais",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Pais>>(){});

        Result<Pais> resultPais = responseEntityPais.getBody();

        if (resultPais != null && resultPais.objects != null) {
            model.addAttribute("paises", resultPais.objects);
        }
        
//        ResponseEntity<Result<Estado>> responseEntityEstado = 
//                restTemplate.exchange(rutaBase + "/Api/Usuario/Estado/GetByIdPais/", 
//                        HttpMethod.GET,
//                        ,
//                        new ParameterizedTypeReference<Result<Estado>>(){});
//        
//        Result<Estado> resultEstado = responseEntityEstado.getBody();
//
//        if (resultPais != null && resultPais.objects != null) {
//            model.addAttribute("estados", resultEstado.objects);
//        }
        
        ResponseEntity<Result<Rol>> responseEntityRol = 
                restTemplate.exchange(rutaBase + "/Api/Usuario/Rol", 
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Rol>>(){});
        
        Result<Rol> resultRol = responseEntityRol.getBody();
        
        if (resultRol != null && resultRol.objects != null) {
            model.addAttribute("roles", resultRol.objects);
        }
        
        return "Formulario";
    }
    
    
}