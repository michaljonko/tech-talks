package pl.org.jdd.legacy.stub;

/**
 * The place in which Tola stores valuable souvenirs
 */
public final class Treasury {

  public Location put(SouvenirPackage souvenirPackage) {
    return new Location("safe behind the picture");
  }
}
