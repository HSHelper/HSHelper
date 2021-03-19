package backend.api.controllers;

import backend.api.entities.Group;
import backend.api.entities.UserGroupRole;
import backend.api.repositories.CourseRepository;
import backend.api.repositories.GroupRepository;
import backend.api.repositories.PartitionRepository;
import backend.api.repositories.UserGroupRoleRepository;
import backend.api.requests.create.GroupCreateRequest;
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
