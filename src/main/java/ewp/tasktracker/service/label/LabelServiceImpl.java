package ewp.tasktracker.service.label;

import ewp.tasktracker.api.dto.label.CreateLabelRq;
import ewp.tasktracker.api.dto.label.LabelDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.label.LabelEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.label.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * Сервис для работы с метками задачи
 */

@AllArgsConstructor
@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    /**
     * Ищет метку задачи по Id
     */
    @Override
    public LabelDto findById(String id) {
        return new LabelDto(labelRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Label not found, id: " + id)));
    }

    /**
     * Сохранить метку задачи
     */
    @Override
    public LabelDto save(CreateLabelRq dto) {
        return new LabelDto(labelRepository.save(dto.toEntity()));
    }

    /**
     * Если размер страницы больше 40, возвращаем максимум 40 элементов.
     * Если в параметрах ничего не пришло (дефолтное значение "nothing", берем из бд любые элементы, в заданном количестве.
     * Если в параметрах taskId который есть в БД возвращаем метки с нужным taskId, в заданном количестве.
     */
    @Override
    public PageDto<LabelDto> findAllByPageRequest(String taskId, Pageable pageable) {
        if (taskId == null) {
            Page<LabelEntity> labelEntityPage = labelRepository.findAll(pageable);
            return new PageDto<>(labelEntityPage.getContent().stream()
                    .map(labelEntity -> new LabelDto(labelEntity)).collect(Collectors.toList()),
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    (int)labelEntityPage.getTotalElements());
        } else {
            Page<LabelEntity> labelEntityPage = labelRepository.findAllByTaskId(taskId, pageable);
            return new PageDto<>(labelEntityPage.getContent().stream()
                    .map(labelEntity -> new LabelDto(labelEntity)).collect(Collectors.toList()),
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    (int)labelEntityPage.getTotalElements());
        }
    }

    /**
     * Обновляет метку задачи
     */
    @Override
    @Transactional
    public LabelDto updateLabelFromController(LabelDto dto) {
        LabelEntity.updateLabel(labelRepository.findById(dto.getId()).get(), dto);
        return dto;
    }

    /**
     * Удаляет метку задачи
     */
    @Override
    @Transactional
    public LabelDto deleteLabelEntityById(String id) {
        LabelDto labelDto = findById(id);
        labelRepository.deleteById(id);
        return labelDto;
    }
}
