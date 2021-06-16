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
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.requests.add.ObjectsWithRoleAddRequest;
import ru.hsHelper.api.requests.create.CoursePartCreateRequest;
import ru.hsHelper.api.requests.update.CoursePartUpdateRequest;
import ru.hsHelper.api.services.CoursePartService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/course-parts")
public class CoursePartController {
    private final CoursePartService coursePartService;

    @Autowired
    public CoursePartController(CoursePartService coursePartService) {
        this.coursePartService = coursePartService;
    }

    @PostMapping("/")
    public CoursePart createCoursePart(@RequestBody @Valid CoursePartCreateRequest coursePartCreateRequest) {
        return coursePartService.createCoursePart(coursePartCreateRequest);
    }

    @PutMapping("/{coursePartId}")
    public CoursePart updateCoursePart(@PathVariable long coursePartId, @RequestBody @Valid CoursePartUpdateRequest coursePartUpdateRequest) {
        return coursePartService.updateCoursePart(coursePartId, coursePartUpdateRequest);
    }

    @GetMapping("/{coursePartId}")
    public CoursePart getCoursePart(@PathVariable long coursePartId) {
        return coursePartService.getCoursePartById(coursePartId);
    }

    @DeleteMapping("/{coursePartId}")
    public void deleteCoursePart(@PathVariable long coursePartId) {
        coursePartService.deleteCoursePart(coursePartId);
    }

    @PutMapping("/{coursePartId}/users")
    public CoursePart addUsers(@PathVariable long coursePartId, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return coursePartService.addUsers(coursePartId, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{coursePartId}/users")
    public CoursePart deleteUsers(@PathVariable long coursePartId, @RequestBody Set<Long> userIds) {
        return coursePartService.deleteUsers(coursePartId, userIds);
    }

    @GetMapping("/")
    public Set<CoursePart> getAllCourseParts() {
        return coursePartService.getAllCourseParts();
    }

    @GetMapping("/{coursePartId}/users/{userId}")
    public UserCoursePartRole getUserCoursePartRole(@PathVariable long coursePartId, @PathVariable long userId) {
        return coursePartService.getUserCoursePartRole(coursePartId, userId);
    }

    @GetMapping("/{coursePartId}/users")
    public Set<UserCoursePartRole> getAllUserCoursePartRoles(@PathVariable long coursePartId) {
        return coursePartService.getAllUserCoursePartRoles(coursePartId);
    }

    @GetMapping("/{coursePartId}/works")
    public Set<Work> getAllWorks(@PathVariable long coursePartId) {
        return coursePartService.getAllWorks(coursePartId);
    }
}
