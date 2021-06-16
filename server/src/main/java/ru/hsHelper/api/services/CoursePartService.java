package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.requests.create.CoursePartCreateRequest;
import ru.hsHelper.api.requests.update.CoursePartUpdateRequest;

import java.util.Set;
import java.util.Map;

public interface CoursePartService {
    CoursePart createCoursePart(CoursePartCreateRequest coursePartCreateRequest);
    CoursePart getCoursePartById(long coursePartId);
    CoursePart updateCoursePart(long coursePartId, CoursePartUpdateRequest coursePartUpdateRequest);
    void deleteCoursePart(long coursePartId);
    void preDeleteCoursePart(CoursePart coursePart);
    CoursePart addUsers(long coursePartId, Set<Long> userIds, Map<Long, Set<Long>> roleIds);
    CoursePart deleteUsers(long coursePartId, Set<Long> userIds);
    Set<CoursePart> getAllCourseParts();
    UserCoursePartRole getUserCoursePartRole(long coursePartId, long userId);
    Set<UserCoursePartRole> getAllUserCoursePartRoles(long coursePartId);
    Set<Work> getAllWorks(long coursePartId);
}