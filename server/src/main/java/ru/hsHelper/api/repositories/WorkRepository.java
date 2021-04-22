package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface WorkRepository extends CrudRepository<Work, Long> {
    Set<Work> findAllByIdIn(Set<Long> workIds);
}
