package web.dto.response;

import lombok.Data;

@Data
public class EmailSendDto {

    private String address;

    private Object content;
}
