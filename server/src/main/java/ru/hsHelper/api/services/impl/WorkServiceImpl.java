package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.WorkRepository;
import ru.hsHelper.api.requests.create.WorkCreateRequest;
import ru.hsHelper.api.services.WorkService;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final CoursePartRepository coursePartRepository;

    @Autowired
    public WorkServiceImpl(WorkRepository workRepository, CoursePartRepository coursePartRepository) {
        this.workRepository = workRepository;
        this.coursePartRepository = coursePartRepository;
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
}
