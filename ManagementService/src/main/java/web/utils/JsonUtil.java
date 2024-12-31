package web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import web.exception.InvalidDataException;

@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromString(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        } catch (Exception e) {
            log.warn("Ошибка парсинга сообщения в объект: {}", content);
            throw new InvalidDataException("Ошибка парсинга сообщения в объект.");
        }
    }

    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Ошибка парсинга объекта в строку: {}", obj);
            throw new InvalidDataException("Ошибка парсинга объекта в строку.");
        }
    }

}
