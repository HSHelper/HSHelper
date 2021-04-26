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
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.requests.add.ObjectsWithRoleAddRequest;
import ru.hsHelper.api.requests.add.ObjectsWithSolutionsAddRequest;
import ru.hsHelper.api.requests.add.PartitionAddRequest;
import ru.hsHelper.api.requests.create.UserCreateRequest;
import ru.hsHelper.api.requests.update.UserUpdateRequest;
import ru.hsHelper.api.services.UserService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public User createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return userService.createUser(new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName(),
                userCreateRequest.getEmail()));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(id, userUpdateRequest);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/groups")
    public User addGroups(@PathVariable long id, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return userService.addGroups(id, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @DeleteMapping("/{id}/groups")
    public User deleteGroups(@PathVariable long id, @RequestBody Set<Long> groupIds) {
        return userService.deleteGroups(id, groupIds);
    }

    @PutMapping("/{id}/partitions")
    public User addToPartitions(@PathVariable long id, @RequestBody @Valid PartitionAddRequest partitionAddRequest) {
        return userService.addToPartitions(id, partitionAddRequest.getPartitionIds(), partitionAddRequest.getUserParts());
    }

    @DeleteMapping("/{id}/partitions")
    public User deletePartitions(@PathVariable long id, @RequestBody Set<Long> partitionIds) {
        return userService.deletePartitions(id, partitionIds);
    }

    @PutMapping("/{id}/courses")
    public User addCourses(@PathVariable long id, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return userService.addCourses(id, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @DeleteMapping("/{id}/courses")
    public User deleteCourses(@PathVariable long id, @RequestBody Set<Long> courseIds) {
        return userService.deleteCourses(id, courseIds);
    }

    @PutMapping("/{id}/course-parts")
    public User addCourseParts(@PathVariable long id, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return userService.addCourseParts(id, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @DeleteMapping("/{id}/course-parts")
    public User deleteCourseParts(@PathVariable long id, @RequestBody Set<Long> coursePartIds) {
        return userService.deleteCourseParts(id, coursePartIds);
    }

    @PutMapping("/{id}/works")
    public User addWorks(@PathVariable long id,
                         @RequestBody @Valid ObjectsWithSolutionsAddRequest objectsWithSolutionsAddRequest) {
        return userService.addWorks(id, objectsWithSolutionsAddRequest.getObjectsIds(),
                objectsWithSolutionsAddRequest.getSolutions());
    }

    @DeleteMapping("/{id}/works")
    public User deleteWorks(@PathVariable long id, @RequestBody Set<Long> groupIds) {
        return userService.deleteWorks(id, groupIds);
    }

    @GetMapping("/")
    public Set<User> getAll() {
        return userService.getAll();
    }
}
