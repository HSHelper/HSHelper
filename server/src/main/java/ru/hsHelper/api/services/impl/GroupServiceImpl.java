package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.services.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
    }
}
