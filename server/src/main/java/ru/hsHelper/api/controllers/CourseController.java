package ru.hsHelper.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.requests.add.ObjectsWithRoleAddRequest;
import ru.hsHelper.api.requests.create.CourseCreateRequest;
import ru.hsHelper.api.requests.update.CourseUpdateRequest;
import ru.hsHelper.api.services.CourseService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/")
    public Course createCourse(@RequestBody @Valid CourseCreateRequest courseCreateRequest) {
        return courseService.createCourse(courseCreateRequest);
    }

    @PutMapping("/{courseId}")
    public Course updateCourse(@PathVariable long courseId, @RequestBody @Valid CourseUpdateRequest courseUpdateRequest) {
        return courseService.updateCourse(courseId, courseUpdateRequest);
    }

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable long courseId) {
        return courseService.getCourseById(courseId);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable long courseId) {
        courseService.deleteCourse(courseId);
    }

    @PutMapping("/{courseId}/users")
    public Course addUsers(@PathVariable long courseId, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return courseService.addUsers(courseId, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{courseId}/users")
    public Course deleteUsers(@PathVariable long courseId, @RequestBody Set<Long> userIds) {
        return courseService.deleteUsers(courseId, userIds);
    }

    @GetMapping("/")
    public Set<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}/users/{userId}")
    public UserCourseRole getUserCourseRole(@PathVariable long courseId, @PathVariable long userId) {
        return courseService.getUserCourseRole(courseId, userId);
    }

    @GetMapping("/{courseId}/users")
    public Set<UserCourseRole> getAllUserCourseRoles(@PathVariable long courseId) {
        return courseService.getAllUserCourseRoles(courseId);
    }

    @GetMapping("/{courseId}/course-parts")
    public Set<CoursePart> getAllCourseParts(@PathVariable long courseId) {
        return courseService.getAllCourseParts(courseId);
    }
}
