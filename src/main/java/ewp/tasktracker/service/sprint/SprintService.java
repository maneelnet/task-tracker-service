package ewp.tasktracker.service.sprint;

import ewp.tasktracker.api.dto.sprint.CreateSprintRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.sprint.SprintDto;
import org.springframework.data.domain.Pageable;

public interface SprintService {
    /**
     * Метод сохраняет сущность пришедшую от клиента, если она валидна
     */
    SprintDto save(CreateSprintRq dto);

    /**
     * Метод ищет элемент по айди пришедшему из урла от клиента
     */
    SprintDto findById(String id);

    /**
     * Метод возвращает PageDto<SprintDto></> с заданным количеством элементов, в поле Total лежит общее количество эпиков
     */
    PageDto<SprintDto> findAllByPageRequest(Pageable pageable);

    /**
     * Метод передает пришедшее SprintDto в класс SprintEntity и там у него обновляются поля
     */
    SprintDto updateSprintFromController(SprintDto supersprintDto);

    /**
     * Метод ищет элементы по имени в заданном количестве, возвращает pageDto, в качестве параметра size
     * устанавливает общее количество спринтов удовлетворяющих условиям поиска
     */
    PageDto<SprintDto> findAllByName(String name, Pageable pageable);
}
