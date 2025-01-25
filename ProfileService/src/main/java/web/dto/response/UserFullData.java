package web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.dto.request.EmailDto;
import web.dto.request.UserDataDto;
import web.model.Info;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFullData {

    private Info info;

    private UserDataDto data;

    private EmailDto email;
}
