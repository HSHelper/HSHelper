package ru.hsHelper.api.services.impl.util;

import io.reactivex.rxjava3.functions.Function3;
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
import ru.hsHelper.api.sheets.SheetsController;
import ru.hsHelper.api.sheets.data.MarksColumnData;
import ru.hsHelper.api.sheets.data.SheetInitData;
import ru.hsHelper.api.sheets.data.SheetLinkData;
import ru.hsHelper.api.sheets.data.UsersRangeData;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final SheetsController sheets;

    @Autowired
    public InitService(WorkService workService, UserService userService, GroupService groupService,
                       CourseService courseService, CoursePartService coursePartService,
                       PartitionService partitionService, PermissionService permissionService, RoleService roleService,
                       NotificationService notificationService, SheetsController sheets) {
        this.workService = workService;
        this.userService = userService;
        this.groupService = groupService;
        this.courseService = courseService;
        this.coursePartService = coursePartService;
        this.partitionService = partitionService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.notificationService = notificationService;
        this.sheets = sheets;
    }

    @Transactional
    public void initialize() throws Throwable {
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

        var users = List.of(
            userService.createUser(new User("Viktor", "Samoylov", "vitya@gmail.com")),
            userService.createUser(new User("Roma", "Venediktov", "roma@gmail.com")),
            userService.createUser(new User("Kirill", "Turov", "kirill@gmail.com"))
        );

        Group group = groupService.createGroup(new Group("HSE 2019"));

        Partition partition = partitionService.createPartition(new PartitionCreateRequest("Test Partition", group.getId()));

        Course cpp = courseService.createCourse(new CourseCreateRequest("C++", partition.getId(), group.getId()));
        Course java = courseService.createCourse(new CourseCreateRequest("Java", partition.getId(), group.getId()));

        var courses = List.of(cpp, java);

        CoursePart cpplabs = coursePartService.createCoursePart(new CoursePartCreateRequest("C++ Labs",
            "1WsAUNXAIl-k2hI6GUD5gbhb1DA_wo3MXK0h5BNzTFUw", "336654144",
            partition.getId(), cpp.getId(), 0.5, 0.5));
        CoursePart cpphws = coursePartService.createCoursePart(new CoursePartCreateRequest("C++ HWs",
            "1Z5N0DidQBTpxYdIpxAUkwvIk-jwQ9jQQUoj2Gwv9Aew", "0",
            partition.getId(), cpp.getId(), 0.5, 0.5));
        CoursePart javalabs = coursePartService.createCoursePart(new CoursePartCreateRequest("Java Labs",
            "1556pILWqmmwvKdkJBm_VHPXRXkA7d6omuB8zRB3aucs", "2084554136",
            partition.getId(), java.getId(), 1, 0.5));

        var courseParts = List.of(cpplabs, cpphws, javalabs);

        Function3<String, Double, CoursePart, Work> newWork = (String name, Double weight, CoursePart coursePart) ->
            workService.createWork(new WorkCreateRequest(name, "", new Date(), weight, 0.4, Work.WorkType.HOMEWORK, coursePart.getId()));

        var works = List.of(
            newWork.apply("Lab 1", 0.5, cpplabs),
            newWork.apply("Lab 2", 0.2, cpplabs),
            newWork.apply("Lab 3", 0.3, cpplabs),
            newWork.apply("Lab 1", 0.6, javalabs),
            newWork.apply("Lab 2", 0.4, javalabs),
            newWork.apply("HW 1", 0.5, cpphws),
            newWork.apply("HW 2", 0.25, cpphws),
            newWork.apply("HW 3", 0.25, cpphws)
        );

        var allWorks = works.stream().map(Work::getId).collect(Collectors.toSet());
        Map<Long, String> allWorksSolutions = new HashMap<>();
        for (Work work : works) {
            allWorksSolutions.put(work.getId(), "");
        }

        long newMarkId = notificationService.createNotification(new NotificationCreateRequest(Notification.NotificationType.USERWORKUPDATE)).getId();
        long newWorkId = notificationService.createNotification(new NotificationCreateRequest(Notification.NotificationType.WORKUPDATE)).getId();
        var notifications = Set.of(newMarkId, newWorkId);

        for (User user : users) {
            userService.addGroups(user.getId(), Set.of(group.getId()), Map.of(group.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
            for (Course course : courses) {
                userService.addCourses(user.getId(), Set.of(course.getId()), Map.of(course.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
            }
            for (CoursePart coursePart : courseParts) {
                userService.addCourseParts(user.getId(), Set.of(coursePart.getId()), Map.of(coursePart.getId(), Set.of(rm.get(Role.RoleType.STUDENT))));
            }
            userService.addWorks(user.getId(), allWorks, allWorksSolutions);
            userService.addNotifications(user.getId(), notifications);
        }

        var userIds = users.stream().map(User::getId).collect(Collectors.toList());
        var columns = List.of(
            new MarksColumnData("B", works.get(5).getId()),
            new MarksColumnData("E", works.get(6).getId()),
            new MarksColumnData("I", works.get(7).getId())
        );
        sheets.createSheet(new SheetInitData(
            new SheetLinkData("1Z5N0DidQBTpxYdIpxAUkwvIk-jwQ9jQQUoj2Gwv9Aew", "Лист1"),
            new UsersRangeData(3, userIds),
            columns
        ));
    }
}
