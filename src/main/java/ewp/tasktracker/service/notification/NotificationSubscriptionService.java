package ewp.tasktracker.service.notification;

import ewp.tasktracker.api.dto.notification.CreateNotificationSubscriptionRq;
import ewp.tasktracker.api.dto.notification.NotificationSubscriptionDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.response.ResponseDto;
import org.springframework.data.domain.Pageable;

/**
 * Сервис для работы с подписчиками на уведомления
 */
public interface NotificationSubscriptionService {


    ResponseDto subscribeOnNotifications(CreateNotificationSubscriptionRq dto);

    ResponseDto unsubscribeOnNotifications(NotificationSubscriptionDto dto);

    PageDto<NotificationSubscriptionDto> findAllByPageRequest(Pageable pageable);
}
