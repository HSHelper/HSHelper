package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.requests.create.WorkCreateRequest;

public interface WorkService {
    Work createWork(WorkCreateRequest workCreateRequest);
    Work getWorkById(long id);
}
