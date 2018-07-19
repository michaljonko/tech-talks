package pl.coffeepower.yet.another.functional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TryItOfTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  private Collection<Path> paths;
  @Mock
  private Path pathWithException;

  @Before
  public void setUp() throws Exception {
    paths = Arrays.asList(
        Paths.get(temporaryFolder.newFile().toURI()),
        Paths.get(""),
        pathWithException,
        Paths.get(temporaryFolder.newFile().toURI()));

    when(pathWithException.getFileSystem()).thenThrow(new IllegalStateException("File doesn't exist."));
  }

  @Test
  public void failsBecauseOfOldPlainJavaImplementation() throws Exception {
    Set<Path> hiddenPaths = TryItOf.getHiddenPaths(paths);
    assertThat(hiddenPaths.isEmpty(), is(true));
  }

  @Test
  public void passesBecauseOfNewClearWayWithVavrImplementation() throws Exception {
    io.vavr.collection.Set<Path> hiddenPaths = TryItOf.getHiddenPaths(io.vavr.collection.List.ofAll(paths));
    assertThat(hiddenPaths.isEmpty(), is(true));
  }
}