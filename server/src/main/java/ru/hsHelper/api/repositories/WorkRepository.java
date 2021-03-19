package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Work;
import org.springframework.data.repository.CrudRepository;

public interface WorkRepository extends CrudRepository<Work, Long> {
}
