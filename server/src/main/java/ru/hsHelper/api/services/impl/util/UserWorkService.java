package ru.hsHelper.api.services.impl.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.keys.UserWorkKey;
import ru.hsHelper.api.repositories.UserWorkRepository;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;

import java.util.Date;

@Service
public class UserWorkService {
    private final UserWorkRepository userWorkRepository;


    public UserWorkService(UserWorkRepository userWorkRepository) {
        this.userWorkRepository = userWorkRepository;
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
        userWork.setSolution(userWorkUpdateRequest.getSolution());
        userWork.setMark(userWorkUpdateRequest.getMark());
        userWork.setSendTime(new Date());
        return userWork;
    }
}
