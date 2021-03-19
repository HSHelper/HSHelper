package backend.api.controllers;

import backend.api.entities.Group;
import backend.api.repositories.GroupRepository;
import backend.api.requests.create.GroupCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @PostMapping ({"", "/"})
    public Group createGroup(@RequestBody @Valid GroupCreateRequest request) {
        return groupRepository.save(new Group(request.getName()));
    }
}
