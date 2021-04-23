package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.UserWorkRepository;
import ru.hsHelper.api.repositories.WorkRepository;
import ru.hsHelper.api.requests.create.WorkCreateRequest;
import ru.hsHelper.api.services.WorkService;

import java.util.Set;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final CoursePartRepository coursePartRepository;
    private final UserWorkRepository userWorkRepository;

    @Autowired
    public WorkServiceImpl(WorkRepository workRepository, CoursePartRepository coursePartRepository,
                           UserWorkRepository userWorkRepository) {
        this.workRepository = workRepository;
        this.coursePartRepository = coursePartRepository;
        this.userWorkRepository = userWorkRepository;
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

    @Transactional
    @Override
    public Work getWorkById(long id) {
        return workRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No work with such id")
        );
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
}
