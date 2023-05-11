package ewp.tasktracker.service.notification;

import ewp.tasktracker.api.dto.notification.CreateNotificationSubscriptionRq;
import ewp.tasktracker.api.dto.notification.NotificationSubscriptionDto;
import ewp.tasktracker.api.dto.response.ErrorDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.response.ResponseDto;
import ewp.tasktracker.api.dto.response.SuccessDto;
import ewp.tasktracker.entity.notification.NotificationSubscriptionEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.notification.NotificationSubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotificationSubscriptionServiceImpl implements NotificationSubscriptionService {

    private final NotificationSubscriptionRepository repository;


    public ResponseDto subscribeOnNotifications(CreateNotificationSubscriptionRq dto) {
        try {
            repository.save(new CreateNotificationSubscriptionRq(dto.getUserId(),
                    dto.getProjectId()).toEntity());
            return new SuccessDto(200, "Вы подписаны на уведомления!");
        } catch (ResourceNotFoundException e) {
            return new ErrorDto(404, " Не найден пользователь и/или проект. \n Подписаться не удалось!");
        }
    }

    public ResponseDto unsubscribeOnNotifications(NotificationSubscriptionDto dto) {
        Optional<NotificationSubscriptionEntity> entityOptional
                = repository.findByUserIdAndProjectId(dto.getUserId(), dto.getProjectId());
        if (entityOptional.isPresent()) {
            return new SuccessDto(200, "Вы успешно отписались от уведомлений");
        } else {
            return new ErrorDto(404, " Не найден пользователь и/или проект. \n Отписаться не удалось!");
        }
    }

    @Override
    public PageDto<NotificationSubscriptionDto> findAllByPageRequest(Pageable pageable) {
        Page<NotificationSubscriptionEntity> entities = repository.findAll(pageable);
        return new PageDto<>(entities.getContent().stream()
                .map(NotificationSubscriptionDto::from).collect(Collectors.toList()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) entities.getTotalElements());
    }

}
