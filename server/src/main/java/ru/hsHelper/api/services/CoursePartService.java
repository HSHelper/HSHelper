package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.requests.create.CoursePartCreateRequest;
import ru.hsHelper.api.requests.update.CoursePartUpdateRequest;

import java.util.Set;
import java.util.Map;

public interface CoursePartService {
    CoursePart createCoursePart(CoursePartCreateRequest coursePartCreateRequest);
    CoursePart getCoursePartById(long id);
    CoursePart updateCoursePart(long id, CoursePartUpdateRequest coursePartUpdateRequest);
    void deleteCoursePart(long id);
    void preDeleteCoursePart(CoursePart coursePart);
    CoursePart addUsers(long coursePartId, Set<Long> userIds, Map<Long, Set<Long>> roleIds);
    CoursePart deleteUsers(long coursePartId, Set<Long> userIds);
}
