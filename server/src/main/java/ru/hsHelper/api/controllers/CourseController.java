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
import ru.hsHelper.api.entities.User;
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

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable long id, @RequestBody @Valid CourseUpdateRequest courseUpdateRequest) {
        return courseService.updateCourse(id, courseUpdateRequest);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseService.getCourseById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
    }

    @PutMapping("/{id}/users")
    public Course addUsers(@PathVariable long id, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return courseService.addUsers(id, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{id}/users")
    public Course deleteUsers(@PathVariable long id, @RequestBody Set<Long> userIds) {
        return courseService.deleteUsers(id, userIds);
    }

    @GetMapping("/")
    public Set<Course> getAll() {
        return courseService.getAll();
    }

    @GetMapping("/{courseId}/users/{userId}")
    public UserCourseRole getUser(@PathVariable long courseId, @PathVariable long userId) {
        return courseService.getUser(courseId, userId);
    }

    @GetMapping("/{courseId}/users")
    public Set<UserCourseRole> getAllUsers(@PathVariable long courseId) {
        return courseService.getAllUsers(courseId);
    }

    @GetMapping("/{courseId}/course-parts")
    public Set<CoursePart> getAllCourseParts(@PathVariable long courseId) {
        return courseService.getAllCourseParts(courseId);
    }
}
