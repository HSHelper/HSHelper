package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.requests.create.CourseCreateRequest;
import ru.hsHelper.api.requests.update.CourseUpdateRequest;

import java.util.Map;
import java.util.Set;

public interface CourseService {
    Course createCourse(CourseCreateRequest courseCreateRequest);
    Course updateCourse(long id, CourseUpdateRequest courseUpdateRequest);
    Course getCourseById(long id);
    void deleteCourse(long id);
    void preDeleteCourse(Course course);
    Course addUsers(long courseId, Set<Long> userIds, Map<Long, Set<Long>> roleIds);
    Course deleteUsers(long courseId, Set<Long> userIds);
    Set<Course> getAll();
}
