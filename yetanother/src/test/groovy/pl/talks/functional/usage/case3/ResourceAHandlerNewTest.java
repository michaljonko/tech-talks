package pl.talks.functional.usage.case3;

import io.vavr.control.Option;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceAHandlerNewTest {

    @Test
    public void name() {
        String s1 = Option.of("T")
                .filter(s -> s.equals("T"))
                .onEmpty(() -> System.out.println("pusto"))
                .map(s -> s + "T")
                .peek(s -> System.out.println(s))
                .get();
        System.out.println(s1);
    }
}