package ewp.tasktracker.api.dto.supersprint;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.entity.supersprint.SupersprintEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupersprintDto {
    @NotBlank
    @Size(min = 2, max = 36)
    private String id;
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime startAt;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime endAt;
    @NotBlank
    @Size(min = 2, max = 36)
    private String authorId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;


    public SupersprintEntity toEntity() {
        return new SupersprintEntity(
                this.name,
                this.startAt,
                this.endAt,
                this.authorId
        );
    }

    public SupersprintDto(SupersprintEntity supersprintEntity) {
        this.id = supersprintEntity.getId();
        this.name = supersprintEntity.getName();
        this.startAt = supersprintEntity.getStartAt();
        this.endAt = supersprintEntity.getEndAt();
        this.authorId = supersprintEntity.getAuthorId();
        this.createdAt = supersprintEntity.getCreatedAt();
        this.updatedAt = supersprintEntity.getUpdatedAt();
    }

    public static SupersprintDto from(SupersprintEntity supersprintEntity) {
        return new SupersprintDto(supersprintEntity);
    }
}
