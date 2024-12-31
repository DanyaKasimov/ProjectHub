package web.utils;

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
            e.printStackTrace();
            throw new InvalidDataException("Ошибка парсинга сообщения в объект.");
        }
    }

    public static String validate(String input, String field) {
        String output = input.replace("=", ":");

        output = output.replace(field + ":", "\"" + field + "\":");

        int startIndex = output.indexOf("\"" + field + "\":") + field.length() + 3;
        int endIndex = output.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = output.indexOf("}", startIndex);
        }

        String value = output.substring(startIndex, endIndex).trim();
        output = output.substring(0, startIndex) + "\"" + value + "\"" + output.substring(endIndex);

        return output;
    }
}
