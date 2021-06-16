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

    @GetMapping("/{partitionId}")
    public Partition getPartition(@PathVariable long partitionId) {
        return partitionService.getPartitionById(partitionId);
    }

    @PutMapping("/{partitionId}")
    public Partition updatePartition(@PathVariable long partitionId, @RequestBody @Valid PartitionUpdateRequest partitionUpdateRequest) {
        return partitionService.updatePartition(partitionId, partitionUpdateRequest);
    }

    @DeleteMapping("/{partitionId}")
    public void deletePartition(@PathVariable long partitionId) {
        partitionService.deletePartition(partitionId);
    }

    @PutMapping("/{partitionId}/users")
    public Partition addUsers(@PathVariable long partitionId, @RequestBody @Valid PartitionAddRequest partitionAddRequest) {
        return partitionService.addUsers(partitionId, partitionAddRequest.getPartitionIds(), partitionAddRequest.getUserParts());
    }

    @PostMapping("/{partitionId}/users")
    public Partition deleteUsers(@PathVariable long partitionId, @RequestBody Set<Long> userIds) {
        return partitionService.deleteUsers(partitionId, userIds);
    }

    @GetMapping("/")
    public Set<Partition> getAllPartitions() {
        return partitionService.getAllPartitions();
    }

    @GetMapping("/{partitionId}/users/{userId}")
    public UserToPartition getUserToPartition(@PathVariable long partitionId, @PathVariable long userId) {
        return partitionService.getUserToPartition(partitionId, userId);
    }

    @GetMapping("/{partitionId}/users")
    public Set<UserToPartition> getAllUserToPartitions(@PathVariable long partitionId) {
        return partitionService.getAllUserToPartitions(partitionId);
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
