package ru.hsHelper.api.repositories;

import org.jetbrains.annotations.NotNull;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface WorkRepository extends CrudRepository<Work, Long> {
    Set<Work> findAllByIdIn(Set<Long> workIds);

    Set<Work> findAllByCoursePart(CoursePart coursePart);

    @NotNull
    Set<Work> findAll();
}
