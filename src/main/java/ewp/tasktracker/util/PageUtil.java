package ewp.tasktracker.util;

import ewp.tasktracker.config.TaskTrackerProperties;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public final class PageUtil {
    private final TaskTrackerProperties props;

    public Pageable buildPageable(Integer pageSize, Integer pageNumber) {
        return Pageable
                .ofSize(checkPageSize(pageSize))
                .withPage(checkPageNumber(pageNumber));
    }

    private Integer checkPageSize(Integer pageSize) {
        return pageSize <= 0
                ? props.getPageDefaultSize()
                : (Math.min(pageSize, props.getPageMaxSize()));
    }

    private Integer checkPageNumber(Integer pageNumber) {
        return pageNumber < 0
                ? 0
                : pageNumber;
    }
}