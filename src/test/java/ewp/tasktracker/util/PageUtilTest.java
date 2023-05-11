package ewp.tasktracker.util;

import ewp.tasktracker.config.TaskTrackerProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PageUtilTest {
    private TaskTrackerProperties props;
    private PageUtil pageUtil;

    @BeforeEach
    public void setUp() {
        props = new TaskTrackerProperties();
        props.setPageDefaultSize(20);
        props.setPageMaxSize(40);
        pageUtil = new PageUtil(props);
    }

    @Test
    public void buildPageableWithValidParams() {
        int pageSize = 20;
        int pageNumber = 1;

        Pageable pageable = pageUtil.buildPageable(pageSize, pageNumber);

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPageSize()).isEqualTo(pageSize);
        assertThat(pageable.getPageNumber()).isEqualTo(pageNumber);
    }

    @Test
    public void buildPageableWithInvalidPageSize() {
        int pageSize = -1;
        int pageNumber = 1;

        Pageable pageable = pageUtil.buildPageable(pageSize, pageNumber);

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPageSize()).isEqualTo(props.getPageDefaultSize());
        assertThat(pageable.getPageNumber()).isEqualTo(pageNumber);
    }

    @Test
    public void buildPageableWithPageSizeGreaterThanMax() {
        int pageSize = 200;
        int pageNumber = 1;

        Pageable pageable = pageUtil.buildPageable(pageSize, pageNumber);

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPageSize()).isEqualTo(props.getPageMaxSize());
        assertThat(pageable.getPageNumber()).isEqualTo(pageNumber);
    }

    @Test
    public void buildPageableWithInvalidPageNumber() {
        int pageSize = 20;
        int pageNumber = -1;

        Pageable pageable = pageUtil.buildPageable(pageSize, pageNumber);

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPageSize()).isEqualTo(pageSize);
        assertThat(pageable.getPageNumber()).isEqualTo(0);
    }
}