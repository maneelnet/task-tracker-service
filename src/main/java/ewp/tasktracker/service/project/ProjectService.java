package ewp.tasktracker.service.project;

import ewp.tasktracker.api.dto.project.CreateProjectRq;
import ewp.tasktracker.api.dto.project.UpdateProjectRq;
import ewp.tasktracker.entity.project.ProjectEntity;

import java.util.List;

/**
 * Сервис для работы с проектами
 */
public interface ProjectService {

    ProjectEntity save(CreateProjectRq projectRq);

    ProjectEntity findById(String id);

    List<ProjectEntity> findAll();

    boolean deleteById(String id);

    ProjectEntity update(UpdateProjectRq updateProjectRq);
}
