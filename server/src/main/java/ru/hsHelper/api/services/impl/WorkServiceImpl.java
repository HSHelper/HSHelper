package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.repositories.UserWorkRepository;
import ru.hsHelper.api.repositories.WorkRepository;
import ru.hsHelper.api.requests.create.WorkCreateRequest;
import ru.hsHelper.api.requests.update.WorkUpdateRequest;
import ru.hsHelper.api.services.WorkService;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final CoursePartRepository coursePartRepository;
    private final UserWorkRepository userWorkRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkServiceImpl(WorkRepository workRepository, CoursePartRepository coursePartRepository,
                           UserWorkRepository userWorkRepository, UserRepository userRepository) {
        this.workRepository = workRepository;
        this.coursePartRepository = coursePartRepository;
        this.userWorkRepository = userWorkRepository;
        this.userRepository = userRepository;
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
        work.setBlock(workUpdateRequest.getBlock());
        work.setDeadline(workUpdateRequest.getDate());
        work.setDescription(workUpdateRequest.getDescription());
        work.setWeight(workUpdateRequest.getWeight());
        work.setName(workUpdateRequest.getName());
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
                    solutions.get(user.getId()), 0));
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
}
