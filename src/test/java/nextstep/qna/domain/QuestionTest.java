package nextstep.qna.domain;

import nextstep.fixture.TestFixture;
import nextstep.qna.exception.QuestionDeleteUnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;

public class QuestionTest {
    @BeforeEach
    public void setUp() {
        TestFixture.fixtureInit();
    }

    @DisplayName("Answer 가 존재하지 않는 경우 삭제에 성공한다")
    @Test
    public void noAnswerDeleteSuccess() {
        //given
        NsUser loginUser = TestFixture.BADAJIGI;
        Question question = TestFixture.BADAJIGI_QUESTION;
        Answer answer = TestFixture.BADAJIGI_ANSWER;

        //when
        question.delete(loginUser);
        //then
        assertAll("삭제성공을 검증한다",
                () -> assertThat(question.isDeleted())
                        .as("삭제 질의시 True 를 리턴해야한다")
                        .isTrue(),
                () -> assertThat(answer.isDeleted())
                        .as("연관되어있지 않은 Answer 는 삭제되지 않는다")
                        .isFalse()
        );
    }

    @DisplayName("작성자 본인이 작성한 Answer 만 존재하는 경우 삭제에 성공한다")
    @Test
    public void selfAnswerDeleteSuccess() {
        //given
        NsUser user = TestFixture.JAVAJIGI;
        Question question = TestFixture.JAVAJIGI_QUESTION;
        Answer answer = TestFixture.JAVAJIGI_ANSWER;
        question.addAnswer(answer);

        //when
        question.delete(user);

        //then
        assertAll("삭제성공을 검증한다",
                () -> assertThat(question.isDeleted())
                        .as("Question 삭제에 성공해야한다")
                        .isTrue(),
                () -> assertThat(answer.isDeleted())
                        .as("연관된 Answer 도 모두 삭제되어야한다")
                        .isTrue()
        );
    }

    @DisplayName("타인이 작성한 Answer 이 존재하는 경우 QuestionDeleteAnswerExistedException 를 던진다")
    @Test
    public void validateAnswers() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {

        }).isInstanceOf(QuestionDeleteUnauthorizedException.class)
                .hasMessageContaining("바꾸셈");
        fail();
    }

    @DisplayName("작성자가 아닌경우 QuestionDeleteUnauthorizedException 를 던진다")
    @Test
    public void validateQuestionOwner() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {

        }).isInstanceOf(QuestionDeleteUnauthorizedException.class)
                .hasMessageContaining("바꾸셈");
        fail();
    }

    @DisplayName("자신의 정보에 맞게 DeleteHistory 를 생성한다")
    @Test
    public void toDeleteHistory() {
        //given
        //when
        //then
        fail();
        assertAll("",
                () -> assertThat(true)
                        .as("")
                        .isTrue()
        );
    }

}
