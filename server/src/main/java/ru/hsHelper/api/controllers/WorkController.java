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

    @GetMapping("/{workId}")
    public Work getWork(@PathVariable long workId) {
        return workService.getWorkById(workId);
    }

    @PutMapping("/{workId}")
    public Work updateWork(@PathVariable long workId, @RequestBody @Valid WorkUpdateRequest workUpdateRequest) {
        return workService.updateWork(workId, workUpdateRequest);
    }

    @DeleteMapping("/{workId}")
    public void deleteWork(@PathVariable long workId) {
        workService.deleteWork(workId);
    }

    @PutMapping("/{workId}/users")
    public Work addUsers(@PathVariable long workId,
                         @RequestBody @Valid ObjectsWithSolutionsAddRequest objectsWithSolutionsAddRequest) {
        return workService.addUsers(workId, objectsWithSolutionsAddRequest.getObjectsIds(),
                objectsWithSolutionsAddRequest.getSolutions());
    }

    @PostMapping("/{workId}/users")
    public Work deleteUsers(@PathVariable long workId, @RequestBody Set<Long> userIds) {
        return workService.deleteUsers(workId, userIds);
    }

    @GetMapping("/")
    public Set<Work> getAllWorks() {
        return workService.getAllWorks();
    }

    @GetMapping("/{workId}/users/{userId}")
    public UserWork getUserWork(@PathVariable long workId, @PathVariable long userId) {
        return workService.getUserWork(workId, userId);
    }

    @GetMapping("/{workId}/users")
    public Set<UserWork> getAllUserWorks(@PathVariable long workId) {
        return workService.getAllUserWorks(workId);
    }

    @PutMapping("/{workId}/users/{userId}")
    public UserWork updateUserWork(@PathVariable long workId, @PathVariable long userId,
                                   @RequestBody @Valid UserWorkUpdateRequest userWorkUpdateRequest) {
        return workService.updateUserWork(workId, userId, userWorkUpdateRequest);
    }
}
