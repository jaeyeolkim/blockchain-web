package com.okbank.blockchain.config.auth;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Entity 의 생성자/수정자 정보를 자동으로 입력해주는 클래스이다.
 * 단, (@EntityListeners(AuditingEntityListener.class) 선언하거나 BaseEntity를 상속한 엔티티에 한해)
 *
 * @TODO Security Filter 를 통해 Authentication 정보를 세팅해야 userId 값을 받아올 수 있게 된다.
 * @author jaeyeol
 */
@Component
public class UserAuditAware implements AuditorAware<String> {

    /**
     * Auditing 기능이 활성화된 엔티티에 변경이 감지되면 호출되어 생성자/수정자 정보를 반환한다.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken
                || authentication.getPrincipal() == null) {
            return Optional.empty();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Optional.ofNullable(userDetails.getUserUid());
        */
        return Optional.ofNullable("okbank");
    }
}
