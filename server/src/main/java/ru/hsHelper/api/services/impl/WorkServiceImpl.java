package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.notifications.PushNotificationService;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.repositories.UserWorkRepository;
import ru.hsHelper.api.repositories.WorkRepository;
import ru.hsHelper.api.requests.create.WorkCreateRequest;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;
import ru.hsHelper.api.requests.update.WorkUpdateRequest;
import ru.hsHelper.api.services.WorkService;
import ru.hsHelper.api.services.impl.util.UserWorkService;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final CoursePartRepository coursePartRepository;
    private final UserWorkRepository userWorkRepository;
    private final UserRepository userRepository;
    private final UserWorkService userWorkService;
    private final PushNotificationService pushNotificationService;

    @Autowired
    public WorkServiceImpl(WorkRepository workRepository, CoursePartRepository coursePartRepository,
                           UserWorkRepository userWorkRepository, UserRepository userRepository,
                           UserWorkService userWorkService, PushNotificationService pushNotificationService) {
        this.workRepository = workRepository;
        this.coursePartRepository = coursePartRepository;
        this.userWorkRepository = userWorkRepository;
        this.userRepository = userRepository;
        this.userWorkService = userWorkService;
        this.pushNotificationService = pushNotificationService;
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
        pushNotificationService.newWork(work);
        return work;
    }

    @Transactional(readOnly = true)
    @Override
    public Work getWorkById(long workId) {
        return workRepository.findById(workId).orElseThrow(
            () -> new IllegalArgumentException("No work with such id")
        );
    }

    @Transactional
    @Override
    public Work updateWork(long workId, WorkUpdateRequest workUpdateRequest) {
        Work work = getWorkById(workId);
        pushNotificationService.modifyWork(work, workUpdateRequest);
        work.setBlock(workUpdateRequest.getBlock());
        work.setDeadline(workUpdateRequest.getDate());
        work.setDescription(workUpdateRequest.getDescription());
        work.setWeight(workUpdateRequest.getWeight());
        work.setName(workUpdateRequest.getName());
        return work;
    }

    @Transactional
    @Override
    public void deleteWork(long workId) {
        Work work = workRepository.findById(workId).orElseThrow(
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
            UserWork userWork =
                userWorkRepository.save(new UserWork(user, work, new Date(), solutions.get(user.getId())));
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
    public Set<Work> getAllWorks() {
        return workRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public UserWork getUserWork(long workId, long userId) {
        return userWorkService.getUserWork(userId, workId);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<UserWork> getAllUserWorks(long workId) {
        return userWorkRepository.findAllByWork(getWorkById(workId));
    }

    @Transactional
    @Override
    public UserWork updateUserWork(long workId, long userId, UserWorkUpdateRequest userWorkUpdateRequest) {
        return userWorkService.updateUserWork(userId, workId, userWorkUpdateRequest);
    }
}
