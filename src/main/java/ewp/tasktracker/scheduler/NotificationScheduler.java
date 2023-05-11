package ewp.tasktracker.scheduler;

import ewp.tasktracker.api.dto.bug.BugDto;
import ewp.tasktracker.api.dto.notification.NotificationSubscriptionDto;
import ewp.tasktracker.api.dto.scheduler.SchedulerInfoDto;
import ewp.tasktracker.api.dto.task.TaskDto;
import ewp.tasktracker.config.TaskTrackerProperties;
import ewp.tasktracker.service.bug.BugService;
import ewp.tasktracker.service.notification.NotificationSubscriptionService;
import ewp.tasktracker.service.scheduler.SchedulerInfoService;
import ewp.tasktracker.service.task.TaskService;
import ewp.tasktracker.service.kafka.Notification;
import ewp.tasktracker.service.kafka.NotificationKafkaProducerService;
import ewp.tasktracker.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class NotificationScheduler {
    private final TaskService taskService;
    private final BugService bugService;
    private final SchedulerInfoService schedulerService;
    private final TaskTrackerProperties props;
    private final NotificationKafkaProducerService producerService;
    private final NotificationSubscriptionService subscriptionService;
    private final PageUtil pageUtil;
    private static Long SCHEDULER_ID = 1L;

    @Scheduled(fixedDelayString = "${task-tracker.notification-period}")
    public void sendNotifications() {
        log.info("Running notification scheduler");
        LocalDateTime timeNow = LocalDateTime.now();
        int amountNotifications = 0;
        LocalDateTime scheduledTime = timeNow.minus(props.getNotificationPeriod());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedScheduledTime = scheduledTime.format(formatter);

        // По каждой задаче/багу формируется объект уведомление (Notification) c сообщением: "Новая задача/баг: название задачи/бага"
        // Получаем новые задачи и баги, созданные позднее указанного периода времени
        Pageable pageable = pageUtil.buildPageable(props.getPageDefaultSize(), 0);
        List<NotificationSubscriptionDto> subscriptions = subscriptionService.findAllByPageRequest(pageable)
                .getItems();

        for (NotificationSubscriptionDto s : subscriptions) {
            List<TaskDto> tasks = taskService.getTaskEntitiesByProjectId(s.getProjectId(), scheduledTime, pageable)
                    .getItems();

            // Отправляем уведомление(notification) о новых задачах в Кафку
            for (TaskDto task : tasks) {
                producerService.send(new Notification(getNotificationMessage(task), s.getUserId()));
                log.info("Новая задача: {}, для проекта с id: {}, время создания, не ранее: {}",
                        task.getName(), s.getProjectId(), formattedScheduledTime);
                amountNotifications++;
            }

            List<BugDto> bugs = bugService.findAllByProjectIdAndDate(s.getProjectId(), scheduledTime, pageable)
                    .getItems();

            // Отправляем уведомление(notification) о новых багах в Кафку
            for (BugDto bug : bugs) {
                producerService.send(new Notification(getNotificationMessage(bug), s.getUserId()));
                log.info("Новый баг: {}, для проекта с id: {}, время создания, не ранее: {}",
                        bug.getName(), s.getProjectId(), formattedScheduledTime);
                amountNotifications++;
            }
        }

        log.info("Sent {} notifications", amountNotifications);

        // Записываем в БД
        if (amountNotifications > 0) {
            schedulerService.save(new SchedulerInfoDto(SCHEDULER_ID++, timeNow));
        }
    }

    private String getNotificationMessage(TaskDto task) {
        return "Новая задача: " + task.getName();
    }

    private String getNotificationMessage(BugDto bug) {
        return "Новый баг: " + bug.getName();
    }
}
