import ru.tenebrius.lab2.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Test {
    @org.junit.jupiter.api.Test
    public void mainTest() {
        assertNull(Main.calculatePrice("0", "-1", "0", "0", "0", null, null));

        assertEquals(550, Main.calculatePrice("0", "100", "101", "500", "50", null, null));
        assertEquals(500, Main.calculatePrice("0", "100", "100", "500", "50", null, null));

        assertEquals(500, Main.calculatePrice("1", "100", "100", null, null, "5", "10"));
        assertEquals(1011, Main.calculatePrice("1", "100", "101", null, null, "5", "10"));
    }
}
