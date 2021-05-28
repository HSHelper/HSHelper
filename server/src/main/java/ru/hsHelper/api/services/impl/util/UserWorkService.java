package ru.hsHelper.api.services.impl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.configs.PushNotifyConf;
import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.keys.UserWorkKey;
import ru.hsHelper.api.repositories.UserCoursePartRoleRepository;
import ru.hsHelper.api.repositories.UserWorkRepository;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;
import ru.hsHelper.api.services.NotificationService;
import ru.hsHelper.api.services.RoleService;
import ru.hsHelper.api.services.impl.PushNotificationService;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;

@Service
public class UserWorkService {
    private final UserWorkRepository userWorkRepository;
    private final PushNotificationService pushNotificationService;
    private final RoleService roleService;
    private final NotificationService notificationService;
    private final UserCoursePartRoleRepository userCoursePartRoleRepository;


    @Autowired
    public UserWorkService(UserWorkRepository userWorkRepository, PushNotificationService pushNotificationService,
                           RoleService roleService,
                           NotificationService notificationService,
                           UserCoursePartRoleRepository userCoursePartRoleRepository) {
        this.userWorkRepository = userWorkRepository;
        this.pushNotificationService = pushNotificationService;
        this.roleService = roleService;
        this.notificationService = notificationService;
        this.userCoursePartRoleRepository = userCoursePartRoleRepository;
    }

    @Transactional(readOnly = true)
    public UserWork getUserWork(long userId, long workId) {
        return userWorkRepository.findById(new UserWorkKey(userId, workId)).orElseThrow(
                () -> new IllegalArgumentException("There is not such user with this work")
        );
    }

    @Transactional
    public UserWork updateUserWork(long userId, long workId, UserWorkUpdateRequest userWorkUpdateRequest) {
        UserWork userWork = getUserWork(userId, workId);
        String body = "UserWork " + userWork.getWork().getName() + " updated: ";
        if (!userWork.getSolution().equals(userWorkUpdateRequest.getSolution())) {
            body += "solution; ";
        }
        userWork.setSolution(userWorkUpdateRequest.getSolution());
        if (userWork.getMark() != userWorkUpdateRequest.getMark()) {
            body += "mark; ";
        }
        userWork.setMark(userWorkUpdateRequest.getMark());
        userWork.setSendTime(new Date());
        Set<User> users = userCoursePartRoleRepository.findAllByCoursePart(userWork.getWork().getCoursePart()).stream().
                filter(u -> u.getRoles().contains(roleService.getRoleByRoleType(Role.RoleType.TEACHER))).
                map(UserCoursePartRole::getUser).collect(Collectors.toSet());
        users.add(userWork.getUser());
        pushNotificationService.processNotifications(new PushNotifyConf("Updated UserWork", body,
                Map.of("userId", Long.toString(userWork.getId().getUserId()),
                        "workId", Long.toString(userWork.getId().getWorkId()))), users,
                notificationService.getNotificationByNotificationType(Notification.NotificationType.USERWORKUPDATE));
        return userWork;
    }
}
