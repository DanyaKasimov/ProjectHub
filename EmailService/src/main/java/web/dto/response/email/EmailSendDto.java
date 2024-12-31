package web.dto.response.email;

import lombok.Data;

@Data
public class EmailSendDto {

    private String address;

    private Object content;
}
