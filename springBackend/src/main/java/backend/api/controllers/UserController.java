package backend.api.controllers;

import backend.api.entities.User;
import backend.api.entities.UserCoursePartRole;
import backend.api.entities.UserCourseRole;
import backend.api.entities.UserGroupRole;
import backend.api.entities.UserToPartition;
import backend.api.entities.UserWork;
import backend.api.repositories.UserCoursePartRoleRepository;
import backend.api.repositories.UserCourseRoleRepository;
import backend.api.repositories.UserGroupRoleRepository;
import backend.api.repositories.UserRepository;
import backend.api.repositories.UserToPartitionRepository;
import backend.api.repositories.UserWorkRepository;
import backend.api.requests.create.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserCoursePartRoleRepository userCoursePartRoleRepository;
    private final UserCourseRoleRepository userCourseRoleRepository;
    private final UserGroupRoleRepository userGroupRoleRepository;
    private final UserToPartitionRepository userToPartitionRepository;
    private final UserWorkRepository userWorkRepository;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserCoursePartRoleRepository userCoursePartRoleRepository,
                          UserCourseRoleRepository userCourseRoleRepository, UserGroupRoleRepository userGroupRoleRepository, UserToPartitionRepository userToPartitionRepository, UserWorkRepository userWorkRepository) {
        this.userRepository = userRepository;
        this.userCoursePartRoleRepository = userCoursePartRoleRepository;
        this.userCourseRoleRepository = userCourseRoleRepository;
        this.userGroupRoleRepository = userGroupRoleRepository;
        this.userToPartitionRepository = userToPartitionRepository;
        this.userWorkRepository = userWorkRepository;
    }

    @PutMapping({"", "/"})
    public User createUser(@RequestBody @Valid UserCreateRequest request) {
        User user = new User(request.getFirstName(), request.getLastName());
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No user with such id"));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No user with such id"));
        for (UserGroupRole ugr : user.getGroups()) {
            ugr.getGroup().getUserGroupRoleSet().remove(ugr);
        }
        userGroupRoleRepository.deleteByUser(user);
        for (UserToPartition utp : user.getPartitions()) {
            utp.getPartition().getUsers().remove(utp);
        }
        userToPartitionRepository.deleteByUser(user);
        for (UserCourseRole ucr : user.getCourses()) {
            ucr.getCourse().getUsers().remove(ucr);
        }
        userCourseRoleRepository.deleteByUser(user);
        for (UserCoursePartRole ucpr : user.getCourseParts()) {
            ucpr.getCoursePart().getUsers().remove(ucpr);
        }
        userCoursePartRoleRepository.deleteByUser(user);
        for (UserWork uw : user.getUserWorks()) {
            uw.getWork().getUsers().remove(uw);
        }
        userWorkRepository.deleteByUser(user);
        userRepository.deleteById(id);
    }
}
