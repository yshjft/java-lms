package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {

    private Long id;
    private Course course;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String coverImage;
    private SessionType sessionType;
    private SessionStatus sessionStatus;
    private SessionCapacity sessionCapacity;

    public Session(Long id, Course course, LocalDateTime startDate, LocalDateTime endDate, String coverImage, SessionType sessionType, SessionStatus sessionStatus, int maximumCapacity) {
        this.id = id;
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImage = coverImage;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.sessionCapacity = new SessionCapacity(maximumCapacity);
    }

    public void enrollSession(NsUser student) {
        if (sessionStatus != SessionStatus.RECRUITING) {
            throw new IllegalArgumentException("모집중일때만 신청 가능하다");
        }
        sessionCapacity.addUser(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(course, session.course) && Objects.equals(startDate, session.startDate) && Objects.equals(endDate, session.endDate) && Objects.equals(coverImage, session.coverImage) && sessionType == session.sessionType && sessionStatus == session.sessionStatus && Objects.equals(sessionCapacity, session.sessionCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, startDate, endDate, coverImage, sessionType, sessionStatus, sessionCapacity);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", course=" + course +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", coverImage='" + coverImage + '\'' +
                ", sessionType=" + sessionType +
                ", sessionStatus=" + sessionStatus +
                ", sessionCapacity=" + sessionCapacity +
                '}';
    }

    public int getCurrentUserSize() {
        return sessionCapacity.getCurrentUserSize();
    }
}