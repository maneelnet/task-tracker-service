package ewp.tasktracker.service.project;

import ewp.tasktracker.api.dto.project.CreateProjectRq;
import ewp.tasktracker.api.dto.project.UpdateProjectRq;
import ewp.tasktracker.entity.project.ProjectEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.project.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public ProjectEntity save(CreateProjectRq createProjectRq) {
        ProjectEntity projectEntity = createProjectRq.toEntity();
        return projectRepository.save(projectEntity);
    }

    @Override
    public ProjectEntity findById(String id) {
        return projectRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Project not found by id: " + id));
    }

    @Override
    public List<ProjectEntity> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        if (projectRepository.findById(id).isEmpty()) {
            return false;
        } else {
            projectRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public ProjectEntity update(UpdateProjectRq updateProjectRq) {
        ProjectEntity projectEntity = findById(updateProjectRq.getId());
        projectEntity.setName(updateProjectRq.getName());
        projectEntity.setDescription(updateProjectRq.getDescription());
        projectEntity.setStatus(updateProjectRq.getStatus());
        projectEntity.setUpdatedAt(LocalDateTime.now());
        return projectRepository.save(projectEntity);
    }
}

