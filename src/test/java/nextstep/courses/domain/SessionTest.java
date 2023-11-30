package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static nextstep.courses.domain.SessionFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SessionTest {

    static final LocalDateTime BEFORE_START_DATE = LocalDateTime.of(2022, 12, 31, 23, 59);
    static final LocalDateTime AFTER_END_DATE = LocalDateTime.of(2024, 1, 1, 0, 0);
    static final LocalDateTime DURING_START_AND_END_DATE = LocalDateTime.of(2023, 6, 1, 0, 0);
    static final Price PRICE_1000 = new Price(1000);


    @Test
    @DisplayName("강의가 진행중 인지 확인 할 수 있다.")
    void sessionHasStartAndEndDate() {
        Session session = SESSION_유료_모집중_10명_1000원_최대사이즈이미지포함(START_DATE_2023, END_DATE_2023);
        assertThat(session.isInProgress(LocalDateTime.of(2023,2,1,0,0))).isTrue();
    }

    @Test
    @DisplayName("강의는 강의 커버 이미지 정보를 가진다.")
    void sessionHasCoverUrl() {
        assertThat(basePaidSession()).hasFieldOrProperty("coverImage");
    }

    @Test
    @DisplayName("무료 강의는 최대 수강 인원 제한이 없다.")
    void sessionHasSessionType() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Session(
                    START_DATE_2023,
                    END_DATE_2023,
                    SessionType.FREE,
                    new Image(ImageTest.IMAGE_URL, 1024, 1024, ImageType.PNG),
                    SessionStatus.OPENED,
                    1,
                    1000
            );
        });
    }

    @Test
    @DisplayName("유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.")
    void sessionHasFee() {
        Session session = SESSION_유료_모집중_10명_1000원_최대사이즈이미지포함(START_DATE_2023, END_DATE_2023);
        assertThat(session.enroll(NsUserTest.JAVAJIGI, PRICE_1000, DURING_START_AND_END_DATE)).isTrue();
    }

    @Test
    @DisplayName("유료 강의는 강의 최대 수강 인원을 초과할 수 없다.")
    void sessionHasMaximumEnrollment() {
        Session session = SESSION_최대인원으로_수강신청됨();
        assertThatIllegalArgumentException().isThrownBy(() -> {
            session.enroll(NsUserTest.SANJIGI, PRICE_1000, DURING_START_AND_END_DATE);
        });
    }

    private static Session SESSION_최대인원으로_수강신청됨() {
        Session session = SESSION_유료_모집중_10명_1000원_최대사이즈이미지포함(START_DATE_2023, END_DATE_2023);
        for (int i = 0; i < 10; i++) {
            session.enroll(NsUserTest.JAVAJIGI, PRICE_1000, DURING_START_AND_END_DATE);
        }
        return session;
    }

    @Test
    @DisplayName("강의 수강신청은 강의 상태가 모집중일 때만 가능하다.")
    void sessionHasSessionStatus() {
        Session session = SESSION_유료_모집중_10명_1000원_최대사이즈이미지포함(START_DATE_2023, END_DATE_2023);
        assertThat(session.enroll(NsUserTest.JAVAJIGI, PRICE_1000, DURING_START_AND_END_DATE)).isTrue();
    }

    @Test
    @DisplayName("강의 수강신청은 강의 기간이 종료된 후 불가능하다.")
    void cannotEnrollAfterEndDate() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            SESSION_유료_모집중_10명_1000원_최대사이즈이미지포함(START_DATE_2023, END_DATE_2023)
                    .enroll(NsUserTest.JAVAJIGI, PRICE_1000, AFTER_END_DATE);
        });
    }

}
