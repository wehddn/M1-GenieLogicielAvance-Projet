package hubertmap.model.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParserFactoryTest {

    @Test
    void parserFactoryTest() {
        ParserFactory factory = new ParserFactory();
        assertNotEquals(null, factory);
    }

}