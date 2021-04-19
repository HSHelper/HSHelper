package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.requests.create.CoursePartCreateRequest;

public interface CoursePartService {
    CoursePart createCoursePart(CoursePartCreateRequest coursePartCreateRequest);
    CoursePart getCoursePartById(long id);
}
