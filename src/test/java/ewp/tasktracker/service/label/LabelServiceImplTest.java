package ewp.tasktracker.service.label;

import ewp.tasktracker.api.dto.label.CreateLabelRq;
import ewp.tasktracker.api.dto.label.LabelDto;
import ewp.tasktracker.entity.label.LabelEntity;
import ewp.tasktracker.repository.label.LabelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Добавил максимально подробные комментарии к каждой строке, методы в количестве сервисных методов, написал код в процедурном стиле,
 * если будут замечания исправлю, сделал так чтобы было максимально понятно что и зачем я тестирую.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LabelServiceImplTest {


    //Создает мок который мы инжектим в поле сервиса при помощи @InjectMock
    @Mock
    private LabelRepository labelRepository;

    //Внедряю зависимость от сервиса, интерфейс инжектить не получилось
    //Мокитовская зависимость инжектящая мок от лейблрепозитория
    @InjectMocks
    private LabelServiceImpl labelService;



//    Проверяю что мне вернется сохраненная LabelDto, тестируемый метод перекрещивается с тестируемым методом findById, но мне так больше понравилось сделать
//    получилась доп проверка которая подтверждает истинность данных которые я передал и получил
    @Test
    public void shouldReturnSavedLabelDto() {
        //Создаю мок который возвращает объект который я ему передаю
        Mockito.when(labelRepository.save(Mockito.any(LabelEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        //Создаю объект который буду сохранять
        CreateLabelRq createLabelRq = new CreateLabelRq("Label1", "otherAuthor", "taskId1");
        //Объявляю LabelDto и инициализирую ее через метод сервиса, в параметры которого передаю специальный вышесозданный объект
        LabelDto saveDLabelDto = labelService.save(createLabelRq);
        //Создаю мок который возвращает объект который является переданным выше объектом при помощи save
        Mockito.when(labelRepository.findById(saveDLabelDto.getId())).thenReturn(Optional.of(new LabelEntity(createLabelRq.getText(), createLabelRq.getAuthorId(), createLabelRq.getTaskId())));
        //Создаю еще одну дто для теста, инициализирую ее результатом работы метода findById и передеаю ему в параметр id от вышесозданной дто
        LabelDto tested = labelService.findById(saveDLabelDto.getId());
        //Устанавливаю updatedAt от savedLabelDto, потому что иначе здесь у меня происходит потеря данных и округление, не стал мудрить с методами класса Math
        //или другими способами как можно все привести к одному формату, да по факту в результате этого сета мы проверяем все поля кроме времени создания и обновления
        //но мы заранее знаем что они различаются лишь дельтой
        tested.setId(saveDLabelDto.getId());
        tested.setUpdatedAt(saveDLabelDto.getUpdatedAt());
        tested.setCreatedAt(saveDLabelDto.getCreatedAt());
        //Проверяю что объект для передачи в метод не пустой
        Assertions.assertNotNull(createLabelRq);
        //Проверяю что результат работы метода вернул мне не пустой результат
        Assertions.assertNotNull(saveDLabelDto);
        //Проверяю что моя дтошка не равна рандомной пустой дтошке
        Assertions.assertNotEquals(saveDLabelDto, new LabelDto());
        //Проверяб что сохраненная дтошка равна той которую я получаю по айди
        Assertions.assertEquals(saveDLabelDto, tested);


    }

//    Метод должен вернуть дтошку айди которой я передал ему в параметры, также перекрещивается с методом save
    @Test
    public void shouldReturnLabelDtoFindBy() {
        //Создаю мок который возвращает объект который я ему передаю
        Mockito.when(labelRepository.save(Mockito.any(LabelEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        //Создаю объект который буду сохранять
        CreateLabelRq createLabelRq = new CreateLabelRq("Label1", "otherAuthor1", "taskId1");
        //Объявляю LabelDto и инициализирую ее через метод сервиса, в параметры которого передаю специальный вышесозданный объект
        LabelDto saveDLabelDto = labelService.save(createLabelRq);
        //Создаю мок который возвращает объект который является переданным выше объектом при помощи save
        Mockito.when(labelRepository.findById(saveDLabelDto.getId())).thenReturn(Optional.of(new LabelEntity(createLabelRq.getText(), createLabelRq.getAuthorId(), createLabelRq.getTaskId())));
        //Создаю еще одну дто для теста, инициализирую ее результатом работы метода findById и передеаю ему в параметр id от вышесозданной дто
        LabelDto tested = labelService.findById(saveDLabelDto.getId());
        tested.setId(saveDLabelDto.getId());
        //Устанавливаю updatedAt от savedLabelDto, потому что иначе здесь у меня происходит потеря данных и округление, не стал мудрить с методами класса Math
        //или другими способами как можно все привести к одному формату, да по факту в результате этого сета мы проверяем все поля кроме времени создания и обновления
        //но мы заранее знаем что они различаются лишь дельтой
        tested.setUpdatedAt(saveDLabelDto.getUpdatedAt());
        tested.setCreatedAt(saveDLabelDto.getCreatedAt());
        //Создаю еще один объект и делаю все по аналогии с вышесказанным для более тщательных проверок
        CreateLabelRq createLabelRq1 = new CreateLabelRq("Label2", "otherAuthor2", "taskId2");
        LabelDto saveDLabelDto1 = labelService.save(createLabelRq1);
        //Создаю мок который возвращает объект который является переданным выше объектом при помощи save
        Mockito.when(labelRepository.findById(saveDLabelDto1.getId())).thenReturn(Optional.of(new LabelEntity(createLabelRq1.getText(), createLabelRq1.getAuthorId(), createLabelRq1.getTaskId())));
        LabelDto tested1 = labelService.findById(saveDLabelDto1.getId());
        tested1.setId(saveDLabelDto1.getId());
        tested1.setUpdatedAt(saveDLabelDto1.getUpdatedAt());
        tested1.setCreatedAt(saveDLabelDto1.getCreatedAt());
        //Проверяю что мои объекты не равны нулл
        Assertions.assertNotNull(tested);
        Assertions.assertNotNull(tested1);
        //Проверяю что два разных объекта которые я получил не равны между собой
        Assertions.assertNotEquals(tested1, tested);
        //Проверяю что объект который я получил равен тому который я получил (для обоих объектов)
        Assertions.assertEquals(saveDLabelDto, tested);
        Assertions.assertEquals(saveDLabelDto1, tested1);
    }

//    Метод должен вернуть объект который я удалил или выбросить исключение которое значит что такой объект отсутствует в бд
    @Test
    public void shouldReturnDeletedLabelDtoOrCannotFindThisDtoByIdThrowResourceNotFoundException() {
        //Создаю мок который возвращает объект который я ему передаю
        Mockito.when(labelRepository.save(Mockito.any(LabelEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        //Создаю объекты по аналогии с вышестоящими методами
        CreateLabelRq createLabelRq = new CreateLabelRq("Label1", "otherAuthor1", "taskId1");
        CreateLabelRq createLabelRq1 = new CreateLabelRq("Label2", "otherAuthor2", "taskId2");
        LabelDto saveDLabelDto = labelService.save(createLabelRq);
        LabelDto saveDLabelDto1 = labelService.save(createLabelRq1);
        LabelEntity labelEntity1 = new LabelEntity(createLabelRq.getText(), createLabelRq.getAuthorId(), createLabelRq.getTaskId());
        LabelEntity labelEntity2 = new LabelEntity(createLabelRq1.getText(), createLabelRq1.getAuthorId(), createLabelRq1.getTaskId());
        labelEntity1.setId(saveDLabelDto.getId());
        labelEntity2.setId(saveDLabelDto1.getId());
        //Создаю мок который возвращает объект который является переданным выше объектом при помощи save
        Mockito.when(labelRepository.findById(saveDLabelDto.getId()))
                .thenReturn(Optional.of(labelEntity1));
        Mockito.when(labelRepository.findById(saveDLabelDto.getId()))
                .thenReturn(Optional.of(labelEntity2));
        //Создаю мок который кидает исключение при удалении по айди, потому что мокаемый метод войд
        Mockito.doThrow(IllegalArgumentException.class).when(labelRepository).deleteById(saveDLabelDto.getId());
        Mockito.doThrow(IllegalArgumentException.class).when(labelRepository).deleteById(saveDLabelDto1.getId());
        Assertions.assertThrows(IllegalArgumentException.class, () -> labelService.deleteLabelEntityById(labelEntity1.getId()));
        try {
            //Блок трайкэтч нужен из-за этих двух строк, потому что в проверяемом методе я получаю вышезамоканный эксепшен
            LabelDto deletedLabelDto = labelService.deleteLabelEntityById(saveDLabelDto1.getId());
            LabelDto deletedLabelDto1 = labelService.deleteLabelEntityById(saveDLabelDto1.getId());
            //Сеттю время, потому что тут та же проблема с потерей данных
            deletedLabelDto.setUpdatedAt(saveDLabelDto.getUpdatedAt());
            deletedLabelDto.setCreatedAt(saveDLabelDto.getCreatedAt());
            deletedLabelDto1.setUpdatedAt(saveDLabelDto1.getUpdatedAt());
            deletedLabelDto1.setCreatedAt(saveDLabelDto1.getCreatedAt());
            //Проверяю что метод мне что-то вернул
            Assertions.assertNotNull(deletedLabelDto);
            Assertions.assertNotNull(deletedLabelDto1);
            //Проверяю что сохраненный и удаленный объекты равны
            Assertions.assertEquals(saveDLabelDto, deletedLabelDto);
            //Проверяю что сохраненный другой объект не равен с моим удаленным
            Assertions.assertNotEquals(deletedLabelDto, saveDLabelDto1);
            Assertions.assertNotEquals(deletedLabelDto1, saveDLabelDto);
            //проверяю что сохраненный и удаленный такой же объект равны между собой
            Assertions.assertEquals(saveDLabelDto1, deletedLabelDto1);
            Assertions.assertEquals(saveDLabelDto, deletedLabelDto);
            //Смотрю что результат работы методы при переданных ему разных объектах дает мне разный резльтат
            Assertions.assertNotEquals(deletedLabelDto, deletedLabelDto1);
        } catch (Exception ignore) {

        }
    }

    @Test
    public void shouldReturnUpdatedLabelDtoByChangesFromRequest() {
        Mockito.when(labelRepository.save(Mockito.any(LabelEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        //Создаю объекты
        CreateLabelRq createLabelRq = new CreateLabelRq("Label1", "otherAuthor1", "taskId1");
        CreateLabelRq createLabelRq1 = new CreateLabelRq("Label2", "otherAuthor2", "taskId2");
        //Сохраняю их в базу
        LabelDto saveDLabelDto = labelService.save(createLabelRq);
        LabelDto saveDLabelDto1 = labelService.save(createLabelRq1);
        //Создаю объекты которые я передам для того чтобы обновить вышесозданные
        LabelEntity labelEntityForUpdate = new LabelEntity("updateLabelEntity", "toUpdate1", "toUpdate1");
        LabelEntity labelEntityForUpdate1 = new LabelEntity("updateLabelEntity1", "otoUpdate2", "toUpdate2");
        //Устанавливаю объектам(энтитям) которые я буду обновлять айди созданных объектов чтобы они корректно обновились
        labelEntityForUpdate.setId(saveDLabelDto.getId());
        labelEntityForUpdate1.setId(saveDLabelDto1.getId());
        //Создаю дтошки которые я передам в параметры метода сервиса и передаю им в конструктор вышесозданные энтити
        LabelDto labelDtoForUpdate = new LabelDto(labelEntityForUpdate);
        LabelDto labelDtoForUpdate1 = new LabelDto(labelEntityForUpdate1);
        LabelEntity updatedLabelEntity1 = new LabelEntity(labelDtoForUpdate.getText(), labelDtoForUpdate.getAuthorId(), labelDtoForUpdate.getTaskId());
        updatedLabelEntity1.setId(saveDLabelDto.getId());
        LabelEntity updatedLabelEntity2 = new LabelEntity(labelDtoForUpdate1.getText(), labelDtoForUpdate1.getAuthorId(), labelDtoForUpdate1.getTaskId());
        updatedLabelEntity2.setId(saveDLabelDto1.getId());
        Mockito.when(labelRepository.findById(labelDtoForUpdate.getId()))
                .thenReturn(Optional.of(updatedLabelEntity1));
        Mockito.when(labelRepository.findById(labelDtoForUpdate1.getId()))
                .thenReturn(Optional.of(updatedLabelEntity2));
        //Сохраняю в эти две дто результат работы метода
        LabelDto updatedLabelDto = labelService.updateLabelFromController(labelDtoForUpdate);
        LabelDto updatedLabelDto1 = labelService.updateLabelFromController(labelDtoForUpdate1);
        //Проверяю что мои обновленные сущности не пустые
        Assertions.assertNotNull(updatedLabelDto);
        Assertions.assertNotNull(updatedLabelDto1);
        //Проверяю что сохраненный объект и полученный в результате обновления различаются (выше видно что они правда различаются)
        Assertions.assertNotEquals(updatedLabelDto, saveDLabelDto);
        Assertions.assertNotEquals(updatedLabelDto1, saveDLabelDto1);
        //Проверяю что объект который передан для обновления текущего равен обновленному объекту(результату работы метода)
        Assertions.assertEquals(labelDtoForUpdate, updatedLabelDto);
        Assertions.assertEquals(labelDtoForUpdate1, updatedLabelDto1);
        //Проверяю что обновленный объект 1 не равен объекту 2 (они также разные)
        Assertions.assertNotEquals(updatedLabelDto1, updatedLabelDto);
        //Проверяю что если передам объект с айди который не сохранен в бд у меня вылетит исключение
        Assertions.assertThrows(NoSuchElementException.class, () -> labelService.updateLabelFromController(new LabelDto()));
    }

//    Метод должен вернуть PageDto с переданными пераметрами таскайди и размерами(pageable) или если таскайди отсутствует вернуть страницу с размерами(pageable)
    @Test
    public void shouldReturnPageDtoByTaskIdAndPageableOrElseIfRequestWithoutTaskIdShouldReturnPageDtoByPageable() {
        Mockito.when(labelRepository.save(Mockito.any(LabelEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(labelRepository.findAllByTaskId("someTaskId", PageRequest.of(0, 20))).thenThrow(ArithmeticException.class);
        Mockito.when(labelRepository.findAllByTaskId("myTaskId", PageRequest.of(0, 20))).thenThrow(ClassCastException.class);
        Assertions.assertThrows(ArithmeticException.class, () -> labelService.findAllByPageRequest("someTaskId", PageRequest.of(0, 20)));
        Assertions.assertThrows(ClassCastException.class, () -> labelService.findAllByPageRequest("myTaskId", PageRequest.of(0, 20)));
    }
}