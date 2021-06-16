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
    Work getWorkById(long workId);
    Work updateWork(long workId, WorkUpdateRequest workUpdateRequest);
    void deleteWork(long workId);
    void preDeleteWork(Work work);
    Work addUsers(long workId, Set<Long> userIds, Map<Long, String> solutions);
    Work deleteUsers(long workId, Set<Long> userIds);
    Set<Work> getAllWorks();
    UserWork getUserWork(long workId, long userId);
    Set<UserWork> getAllUserWorks(long workId);
    UserWork updateUserWork(long workId, long userId, UserWorkUpdateRequest userWorkUpdateRequest);
}
