package ewp.tasktracker.service.epic;

import ewp.tasktracker.api.dto.epic.CreateEpicRq;
import ewp.tasktracker.api.dto.epic.EpicDto;
import ewp.tasktracker.api.dto.response.PageDto;
import org.springframework.data.domain.Pageable;

/**
 * Сервис для работы с эпиками
 */

public interface EpicService {

    /**
     * Метод сохраняет сущность пришедшую от клиента, если она валидна
     */
    EpicDto save(CreateEpicRq dto);

    /**
     * Метод ищет элемент по айди пришедшему из урла от клиента
     */
    EpicDto findById(String id);

    /**
     * Метод возвращает PageDto<EpicDto></> с заданным количеством элементов, в поле Total лежит общее количество эпиков
     */
    PageDto<EpicDto> findAllByPageRequest(Pageable pageable);

    /**
     * Метод передает пришедшее EpicDto в класс EpicEntity и там у него обновляются поля
     */
    EpicDto updateEpicFromController(EpicDto epicDto);

    /**
     * Метод ищет элементы по имени в заданном количестве, возвращает pageDto, в качестве параметра size
     * устанавливает общее количество эпиков удовлетворяющих условиям поиска
     */
    PageDto<EpicDto> findAllByName(String name, Pageable pageable);

}
