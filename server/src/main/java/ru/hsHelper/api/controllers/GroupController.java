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
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.requests.add.ObjectsWithRoleAddRequest;
import ru.hsHelper.api.requests.create.GroupCreateRequest;
import ru.hsHelper.api.requests.update.GroupUpdateRequest;
import ru.hsHelper.api.services.GroupService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/")
    public Group createGroup(@RequestBody @Valid GroupCreateRequest groupCreateRequest) {
        return groupService.createGroup(new Group(groupCreateRequest.getName()));
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable long id) {
        return groupService.getGroupById(id);
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable long id, @RequestBody @Valid GroupUpdateRequest groupUpdateRequest) {
        return groupService.updateGroup(id, groupUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable long id) {
        groupService.deleteGroup(id);
    }

    @PutMapping("/{id}/users")
    public Group addUsers(@PathVariable long id, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return groupService.addUsers(id, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{id}/users")
    public Group deleteUsers(@PathVariable long id, @RequestBody Set<Long> userIds) {
        return groupService.deleteUsers(id, userIds);
    }

    @GetMapping("/")
    public Set<Group> getAll() {
        return groupService.getAll();
    }

    @GetMapping("/{groupId}/users/{userId}")
    public UserGroupRole getUser(@PathVariable long groupId, @PathVariable long userId) {
        return groupService.getUser(groupId, userId);
    }

    @GetMapping("/{groupId}/users")
    public Set<UserGroupRole> getAllUsers(@PathVariable long groupId) {
        return groupService.getAllUsers(groupId);
    }

    @GetMapping("/{groupId}/courses")
    public Set<Course> getAllCourses(@PathVariable long groupId) {
        return groupService.getAllCourses(groupId);
    }

    @GetMapping("/{groupId}/partitions")
    public Set<Partition> getAllPartitions(@PathVariable long groupId) {
        return groupService.getAllPartitions(groupId);
    }
}
