package nextstep.courses.domain;

import java.util.List;
import java.util.Optional;

public interface SessionUserMappingRepository {
    int save(SessionUser sessionUserMapping);

    SessionUser findById(Long id);

    List<SessionUser> findBySessionId(Long sessionId);
    List<SessionUser> findByUserId(Long userId);

    int update(SessionUser sessionUserMapping);
    Optional<List<SessionUser>> findBySessionIds(List<Long> sessionIds);

    int deleteById(Long id);

    int deleteBySessionId(Long sessionId);

    int deleteByUserId(Long userId);

}
