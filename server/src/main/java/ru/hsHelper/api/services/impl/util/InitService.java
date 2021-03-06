package ru.hsHelper.api.services.impl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.requests.create.CourseCreateRequest;
import ru.hsHelper.api.requests.create.CoursePartCreateRequest;
import ru.hsHelper.api.requests.create.NotificationCreateRequest;
import ru.hsHelper.api.requests.create.PartitionCreateRequest;
import ru.hsHelper.api.requests.create.WorkCreateRequest;
import ru.hsHelper.api.services.CoursePartService;
import ru.hsHelper.api.services.CourseService;
import ru.hsHelper.api.services.GroupService;
import ru.hsHelper.api.services.NotificationService;
import ru.hsHelper.api.services.PartitionService;
import ru.hsHelper.api.services.PermissionService;
import ru.hsHelper.api.services.RoleService;
import ru.hsHelper.api.services.UserService;
import ru.hsHelper.api.services.WorkService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class InitService {
    private final WorkService workService;
    private final UserService userService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final CoursePartService coursePartService;
    private final PartitionService partitionService;
    private final PermissionService permissionService;
    private final RoleService roleService;
    private final NotificationService notificationService;

    @Autowired
    public InitService(WorkService workService, UserService userService, GroupService groupService,
                       CourseService courseService, CoursePartService coursePartService,
                       PartitionService partitionService, PermissionService permissionService, RoleService roleService,
                       NotificationService notificationService) {
        this.workService = workService;
        this.userService = userService;
        this.groupService = groupService;
        this.courseService = courseService;
        this.coursePartService = coursePartService;
        this.partitionService = partitionService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.notificationService = notificationService;
    }

    @Transactional
    public void initialize() {
        Map<Permissions.PermissionType, Long> pm = new HashMap<>();

        for (Permissions.PermissionType permissionType : Permissions.PermissionType.values()) {
            pm.put(permissionType, permissionService.createPermission(new Permissions(permissionType)).getId());
        }

        Map<Role.RoleType, Long> rm = new HashMap<>();

        for (Role.RoleType roleType : Role.RoleType.values()) {
            Role role = roleService.createRole(new Role(roleType));
            rm.put(roleType, role.getId());
            switch (roleType) {
                case ADMIN:
                    roleService.addPermissions(role.getId(), Set.of(pm.get(Permissions.PermissionType.COMMENT),
                        pm.get(Permissions.PermissionType.UPDATE), pm.get(Permissions.PermissionType.CREATE),
                        pm.get(Permissions.PermissionType.VIEW)));
                    break;
                case STUDENT:
                    roleService.addPermissions(role.getId(), Set.of(pm.get(Permissions.PermissionType.COMMENT),
                        pm.get(Permissions.PermissionType.VIEW)));
                    break;
                case OBSERVER:
                    roleService.addPermissions(role.getId(), Set.of(
                        pm.get(Permissions.PermissionType.VIEW)));
                    break;
                case TEACHER:
                    roleService.addPermissions(role.getId(), Set.of(pm.get(Permissions.PermissionType.COMMENT),
                        pm.get(Permissions.PermissionType.UPDATE),
                        pm.get(Permissions.PermissionType.VIEW)));
                    break;
            }
        }

        User user1 = userService.createUser(new User("Viktor", "Samoylov", "vitya@gmail.com"));
        User user2 = userService.createUser(new User("Roma", "Venediktov", "roma@gmail.com"));
        User user3 = userService.createUser(new User("Kirill", "Turov", "kirill@gmail.com"));
        User user4 = userService.createUser(new User("Egor", "Suvorov", "egor@gmail.com"));
        User user5 = userService.createUser(new User("Kuzya", "Kuznetsov", "kuzya@gmail.com"));

        Group group = groupService.createGroup(new Group("Test Group"));

        Partition partition = partitionService.createPartition(new PartitionCreateRequest("Test Partition", group.getId()));

        Course cpp = courseService.createCourse(new CourseCreateRequest("C++", partition.getId(), group.getId()));
        Course java = courseService.createCourse(new CourseCreateRequest("Java", partition.getId(), group.getId()));

        CoursePart cpplabs = coursePartService.createCoursePart(new CoursePartCreateRequest("C++ labs",
            "1WsAUNXAIl-k2hI6GUD5gbhb1DA_wo3MXK0h5BNzTFUw", "336654144",
            partition.getId(), cpp.getId(), 0.5, 0.5));
        CoursePart cpphws = coursePartService.createCoursePart(new CoursePartCreateRequest("C++ hws",
            "1Z5N0DidQBTpxYdIpxAUkwvIk-jwQ9jQQUoj2Gwv9Aew", "0",
            partition.getId(), cpp.getId(), 0.5, 0.5));
        CoursePart javalabs = coursePartService.createCoursePart(new CoursePartCreateRequest("Java labs",
            "1556pILWqmmwvKdkJBm_VHPXRXkA7d6omuB8zRB3aucs", "2084554136",
            partition.getId(), java.getId(), 1, 0.5));

        Work cpplab1 = workService.createWork(new WorkCreateRequest("lab1", "huffman archiver",
            new Date(), 0.5, 0.5, Work.WorkType.HOMEWORK, cpplabs.getId()));
        Work javalab1 = workService.createWork(new WorkCreateRequest("lab1", "git",
            new Date(), 0.5, 0.5, Work.WorkType.HOMEWORK, javalabs.getId()));
        Work cpphw1 = workService.createWork(new WorkCreateRequest("hw1", "", new Date(),
            0.5, 0.4, Work.WorkType.HOMEWORK, cpphws.getId()));
        Work cpphw2 = workService.createWork(new WorkCreateRequest("hw2", "", new Date(),
            0.25, 0.5, Work.WorkType.HOMEWORK, cpphws.getId()));
        Work cpphw3 = workService.createWork(new WorkCreateRequest("hw3", "", new Date(),
            0.25, 0.5, Work.WorkType.HOMEWORK, cpphws.getId()));

        userService.addToPartitions(user1.getId(), Set.of(partition.getId()), Map.of(partition.getId(), 1));
        userService.addToPartitions(user2.getId(), Set.of(partition.getId()), Map.of(partition.getId(), 2));
        userService.addToPartitions(user3.getId(), Set.of(partition.getId()), Map.of(partition.getId(), 3));

        userService.addGroups(user5.getId(), Set.of(group.getId()), Map.of(group.getId(), Set.of(rm.get(Role.RoleType.ADMIN))));
        userService.addGroups(user4.getId(), Set.of(group.getId()), Map.of(group.getId(), Set.of(rm.get(Role.RoleType.TEACHER))));
        userService.addGroups(user3.getId(), Set.of(group.getId()), Map.of(group.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
        userService.addGroups(user2.getId(), Set.of(group.getId()), Map.of(group.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
        userService.addGroups(user1.getId(), Set.of(group.getId()), Map.of(group.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));

        Consumer<Course> courseConsumer = (course) -> {
            userService.addCourses(user3.getId(), Set.of(course.getId()), Map.of(course.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
            userService.addCourses(user2.getId(), Set.of(course.getId()), Map.of(course.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
            userService.addCourses(user1.getId(), Set.of(course.getId()), Map.of(course.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
        };

        Consumer<CoursePart> coursePartConsumer = (course) -> {
            userService.addCourseParts(user3.getId(), Set.of(course.getId()), Map.of(course.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
            userService.addCourseParts(user2.getId(), Set.of(course.getId()), Map.of(course.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
            userService.addCourseParts(user1.getId(), Set.of(course.getId()), Map.of(course.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
        };

        userService.addCourses(user5.getId(), Set.of(java.getId()), Map.of(java.getId(), Set.of(rm.get(Role.RoleType.TEACHER))));
        userService.addCourses(user4.getId(), Set.of(java.getId()), Map.of(java.getId(), Set.of(rm.get(Role.RoleType.OBSERVER))));
        courseConsumer.accept(java);

        userService.addCourses(user5.getId(), Set.of(cpp.getId()), Map.of(cpp.getId(), Set.of(rm.get(Role.RoleType.OBSERVER))));
        userService.addCourses(user4.getId(), Set.of(cpp.getId()), Map.of(cpp.getId(), Set.of(rm.get(Role.RoleType.TEACHER))));
        courseConsumer.accept(cpp);

        userService.addCourseParts(user5.getId(), Set.of(javalabs.getId()), Map.of(javalabs.getId(), Set.of(rm.get(Role.RoleType.TEACHER))));
        userService.addCourseParts(user4.getId(), Set.of(javalabs.getId()), Map.of(javalabs.getId(), Set.of(rm.get(Role.RoleType.OBSERVER))));
        coursePartConsumer.accept(javalabs);

        userService.addCourseParts(user5.getId(), Set.of(cpplabs.getId()), Map.of(cpplabs.getId(), Set.of(rm.get(Role.RoleType.OBSERVER))));
        userService.addCourseParts(user4.getId(), Set.of(cpplabs.getId()), Map.of(cpplabs.getId(), Set.of(rm.get(Role.RoleType.TEACHER))));
        coursePartConsumer.accept(cpplabs);

        userService.addWorks(user1.getId(), Set.of(javalab1.getId()), Map.of(javalab1.getId(), "https://github.com/"));
        userService.addWorks(user2.getId(), Set.of(cpplab1.getId()), Map.of(cpplab1.getId(), "https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%B4_%D0%A5%D0%B0%D1%84%D1%84%D0%BC%D0%B0%D0%BD%D0%B0"));
        userService.addWorks(user3.getId(), Set.of(cpplab1.getId()), Map.of(cpplab1.getId(), "https://en.wikipedia.org/wiki/Huffman_coding"));

        var set = Set.of(cpphw1.getId(), cpphw2.getId(), cpphw3.getId());
        var map = Map.of(cpphw1.getId(), " ", cpphw2.getId(), " ", cpphw3.getId(), " ");
        userService.addWorks(user1.getId(), set, map);
        userService.addWorks(user2.getId(), set, map);
        userService.addWorks(user3.getId(), set, map);

        long newMarkId = notificationService.createNotification(new NotificationCreateRequest(Notification.NotificationType.USERWORKUPDATE)).getId();
        long newWorkId = notificationService.createNotification(new NotificationCreateRequest(Notification.NotificationType.WORKUPDATE)).getId();
        userService.addNotifications(user1.getId(), Set.of(newMarkId, newWorkId));
        userService.addNotifications(user2.getId(), Set.of(newWorkId));
        userService.addNotifications(user3.getId(), Set.of(newMarkId));
    }
}
