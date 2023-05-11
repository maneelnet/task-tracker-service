package ewp.tasktracker.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Setter
@MappedSuperclass
public class BaseEntity implements Persistable<String> {
    @Id
    protected String id = UUID.randomUUID().toString();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime createdAt = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime updatedAt = LocalDateTime.now();

    @Transient
    private Boolean justCreated = false;

    public BaseEntity(String id) {
        this.id = id;
        this.justCreated = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return justCreated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

