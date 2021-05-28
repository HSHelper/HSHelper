package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.configs.PushNotifyConf;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.UserCoursePartRoleRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.repositories.UserWorkRepository;
import ru.hsHelper.api.repositories.WorkRepository;
import ru.hsHelper.api.requests.create.WorkCreateRequest;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;
import ru.hsHelper.api.requests.update.WorkUpdateRequest;
import ru.hsHelper.api.services.NotificationService;
import ru.hsHelper.api.services.RoleService;
import ru.hsHelper.api.services.WorkService;
import ru.hsHelper.api.services.impl.util.UserWorkService;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final CoursePartRepository coursePartRepository;
    private final UserWorkRepository userWorkRepository;
    private final UserRepository userRepository;
    private final UserWorkService userWorkService;
    private final RoleService roleService;
    private final PushNotificationService pushNotificationService;
    private final NotificationService notificationService;
    private final UserCoursePartRoleRepository userCoursePartRoleRepository;

    @Autowired
    public WorkServiceImpl(WorkRepository workRepository, CoursePartRepository coursePartRepository,
                           UserWorkRepository userWorkRepository, UserRepository userRepository,
                           UserWorkService userWorkService,
                           RoleService roleService, PushNotificationService pushNotificationService,
                           NotificationService notificationService,
                           UserCoursePartRoleRepository userCoursePartRoleRepository) {
        this.workRepository = workRepository;
        this.coursePartRepository = coursePartRepository;
        this.userWorkRepository = userWorkRepository;
        this.userRepository = userRepository;
        this.userWorkService = userWorkService;
        this.roleService = roleService;
        this.pushNotificationService = pushNotificationService;
        this.notificationService = notificationService;
        this.userCoursePartRoleRepository = userCoursePartRoleRepository;
    }

    @Transactional
    @Override
    public Work createWork(WorkCreateRequest workCreateRequest) {
        CoursePart coursePart = coursePartRepository.findById(workCreateRequest.getCoursePartId()).orElseThrow(
                () -> new IllegalArgumentException("No coursePart with such id")
        );
        Work work = workRepository.save(new Work(workCreateRequest.getName(), workCreateRequest.getDescription(),
                workCreateRequest.getDate(), workCreateRequest.getWeight(), workCreateRequest.getBlock(),
                workCreateRequest.getWorkType(), coursePart));
        coursePart.addWork(work);
        Set<User> users = userCoursePartRoleRepository.findAllByCoursePart(work.getCoursePart()).stream().filter(
                u -> u.getRoles().contains(roleService.getRoleByRoleType(Role.RoleType.STUDENT))).
                map(UserCoursePartRole::getUser).collect(Collectors.toSet());
        pushNotificationService.processNotifications(new PushNotifyConf("New work",
                "Created new Work: " + work.getName(), Map.of("workId", Long.toString(work.getId()))), users,
                notificationService.getNotificationByNotificationType(Notification.NotificationType.WORKUPDATE));
        return work;
    }

    @Transactional(readOnly = true)
    @Override
    public Work getWorkById(long id) {
        return workRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No work with such id")
        );
    }

    @Transactional
    @Override
    public Work updateWork(long id, WorkUpdateRequest workUpdateRequest) {
        Work work = getWorkById(id);
        String body = "Work " + work.getName() + " updated: ";
        if (work.getBlock() != workUpdateRequest.getBlock()) {
            body += "block; ";
        }
        work.setBlock(workUpdateRequest.getBlock());
        if (!work.getDeadline().equals(workUpdateRequest.getDate())) {
            body += "deadline; ";
        }
        work.setDeadline(workUpdateRequest.getDate());
        if (!work.getDescription().equals(workUpdateRequest.getDescription())) {
            body += "description; ";
        }
        work.setDescription(workUpdateRequest.getDescription());
        if (work.getWeight() != workUpdateRequest.getWeight()) {
            body += "weight; ";
        }
        work.setWeight(workUpdateRequest.getWeight());
        if (!work.getName().equals(workUpdateRequest.getName())) {
            body += "name; ";
        }
        work.setName(workUpdateRequest.getName());
        Set<User> users = userCoursePartRoleRepository.findAllByCoursePart(work.getCoursePart()).stream().filter(
                u -> u.getRoles().contains(roleService.getRoleByRoleType(Role.RoleType.STUDENT))).
                map(UserCoursePartRole::getUser).collect(Collectors.toSet());
        pushNotificationService.processNotifications(new PushNotifyConf("Updated work",
                        body, Map.of("workId", Long.toString(work.getId()))), users,
                notificationService.getNotificationByNotificationType(Notification.NotificationType.WORKUPDATE));
        return work;
    }

    @Transactional
    @Override
    public void deleteWork(long id) {
        Work work = workRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No work with such id")
        );
        preDeleteWork(work);
        workRepository.delete(work);
    }

    @Transactional
    @Override
    public void preDeleteWork(Work work) {
        work.getCoursePart().removeWork(work);
        Set<UserWork> userWorks = userWorkRepository.findAllByWork(work);
        for (UserWork userWork : userWorks) {
            userWork.removeUserAndWork();
        }
        userWorkRepository.deleteAll(userWorks);
    }

    @Transactional
    @Override
    public Work addUsers(long workId, Set<Long> userIds, Map<Long, String> solutions) {
        Work work = getWorkById(workId);
        Set<User> users = userRepository.findAllByIdIn(userIds);
        for (User user : users) {
            UserWork userWork = userWorkRepository.save(new UserWork(user, work, new Date(),
                    solutions.get(user.getId())));
            user.addWork(userWork);
            work.addUser(userWork);
        }
        return work;
    }

    @Transactional
    @Override
    public Work deleteUsers(long workId, Set<Long> userIds) {
        Work work = getWorkById(workId);
        Set<User> users = userRepository.findAllByIdIn(userIds);
        Set<UserWork> userWorks = userWorkRepository.findAllByWorkAndUserIn(work, users);
        for (UserWork userWork : userWorks) {
            userWork.removeUserAndWork();
        }
        userWorkRepository.deleteAll(userWorks);
        return work;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Work> getAll() {
        return workRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public UserWork getUser(long workId, long userId) {
        return userWorkService.getUserWork(userId, workId);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<UserWork> getAllUsers(long workId) {
        return userWorkRepository.findAllByWork(getWorkById(workId));
    }

    @Transactional
    @Override
    public UserWork updateUserWork(long workId, long userId, UserWorkUpdateRequest userWorkUpdateRequest) {
        return userWorkService.updateUserWork(userId, workId, userWorkUpdateRequest);
    }
}
