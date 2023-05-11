package ewp.tasktracker.api.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SuccessDto extends ResponseDto {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public SuccessDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
