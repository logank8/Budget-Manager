package model;

import model.exceptions.UnevenRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExceptionTest {

    UnevenRangeException e;

    @BeforeEach
    public void runBefore() {
        e = new UnevenRangeException();
    }

    @Test
    public void test() {
        assertNotNull(e);
    }
}
