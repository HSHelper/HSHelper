package ru.hsHelper.api.services.impl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.keys.UserWorkKey;
import ru.hsHelper.api.notifications.PushNotificationService;
import ru.hsHelper.api.repositories.UserWorkRepository;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;
import java.util.Date;

@Service
public class UserWorkService {
    private final UserWorkRepository userWorkRepository;
    private final PushNotificationService pushNotificationService;

    @Autowired
    public UserWorkService(UserWorkRepository userWorkRepository, PushNotificationService pushNotificationService) {
        this.userWorkRepository = userWorkRepository;
        this.pushNotificationService = pushNotificationService;
    }

    @Transactional(readOnly = true)
    public UserWork getUserWork(long userId, long workId) {
        return userWorkRepository.findById(new UserWorkKey(userId, workId)).orElseThrow(
            () -> new IllegalArgumentException("There is not such user with this work")
        );
    }

    @Transactional
    public UserWork updateUserWork(long userId, long workId, UserWorkUpdateRequest userWorkUpdateRequest) {
        UserWork userWork = getUserWork(userId, workId);
        pushNotificationService.updateUserWork(userWork, userWorkUpdateRequest);
        userWork.setSolution(userWorkUpdateRequest.getSolution());
        userWork.setMark(userWorkUpdateRequest.getMark());
        userWork.setSendTime(new Date());
        return userWork;
    }
}
