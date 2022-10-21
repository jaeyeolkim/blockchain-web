package com.okbank.blockchain.service.user;

import com.okbank.blockchain.domain.user.User;
import com.okbank.blockchain.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findUser(Long userUid) {
        return userRepository.findById(userUid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. userUid=" + userUid));
    }
}
