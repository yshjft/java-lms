package nextstep.courses.domain.strategy;

import nextstep.courses.domain.Amount;
import nextstep.courses.domain.Student;
import nextstep.courses.domain.Students;

public interface EnrollmentStrategy {
    void enroll(long payment,
                Amount amount,
                int capacity,
                Student student,
                Students students);
}
