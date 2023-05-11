package ewp.tasktracker.service.project;

import ewp.tasktracker.api.dto.project.CreateProjectRq;
import ewp.tasktracker.api.dto.project.UpdateProjectRq;
import ewp.tasktracker.entity.project.ProjectEntity;
import ewp.tasktracker.enums.Status;
import ewp.tasktracker.repository.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    public void init() {
        when(projectRepository.save(any(ProjectEntity.class)))
                .then(returnsFirstArg());
    }

    @Test
    void save() {
        ProjectEntity savedProjectEntity1 = projectService.save(getCreateProjectRq());
        ProjectEntity savedProjectEntity2 = projectService.save(getCreateProjectRq());
        assertThat(savedProjectEntity1).isNotNull();
        assertThat(savedProjectEntity2).isNotNull();
        assertNotEquals(savedProjectEntity1, savedProjectEntity2);
    }


    @Test
    void findById() {
        ProjectEntity savedProjectEntity = projectService.save(getCreateProjectRq());
        assert savedProjectEntity.getId() != null;
        when(projectRepository.findById(savedProjectEntity.getId())).thenReturn(Optional.of(savedProjectEntity));
        ProjectEntity findEntity = projectService.findById(savedProjectEntity.getId());
        assertThat(findEntity).isNotNull();
        assertThat(findEntity.getId()).isEqualTo(savedProjectEntity.getId());
    }

    @Test
    void findAll() {
        ProjectEntity savedProjectEntity1 = projectService.save(getCreateProjectRq());
        ProjectEntity savedProjectEntity2 = projectService.save(getCreateProjectRq());
        when(projectRepository.findAll()).thenReturn(List.of(savedProjectEntity1, savedProjectEntity2));
        List<ProjectEntity> projectEntityList = projectService.findAll();
        assertThat(projectEntityList).hasSize(2);
    }

    @Test
    void deleteById() {
        ProjectEntity projectEntity = projectService.save(getCreateProjectRq());
        assert projectEntity.getId() != null;
        when(projectRepository.findById(projectEntity.getId())).thenReturn(Optional.of(projectEntity));
        assertAll(
                () -> projectService.deleteById(projectEntity.getId())
        );
    }

    @Test
    void update() {
        ProjectEntity savedProjectEntity = projectService.save(getCreateProjectRq());
        UpdateProjectRq dto = getUpdateProjectRq(savedProjectEntity);
        assert savedProjectEntity.getId() != null;
        when(projectRepository.findById(savedProjectEntity.getId())).thenReturn(Optional.of(savedProjectEntity));
        when(projectRepository.save(savedProjectEntity)).thenReturn(savedProjectEntity);
        ProjectEntity updatedEntity = projectService.update(dto);
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedEntity.getId()).isEqualTo(savedProjectEntity.getId());
        assertThat(updatedEntity.getDescription()).isEqualTo("updated");
    }

    private static CreateProjectRq getCreateProjectRq() {
        return CreateProjectRq.builder()
                .name("qrName1")
                .description("qrDescription1")
                .status(Status.ACTIVE)
                .workloadId("qrWrId1")
                .author_id("qrAuId1")
                .build();
    }

    private static UpdateProjectRq getUpdateProjectRq(ProjectEntity projectEntity) {
        return UpdateProjectRq.builder()
                .id(projectEntity.getId())
                .name(projectEntity.getName())
                .description("updated")
                .status(Status.ACTIVE)
                .build();
    }
}