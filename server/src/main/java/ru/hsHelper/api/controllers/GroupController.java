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

    @GetMapping("/{groupId}")
    public Group getGroup(@PathVariable long groupId) {
        return groupService.getGroupById(groupId);
    }

    @PutMapping("/{groupId}")
    public Group updateGroup(@PathVariable long groupId, @RequestBody @Valid GroupUpdateRequest groupUpdateRequest) {
        return groupService.updateGroup(groupId, groupUpdateRequest);
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable long groupId) {
        groupService.deleteGroup(groupId);
    }

    @PutMapping("/{groupId}/users")
    public Group addUsers(@PathVariable long groupId, @RequestBody @Valid ObjectsWithRoleAddRequest objectsWithRoleAddRequest) {
        return groupService.addUsers(groupId, objectsWithRoleAddRequest.getObjectIds(), objectsWithRoleAddRequest.getRoleIds());
    }

    @PostMapping("/{groupId}/users")
    public Group deleteUsers(@PathVariable long groupId, @RequestBody Set<Long> userIds) {
        return groupService.deleteUsers(groupId, userIds);
    }

    @GetMapping("/")
    public Set<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{groupId}/users/{userId}")
    public UserGroupRole getUserGroupRole(@PathVariable long groupId, @PathVariable long userId) {
        return groupService.getUserGroupRole(groupId, userId);
    }

    @GetMapping("/{groupId}/users")
    public Set<UserGroupRole> getAllUserGroupRoles(@PathVariable long groupId) {
        return groupService.getAllUserGroupRoles(groupId);
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
