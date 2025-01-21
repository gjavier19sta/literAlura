package aluraLatam.literAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConvertData implements IConvertirData {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try{
            return mapper.readValue(json,clase);
        }catch(JsonProcessingException e){
            throw new RuntimeException("Error al obtener datos del json");
        }
    }
}
