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
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.requests.add.PartitionAddRequest;
import ru.hsHelper.api.requests.create.PartitionCreateRequest;
import ru.hsHelper.api.requests.update.PartitionUpdateRequest;
import ru.hsHelper.api.services.PartitionService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/partitions")
public class PartitionController {
    private final PartitionService partitionService;

    @Autowired
    public PartitionController(PartitionService partitionService) {
        this.partitionService = partitionService;
    }

    @PostMapping("/")
    public Partition createPartition(@RequestBody @Valid PartitionCreateRequest partitionCreateRequest) {
        return partitionService.createPartition(partitionCreateRequest);
    }

    @GetMapping("/{id}")
    public Partition getPartition(@PathVariable long id) {
        return partitionService.getPartitionById(id);
    }

    @PutMapping("/{id}")
    public Partition updatePartition(@PathVariable long id, @RequestBody @Valid PartitionUpdateRequest partitionUpdateRequest) {
        return partitionService.updatePartition(id, partitionUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePartition(@PathVariable long id) {
        partitionService.deletePartition(id);
    }

    @PutMapping("/{id}/users")
    public Partition addUsers(@PathVariable long id, @RequestBody @Valid PartitionAddRequest partitionAddRequest) {
        return partitionService.addUsers(id, partitionAddRequest.getPartitionIds(), partitionAddRequest.getUserParts());
    }

    @DeleteMapping("/{id}/users")
    public Partition deleteUsers(@PathVariable long id, @RequestBody Set<Long> userIds) {
        return partitionService.deleteUsers(id, userIds);
    }

    @GetMapping("/")
    public Set<Partition> getAll() {
        return partitionService.getAll();
    }

    @GetMapping("/{partitionId}/users/{userId}")
    public UserToPartition getUser(@PathVariable long partitionId, @PathVariable long userId) {
        return partitionService.getUser(partitionId, userId);
    }

    @GetMapping("/{partitionId}/users")
    public Set<UserToPartition> getAllUsers(@PathVariable long partitionId) {
        return partitionService.getAllUsers(partitionId);
    }

    @GetMapping("/{partitionId}/courses")
    public Set<Course> getAllCoursesWithSuchDefaultPartition(@PathVariable long partitionId) {
        return partitionService.getCoursesWithSuchDefaultPartition(partitionId);
    }

    @GetMapping("/{partitionId}/course-parts")
    public Set<CoursePart> getAllCourseParts(@PathVariable long partitionId) {
        return partitionService.getCourseParts(partitionId);
    }
}
