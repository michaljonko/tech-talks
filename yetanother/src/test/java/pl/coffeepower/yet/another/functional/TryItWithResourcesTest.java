package pl.coffeepower.yet.another.functional;

import java.io.StringReader;
import org.junit.Test;

public class TryItWithResourcesTest {

  private static final String SEARCHED_WORD = "world";
  private static final String INPUT_TEXT = "Hello world!\nAll the world is,so bad world.";

  @Test
  public void name1() throws Exception {
    assert TryItWithResources
        .counter(() -> new StringReader(INPUT_TEXT), SEARCHED_WORD) == 3;

  }

  @Test
  public void name2() throws Exception {
    assert TryItWithResources
        .counter(() -> new StringReader(INPUT_TEXT), "is") == 1;
  }

  @Test
  public void name3() throws Exception {
    assert TryItWithResources
        .counterOldWay(new StringReader(INPUT_TEXT), SEARCHED_WORD) == 3;
  }

  @Test
  public void name4() throws Exception {
    assert TryItWithResources
        .counterOldWay(new StringReader(INPUT_TEXT), "is") == 1;

  }
}