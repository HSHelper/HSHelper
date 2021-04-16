package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Group;

public interface GroupService {
    Group createGroup(Group group);
    Group getGroupById(long id);
}
