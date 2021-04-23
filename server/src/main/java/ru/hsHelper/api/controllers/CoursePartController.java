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

    @PutMapping("/{id}")
    public CoursePart updateCoursePart(@PathVariable long id, @RequestBody @Valid CoursePartUpdateRequest coursePartUpdateRequest) {
        return coursePartService.updateCoursePart(id, coursePartUpdateRequest);
    }

    @GetMapping("/{id}")
    public CoursePart getCoursePart(@PathVariable long id) {
        return coursePartService.getCoursePartById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCoursePart(@PathVariable long id) {
        coursePartService.deleteCoursePart(id);
    }

    @PutMapping("/{id}/users")
    public CoursePart addUsers(@PathVariable long id, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return coursePartService.addUsers(id, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @DeleteMapping("/{id}/users")
    public CoursePart deleteUsers(@PathVariable long id, @RequestBody Set<Long> userIds) {
        return coursePartService.deleteUsers(id, userIds);
    }
}
