package ru.hsHelper.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.requests.add.ObjectsWithRoleAddRequest;
import ru.hsHelper.api.requests.add.ObjectsWithSolutionsAddRequest;
import ru.hsHelper.api.requests.add.PartitionAddRequest;
import ru.hsHelper.api.requests.create.UserCreateRequest;
import ru.hsHelper.api.requests.update.UserUpdateRequest;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;
import ru.hsHelper.api.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
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
                userCreateRequest.getEmail(), userCreateRequest.getToken()));
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

    @PostMapping("/{id}/groups")
    public User deleteGroups(@PathVariable long id, @RequestBody Set<Long> groupIds) {
        return userService.deleteGroups(id, groupIds);
    }

    @PutMapping("/{id}/partitions")
    public User addToPartitions(@PathVariable long id, @RequestBody @Valid PartitionAddRequest partitionAddRequest) {
        return userService.addToPartitions(id, partitionAddRequest.getPartitionIds(), partitionAddRequest.getUserParts());
    }

    @PostMapping("/{id}/partitions")
    public User deletePartitions(@PathVariable long id, @RequestBody Set<Long> partitionIds) {
        return userService.deletePartitions(id, partitionIds);
    }

    @PutMapping("/{id}/courses")
    public User addCourses(@PathVariable long id, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return userService.addCourses(id, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{id}/courses")
    public User deleteCourses(@PathVariable long id, @RequestBody Set<Long> courseIds) {
        return userService.deleteCourses(id, courseIds);
    }

    @PutMapping("/{id}/course-parts")
    public User addCourseParts(@PathVariable long id, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return userService.addCourseParts(id, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{id}/course-parts")
    public User deleteCourseParts(@PathVariable long id, @RequestBody Set<Long> coursePartIds) {
        return userService.deleteCourseParts(id, coursePartIds);
    }

    @PutMapping("/{id}/works")
    public User addWorks(@PathVariable long id,
                         @RequestBody @Valid ObjectsWithSolutionsAddRequest objectsWithSolutionsAddRequest) {
        return userService.addWorks(id, objectsWithSolutionsAddRequest.getObjectsIds(),
                objectsWithSolutionsAddRequest.getSolutions());
    }

    @PostMapping("/{id}/works")
    public User deleteWorks(@PathVariable long id, @RequestBody Set<Long> groupIds) {
        return userService.deleteWorks(id, groupIds);
    }

    @GetMapping("/")
    public Set<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestParam @Email @Valid String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{userId}/groups/{groupId}")
    public UserGroupRole getGroup(@PathVariable long userId, @PathVariable long groupId) {
        return userService.getGroup(userId, groupId);
    }

    @GetMapping("/{userId}/groups")
    public Set<UserGroupRole> getAllGroups(@PathVariable long userId) {
        return userService.getAllGroups(userId);
    }

    @GetMapping("/{userId}/partitions/{partitionId}")
    public UserToPartition getPartition(@PathVariable long userId, @PathVariable long partitionId) {
        return userService.getPartition(userId, partitionId);
    }

    @GetMapping("/{userId}/partitions")
    public Set<UserToPartition> getAllPartitions(@PathVariable long userId) {
        return userService.getAllPartitions(userId);
    }

    @GetMapping("/{userId}/courses/{courseId}")
    public UserCourseRole getCourse(@PathVariable long userId, @PathVariable long courseId) {
        return userService.getCourse(userId, courseId);
    }

    @GetMapping("/{userId}/courses")
    public Set<UserCourseRole> getAllCourses(@PathVariable long userId) {
        return userService.getAllCourses(userId);
    }

    @GetMapping("/{userId}/course-parts/{coursePartId}")
    public UserCoursePartRole getCoursePart(@PathVariable long userId, @PathVariable long coursePartId) {
        return userService.getCoursePart(userId, coursePartId);
    }

    @GetMapping("/{userId}/course-parts")
    public Set<UserCoursePartRole> getAllCourseParts(@PathVariable long userId) {
        return userService.getAllCourseParts(userId);
    }

    @GetMapping("/{userId}/works/{workId}")
    public UserWork getWork(@PathVariable long userId, @PathVariable long workId) {
        return userService.getWork(userId, workId);
    }

    @GetMapping("/{userId}/works")
    public Set<UserWork> getAllWorks(@PathVariable long userId) {
        return userService.getAllWorks(userId);
    }

    @PutMapping("/{userId}/works/{workId}")
    public UserWork updateUserWork(@PathVariable long userId, @PathVariable long workId,
                                   @RequestBody @Valid UserWorkUpdateRequest userWorkUpdateRequest) {
        return userService.updateUserWork(userId, workId, userWorkUpdateRequest);
    }

    @PutMapping("/{userId}/notifications")
    public User addNotifications(@PathVariable long userId, @RequestBody Set<Long> notificationIds) {
        return userService.addNotifications(userId, notificationIds);
    }

    @PostMapping("/{userId}/notifications")
    public User deleteNotifications(@PathVariable long userId, @RequestBody Set<Long> notificationIds) {
        return userService.deleteNotifications(userId, notificationIds);
    }

    @GetMapping("/{id}/notifications")
    public Set<Notification> getAllNotifications(@PathVariable long id) {
        return userService.getAllNotifications(id);
    }

    @GetMapping("/{userId}/groups/{groupId}/works")
    public Set<UserWork> getAllUserWorksByGroup(@PathVariable long userId, @PathVariable long groupId) {
        return userService.getAllUserWorksByGroup(userId, groupId);
    }

    @GetMapping("/{userId}/courses/{courseId}/works")
    public Set<UserWork> getAllUserWorksByCourse(@PathVariable long userId, @PathVariable long courseId) {
        return userService.getAllUserWorksByCourse(userId, courseId);
    }

    @GetMapping("/{userId}/course-parts/{coursePartId}/works")
    public Set<UserWork> getAllUserWorksByCoursePart(@PathVariable long userId, @PathVariable long coursePartId) {
        return userService.getAllUserWorksByCoursePart(userId, coursePartId);
    }
}
