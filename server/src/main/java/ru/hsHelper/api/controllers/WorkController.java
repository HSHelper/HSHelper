package ru.hsHelper.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.requests.add.ObjectsWithSolutionsAddRequest;
import ru.hsHelper.api.requests.create.WorkCreateRequest;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;
import ru.hsHelper.api.requests.update.WorkUpdateRequest;
import ru.hsHelper.api.services.WorkService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/works")
public class WorkController {

    private final WorkService workService;

    @Autowired
    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping("/")
    public Work createWork(@RequestBody @Valid WorkCreateRequest workCreateRequest) {
        return workService.createWork(workCreateRequest);
    }

    @GetMapping("/{id}")
    public Work getWork(@PathVariable long id) {
        return workService.getWorkById(id);
    }

    @PutMapping("/{id}")
    public Work updateWork(@PathVariable long id, @RequestBody @Valid WorkUpdateRequest workUpdateRequest) {
        return workService.updateWork(id, workUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteWork(@PathVariable long id) {
        workService.deleteWork(id);
    }

    @PutMapping("/{id}/users")
    public Work addUsers(@PathVariable long id,
                         @RequestBody @Valid ObjectsWithSolutionsAddRequest objectsWithSolutionsAddRequest) {
        return workService.addUsers(id, objectsWithSolutionsAddRequest.getObjectsIds(),
                objectsWithSolutionsAddRequest.getSolutions());
    }

    @PostMapping("/{id}/users")
    public Work deleteUsers(@PathVariable long id, @RequestBody Set<Long> userIds) {
        return workService.deleteUsers(id, userIds);
    }

    @GetMapping("/")
    public Set<Work> getAll() {
        return workService.getAll();
    }

    @GetMapping("/{workId}/users/{userId}")
    public UserWork getUser(@PathVariable long workId, @PathVariable long userId) {
        return workService.getUser(workId, userId);
    }

    @GetMapping("/{workId}/users")
    public Set<UserWork> getAllUsers(@PathVariable long workId) {
        return workService.getAllUsers(workId);
    }

    @PutMapping("/{workId}/users/{userId}")
    public UserWork updateUserWork(@PathVariable long workId, @PathVariable long userId,
                                   @RequestBody @Valid UserWorkUpdateRequest userWorkUpdateRequest) {
        return workService.updateUserWork(workId, userId, userWorkUpdateRequest);
    }
}
