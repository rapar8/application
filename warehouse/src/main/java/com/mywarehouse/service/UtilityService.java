package com.mywarehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

@Service
public class UtilityService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");


    public <T> T getModel(MultipartFile file, Class<T> articleClass) throws IOException {
        try (InputStream in = file.getInputStream()) {
            T object_ = objectMapper.readValue(new InputStreamReader(in), articleClass)  ;
            return object_;
        }
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

}
