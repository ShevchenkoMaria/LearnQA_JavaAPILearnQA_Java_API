import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex10Text {
    @ParameterizedTest
    @ValueSource(strings = {"", "It is a very long string for this test", "short string"})
    public void testForStringLengh(String str){
        assertTrue(str.length()>15,"This string is a short");
    }
}
