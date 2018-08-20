package pl.coffeepower.yet.another.functional;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.io.Resources;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TryItOfTest {

  private final FileSystem unixFileSystem = Jimfs.newFileSystem(Configuration.unix());
  private final FileSystem windowsFileSystem = Jimfs.newFileSystem(Configuration.windows());
  private final FileSystem osXFileSystem = Jimfs.newFileSystem(Configuration.osX());
  private Collection<Path> paths;
  @Mock
  private Path pathWithException;

  @Before
  public void setUp() throws Exception {
    paths = Arrays.asList(
        Files.createFile(unixFileSystem.getPath(".unix_file")),
        Files.createFile(windowsFileSystem.getPath("windows_file")),
        Files.createFile(osXFileSystem.getPath(".osx_file")),
        Paths.get(Resources.getResource("regular_file").toURI()),
        pathWithException
    );

    when(pathWithException.getFileSystem()).thenThrow(new IllegalStateException("File doesn't exist."));
  }

  @Test(expected = IllegalStateException.class)
  public void failsBecauseOfOldPlainJavaImplementation() throws Exception {
    Set<Path> hiddenPaths = TryItOf.getHiddenPaths(paths);
    assertThat(hiddenPaths, hasItems(unixFileSystem.getPath(".unix_file"), osXFileSystem.getPath(".osx_file")));
  }

  @Test
  public void passesBecauseOfNewClearWayWithVavrImplementation() throws Exception {
    io.vavr.collection.Set<Path> hiddenPaths = TryItOf.getHiddenPaths(io.vavr.collection.List.ofAll(paths));
    assertThat(hiddenPaths, hasItems(unixFileSystem.getPath(".unix_file"), osXFileSystem.getPath(".osx_file")));
  }

  @Test
  public void name() throws Exception {
    Set<File> regular_file = TryItOf
        .getHiddenFiles(Collections.singleton(new File(Resources.getResource("regular_file").getPath())));
    regular_file.isEmpty();
  }
}