package ewp.tasktracker.service.supersprint;

import ewp.tasktracker.api.dto.supersprint.CreateSupersprintRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.supersprint.SupersprintDto;
import org.springframework.data.domain.Pageable;

public interface SupersprintService {
    /**
     * Метод сохраняет сущность пришедшую от клиента, если она валидна
     */
    SupersprintDto save(CreateSupersprintRq dto);

    /**
     * Метод ищет элемент по айди пришедшему из урла от клиента
     */
    SupersprintDto findById(String id);

    /**
     * Метод возвращает PageDto<SupersprintDto></> с заданным количеством элементов, в поле Total лежит общее количество эпиков
     */
    PageDto<SupersprintDto> findAllByPageRequest(Pageable pageable);

    /**
     * Метод передает пришедшее SupersprintDto в класс SupersprintEntity и там у него обновляются поля
     */
    SupersprintDto updateSupersprintFromController(SupersprintDto supersprintDto);

    /**
     * Метод ищет элементы по имени в заданном количестве, возвращает pageDto, в качестве параметра size
     * устанавливает общее количество эпиков удовлетворяющих условиям поиска
     */
    PageDto<SupersprintDto> findAllByName(String name, Pageable pageable);
}
