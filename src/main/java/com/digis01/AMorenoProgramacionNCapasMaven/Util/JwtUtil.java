package com.digis01.AMorenoProgramacionNCapasMaven.Util;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import tools.jackson.databind.ObjectMapper;

public class JwtUtil {

    public static String getRolFromToken(String token) {

        try {
            String[] chunks = token.split("\\.");
            String payload = new String(Base64.getDecoder().decode(chunks[1]));

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(payload, Map.class);

            var roles = (List<Map<String, String>>) map.get("role");

            if (roles != null && !roles.isEmpty()) {
                return roles.get(0).get("authority").replace("ROLE_", "");
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    public static int getIdFromToken(String token) {

        try {
            String[] chunks = token.split("\\.");
            String payload = new String(Base64.getDecoder().decode(chunks[1]));

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(payload, Map.class);

            return (int) map.get("idUsuario");

        } catch (Exception e) {
            return 0;
        }
    }
}
