package bridge;

import static bridge.ui.MessageUtil.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import bridge.domain.Bridge;
import bridge.ui.InputView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("입력값 검증 관련 테스트")
class InputTest {

    InputView inputView;

    @BeforeEach
    void setUp(){
        inputView = new InputView();
    }

    @Nested
    class InputBridgeSize {
        @ParameterizedTest
        @ValueSource(strings = {"aa", " ", "1b", "-."})
        @DisplayName("숫자가 아닌 값을 입력하면 예외 발생")
        void exceptionTest_notNumeric(String input){
            assertThatThrownBy(() -> inputView.readBridgeSize(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INVALID_BRIDGE_SIZE.message);
        }

        @ParameterizedTest
        @ValueSource(strings = {"1", "67", "43"})
        @DisplayName("범위 밖의 숫자 입력 시 예외 발생")
        void exceptionTest_overRange(String input){
            assertThatThrownBy(() -> inputView.readBridgeSize(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INVALID_BRIDGE_SIZE.message);
        }

        @ParameterizedTest
        @CsvSource(value = {"3", "20", "12"})
        @DisplayName("입력값과 같은 크기의 다리가 만들어졌는지 확인")
        void validInputTest(String input){
            int size = inputView.readBridgeSize(input);
            Bridge bridge = new Bridge(size);
            assertEquals(bridge.getBridgeSize(), Integer.parseInt(input));
        }
    }


}