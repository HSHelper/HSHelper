package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.requests.create.WorkCreateRequest;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;
import ru.hsHelper.api.requests.update.WorkUpdateRequest;

import java.util.Map;
import java.util.Set;

public interface WorkService {
    Work createWork(WorkCreateRequest workCreateRequest);
    Work getWorkById(long id);
    Work updateWork(long id, WorkUpdateRequest workUpdateRequest);
    void deleteWork(long id);
    void preDeleteWork(Work work);
    Work addUsers(long workId, Set<Long> userIds, Map<Long, String> solutions);
    Work deleteUsers(long workId, Set<Long> userIds);
    Set<Work> getAll();
    UserWork getUser(long workId, long userId);
    Set<UserWork> getAllUsers(long workId);
    UserWork updateUserWork(long workId, long userId, UserWorkUpdateRequest userWorkUpdateRequest);
}
