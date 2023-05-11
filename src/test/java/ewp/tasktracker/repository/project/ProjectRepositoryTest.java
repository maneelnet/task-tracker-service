package ewp.tasktracker.repository.project;

import ewp.tasktracker.entity.project.ProjectEntity;
import ewp.tasktracker.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;
    private ProjectEntity projectEntity;

    @BeforeEach
    void setUp() {
        this.projectEntity = getProjectEntity();
    }

    @Test
    void projectSaveTest() {
        ProjectEntity saveProject = projectRepository.save(projectEntity);
        assertThat(saveProject).isNotNull();
        assertThat(saveProject.getId()).isNotEmpty();
    }


    @Test
    void projectFindAllTest() {
        ProjectEntity projectEntity2 = getProjectEntity();
        projectRepository.save(projectEntity);
        projectRepository.save(projectEntity2);
        List<ProjectEntity> entities = projectRepository.findAll();
        assertThat(entities).isNotNull();
        assertThat(entities.size()).isEqualTo(2);
    }

    @Test
    void projectFindByIdTest() {
        projectRepository.save(projectEntity);
        ProjectEntity savedEntity = projectRepository.findById(projectEntity.getId()).get();
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isEqualTo(projectEntity.getId());
    }

    @Test
    void projectUpdateTest() {
        projectRepository.save(projectEntity);
        ProjectEntity project = projectRepository.findById(projectEntity.getId()).get();
        project.setStatus(Status.INACTIVE);
        ProjectEntity updatedProject = projectRepository.save(project);
        assertThat(project.getStatus()).isNotNull();
        assertThat(project.getStatus()).isEqualTo(updatedProject.getStatus());
    }

    @Test
    void projectDeleteByIdTest() {
        ProjectEntity projectEntity2 = getProjectEntity();
        projectRepository.save(projectEntity);
        projectRepository.save(projectEntity2);
        ProjectEntity deletedProject = projectRepository.findById(projectEntity2.getId()).get();
        projectRepository.deleteById(deletedProject.getId());
        List<ProjectEntity> entities = projectRepository.findAll();
        assertThat(entities.size()).isEqualTo(1);
    }

    private static ProjectEntity getProjectEntity() {
        return ProjectEntity.builder()
                .name("1")
                .description("1")
                .status(Status.ACTIVE)
                .workloadId("1")
                .authorId("1")
                .build();
    }
}