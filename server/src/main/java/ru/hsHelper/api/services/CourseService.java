package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.requests.create.CourseCreateRequest;

public interface CourseService {
    Course createCourse(CourseCreateRequest courseCreateRequest);
    Course getCourseById(long id);
}
