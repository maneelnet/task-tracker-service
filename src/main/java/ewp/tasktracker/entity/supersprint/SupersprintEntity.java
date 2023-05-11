package ewp.tasktracker.entity.supersprint;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.api.dto.supersprint.SupersprintDto;
import ewp.tasktracker.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "supersprints")
public class SupersprintEntity extends BaseEntity {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime startAt = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime endAt = LocalDateTime.now();
    private String authorId;

    public static boolean updateSupersprint(SupersprintEntity supersprintEntity, SupersprintDto dto) {
        supersprintEntity.setName(dto.getName());
        supersprintEntity.setStartAt(dto.getStartAt());
        supersprintEntity.setEndAt(dto.getEndAt());
        supersprintEntity.setAuthorId(dto.getAuthorId());
        supersprintEntity.setUpdatedAt(LocalDateTime.now());
        return true;
    }
}
