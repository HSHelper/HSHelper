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

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable long userId, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userId, userUpdateRequest);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}/groups")
    public User addGroups(@PathVariable long userId, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return userService.addGroups(userId, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{userId}/groups")
    public User deleteGroups(@PathVariable long userId, @RequestBody Set<Long> groupIds) {
        return userService.deleteGroups(userId, groupIds);
    }

    @PutMapping("/{userId}/partitions")
    public User addToPartitions(@PathVariable long userId, @RequestBody @Valid PartitionAddRequest partitionAddRequest) {
        return userService.addToPartitions(userId, partitionAddRequest.getPartitionIds(), partitionAddRequest.getUserParts());
    }

    @PostMapping("/{userId}/partitions")
    public User deletePartitions(@PathVariable long userId, @RequestBody Set<Long> partitionIds) {
        return userService.deletePartitions(userId, partitionIds);
    }

    @PutMapping("/{userId}/courses")
    public User addCourses(@PathVariable long userId, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return userService.addCourses(userId, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{userId}/courses")
    public User deleteCourses(@PathVariable long userId, @RequestBody Set<Long> courseIds) {
        return userService.deleteCourses(userId, courseIds);
    }

    @PutMapping("/{userId}/course-parts")
    public User addCourseParts(@PathVariable long userId, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return userService.addCourseParts(userId, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{userId}/course-parts")
    public User deleteCourseParts(@PathVariable long userId, @RequestBody Set<Long> coursePartIds) {
        return userService.deleteCourseParts(userId, coursePartIds);
    }

    @PutMapping("/{userId}/works")
    public User addWorks(@PathVariable long userId,
                         @RequestBody @Valid ObjectsWithSolutionsAddRequest objectsWithSolutionsAddRequest) {
        return userService.addWorks(userId, objectsWithSolutionsAddRequest.getObjectsIds(),
                objectsWithSolutionsAddRequest.getSolutions());
    }

    @PostMapping("/{userId}/works")
    public User deleteWorks(@PathVariable long userId, @RequestBody Set<Long> groupIds) {
        return userService.deleteWorks(userId, groupIds);
    }

    @GetMapping("/")
    public Set<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestParam @Email @Valid String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{userId}/groups/{groupId}")
    public UserGroupRole getUserGroupRole(@PathVariable long userId, @PathVariable long groupId) {
        return userService.getUserGroupRole(userId, groupId);
    }

    @GetMapping("/{userId}/groups")
    public Set<UserGroupRole> getAllUserGroupRoles(@PathVariable long userId) {
        return userService.getAllUserGroupRoles(userId);
    }

    @GetMapping("/{userId}/partitions/{partitionId}")
    public UserToPartition getUserToPartition(@PathVariable long userId, @PathVariable long partitionId) {
        return userService.getUserToPartition(userId, partitionId);
    }

    @GetMapping("/{userId}/partitions")
    public Set<UserToPartition> getAllUserToPartitions(@PathVariable long userId) {
        return userService.getAllUserToPartitions(userId);
    }

    @GetMapping("/{userId}/courses/{courseId}")
    public UserCourseRole getUserCourseRole(@PathVariable long userId, @PathVariable long courseId) {
        return userService.getUserCourseRole(userId, courseId);
    }

    @GetMapping("/{userId}/courses")
    public Set<UserCourseRole> getAllUserCourseRoles(@PathVariable long userId) {
        return userService.getAllUserCourseRoles(userId);
    }

    @GetMapping("/{userId}/course-parts/{coursePartId}")
    public UserCoursePartRole getUserCoursePartRole(@PathVariable long userId, @PathVariable long coursePartId) {
        return userService.getUserCoursePartRole(userId, coursePartId);
    }

    @GetMapping("/{userId}/course-parts")
    public Set<UserCoursePartRole> getAllUserCoursePartRoles(@PathVariable long userId) {
        return userService.getAllUserCoursePartRoles(userId);
    }

    @GetMapping("/{userId}/works/{workId}")
    public UserWork getUserWork(@PathVariable long userId, @PathVariable long workId) {
        return userService.getUserWork(userId, workId);
    }

    @GetMapping("/{userId}/works")
    public Set<UserWork> getAllUserWorks(@PathVariable long userId) {
        return userService.getAllUserWorks(userId);
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

    @GetMapping("/{userId}/notifications")
    public Set<Notification> getAllNotifications(@PathVariable long userId) {
        return userService.getAllNotifications(userId);
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
