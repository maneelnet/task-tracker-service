package ewp.tasktracker.service.workload;

import ewp.tasktracker.api.dto.workload.CreateWorkloadRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.workload.UpdateWorkloadRq;
import ewp.tasktracker.api.dto.workload.WorkloadDto;
import ewp.tasktracker.entity.workload.WorkloadEntity;
import ewp.tasktracker.enums.ActivityStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WorkloadServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(WorkloadServiceTest.class);
    @Autowired
    private WorkloadService workloadService;

    // Для корректной работы testFindAll() таблица в БД изначально должна быть пустой
    @Test
    public void testFindAll() {
        List<CreateWorkloadRq> listDto = new ArrayList<>();
        listDto.add(createWorkload());
        listDto.add(createWorkload());
        listDto.add(createWorkload());

        for (CreateWorkloadRq dto : listDto) {
            WorkloadEntity savedWorkload = workloadService.save(dto);
            LOG.info("Testing method findAll. Workload saved with id: {}, name: {}, status: {}, authorId: {}",
                    savedWorkload.getId(),
                    savedWorkload.getName(),
                    savedWorkload.getStatus(),
                    savedWorkload.getAuthorId());
        }

        PageDto<WorkloadDto> foundPageDto = workloadService.findAll(3, 0);
        List<WorkloadDto> foundWorkloadDtoList = foundPageDto.getItems();

        assertEquals(listDto.size(), foundWorkloadDtoList.size());
        for (int i = 0; i < listDto.size(); i++) {
            assertEquals(listDto.get(i).getName(), foundWorkloadDtoList.get(i).getName());
            assertEquals(listDto.get(i).getStatus(), foundWorkloadDtoList.get(i).getStatus());
            assertEquals(listDto.get(i).getAuthorId(), foundWorkloadDtoList.get(i).getAuthorId());
        }
    }

    @Test
    public void testSave() {
        CreateWorkloadRq dto = createWorkload();
        WorkloadEntity savedWorkload = workloadService.save(dto);
        LOG.info("Testing method save. Workload saved with id: {}, name: {}, status: {}, authorId: {}",
                savedWorkload.getId(),
                savedWorkload.getName(),
                savedWorkload.getStatus(),
                savedWorkload.getAuthorId());

        assertNotNull(savedWorkload);
        assertNotNull(savedWorkload.getId());
        assertEquals(dto.getName(), savedWorkload.getName());
        assertEquals(dto.getStatus(), savedWorkload.getStatus());
        assertEquals(dto.getAuthorId(), savedWorkload.getAuthorId());
    }

    @Test
    public void testFindById() {
        CreateWorkloadRq dto = createWorkload();
        WorkloadEntity savedWorkload = workloadService.save(dto);
        LOG.info("Testing method findById. Workload saved with id: {}, name: {}, status: {}, authorId: {}",
                savedWorkload.getId(),
                savedWorkload.getName(),
                savedWorkload.getStatus(),
                savedWorkload.getAuthorId());

        WorkloadEntity foundWorkload = workloadService.findById(savedWorkload.getId());

        assertNotNull(foundWorkload);
        assertEquals(savedWorkload.getName(), foundWorkload.getName());
        assertEquals(savedWorkload.getStatus(), foundWorkload.getStatus());
        assertEquals(savedWorkload.getAuthorId(), foundWorkload.getAuthorId());
    }

    @Test
    public void testUpdate() {
        CreateWorkloadRq dto = createWorkload();
        WorkloadEntity savedWorkload = workloadService.save(dto);
        LOG.info("Testing method update. Workload saved with id: {}, name: {}, status: {}, authorId: {}",
                savedWorkload.getId(),
                savedWorkload.getName(),
                savedWorkload.getStatus(),
                savedWorkload.getAuthorId());

        WorkloadEntity updatedWorkload = workloadService.update(updateWorkload(savedWorkload.getId(), dto));
        LOG.info("Testing method update. Workload updated with id: {}, name: {}, status: {}, authorId: {}",
                updatedWorkload.getId(),
                updatedWorkload.getName(),
                updatedWorkload.getStatus(),
                updatedWorkload.getAuthorId());

        assertNotNull(savedWorkload);
        assertNotNull(updatedWorkload);
        assertEquals(savedWorkload.getId(), updatedWorkload.getId());
        assertEquals("Test name Updated", updatedWorkload.getName());
        assertEquals(ActivityStatus.ACTIVE, updatedWorkload.getStatus());
        assertEquals("Test author Updated", updatedWorkload.getAuthorId());
    }

    private static CreateWorkloadRq createWorkload() {
        List<CreateWorkloadRq> list = new ArrayList<>();
        list.add(new CreateWorkloadRq("Test name 1", ActivityStatus.INACTIVE, "Test authorId 1"));
        list.add(new CreateWorkloadRq("Test name 2", ActivityStatus.INACTIVE, "Test authorId 2"));
        list.add(new CreateWorkloadRq("Test name 3", ActivityStatus.INACTIVE, "Test authorId 3"));
        list.add(new CreateWorkloadRq("Test name 4", ActivityStatus.INACTIVE, "Test authorId 4"));

        Random random = new Random();
        return list.get(random.nextInt(4));
    }

    private static UpdateWorkloadRq updateWorkload(String id, CreateWorkloadRq dto) {
        dto.setName("Test name Updated");
        dto.setStatus(ActivityStatus.ACTIVE);
        dto.setAuthorId("Test author Updated");
        return new UpdateWorkloadRq(id, dto.getName(), dto.getStatus(), dto.getAuthorId());
    }
}
