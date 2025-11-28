package utils.data;

import java.io.File;
import java.io.IOException;

import utils.model.LoginData;
import tools.jackson.databind.ObjectMapper;

public class LoginDataReader {
    private static final String LOGIN_DATA_PATH = "src\\test\\java\\EducacionIT\\SauceDemoTest\\utils\\data\\loginData.json";

    public static LoginData[] readLoginData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(LOGIN_DATA_PATH);
        if (!file.exists()) {
            throw new RuntimeException("No se encontró el archivo: " + file.getAbsolutePath());
        }
        return mapper.readValue(file, LoginData[].class);
    }
}