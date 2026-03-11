package com.digis01.AMorenoProgramacionNCapasMaven.Controller;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Pais;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Rol;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        ResponseEntity<Result<Rol>> responseEntityRol
                = restTemplate.exchange(rutaBase + "/Api/Usuario/Rol",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Rol>>() {
                });

        Result<Rol> resultRol = responseEntityRol.getBody();

        if (resultRol != null && resultRol.objects != null) {
            model.addAttribute("roles", resultRol.objects);
        }

        ResponseEntity<Result<Pais>> responseEntityPais
                = restTemplate.exchange(
                        rutaBase + "/Api/Usuario/Pais",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Pais>>() {
                });

        Result<Pais> resultPais = responseEntityPais.getBody();

        if (resultPais != null && resultPais.objects != null) {
            model.addAttribute("paises", resultPais.objects);
        }

        return "Usuario";
    }

    @GetMapping("Form")
    public String Form(Model model) {
        Usuario usuario = new Usuario();

        model.addAttribute("usuario", usuario);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<Pais>> responseEntityPais
                = restTemplate.exchange(rutaBase + "/Api/Usuario/Pais",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Pais>>() {
                });
        Result<Pais> resultPais = responseEntityPais.getBody();

        if (resultPais != null && resultPais.objects != null) {
            model.addAttribute("paises", resultPais.objects);
        }

        ResponseEntity<Result<Rol>> responseEntityRol
                = restTemplate.exchange(rutaBase + "/Api/Usuario/Rol",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Rol>>() {
                });

        Result<Rol> resultRol = responseEntityRol.getBody();
        if (resultRol != null && resultRol.objects != null) {
            model.addAttribute("roles", resultRol.objects);
        }
        return "Formulario";
    }

    @PostMapping("Form")
    public String ADD(@ModelAttribute Usuario usuario,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        try {

            RestTemplate restTemplate = new RestTemplate();

            if (usuario.getdireccion() != null) {

                Direccion direccion = usuario.getdireccion();

                Colonia colonia = new Colonia();
                colonia.setIdColonia(usuario.getdireccion().getColonia().getIdColonia());

                direccion.setColonia(colonia);

                List<Direccion> direcciones = new ArrayList<>();
                direcciones.add(direccion);

                usuario.setDirecciones(direcciones);
            }

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            HttpHeaders jsonHeaders = new HttpHeaders();
            jsonHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Usuario> usuarioEntity = new HttpEntity<>(usuario, jsonHeaders);

            body.add("usuario", usuarioEntity);

            if (imagenFile != null && !imagenFile.isEmpty()) {

                ByteArrayResource resource = new ByteArrayResource(imagenFile.getBytes()) {
                    @Override
                    public String getFilename() {
                        return imagenFile.getOriginalFilename();
                    }
                };

                body.add("imagen", resource);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);

            ResponseEntity<Result<Usuario>> responseEntity
                    = restTemplate.exchange(
                            rutaBase + "/Api/Usuario/Add",
                            HttpMethod.POST,
                            requestEntity,
                            new ParameterizedTypeReference<Result<Usuario>>() {
                    });

            Result<Usuario> result = responseEntity.getBody();

            if (result != null && result.correct) {
                redirectAttributes.addFlashAttribute("success", true);
            } else {
                redirectAttributes.addFlashAttribute("error", true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", true);
        }

        return "redirect:/Usuario";
    }
}
