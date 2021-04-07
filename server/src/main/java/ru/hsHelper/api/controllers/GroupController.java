package ru.hsHelper.api.controllers;

import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.repositories.CourseRepository;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.repositories.UserGroupRoleRepository;
import ru.hsHelper.api.requests.create.GroupCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupRepository groupRepository;
    private final UserGroupRoleRepository userGroupRoleRepository;
    private final PartitionRepository partitionRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository,
                           UserGroupRoleRepository userGroupRoleRepository,
                           PartitionRepository partitionRepository,
                           CourseRepository courseRepository) {
        this.groupRepository = groupRepository;
        this.userGroupRoleRepository = userGroupRoleRepository;
        this.partitionRepository = partitionRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping ({"", "/"})
    public Group createGroup(@RequestBody @Valid GroupCreateRequest request) {
        return groupRepository.save(new Group(request.getName()));
    }

    @GetMapping("/{id}")
    public Group createGroup(@PathVariable long id) {
        return groupRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No group with such id"));
    }

    @DeleteMapping("/{id}")
    public void deleteGroupById(@PathVariable long id) {
        Group group = groupRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No group with such id"));
        partitionRepository.deleteByGroup(group);
        courseRepository.deleteByGroup(group);
        for (UserGroupRole ugr : group.getUserGroupRoleSet()) {
            ugr.getUser().getGroups().remove(ugr);
        }
        userGroupRoleRepository.deleteByGroup(group);
        groupRepository.deleteById(id);
    }
}
