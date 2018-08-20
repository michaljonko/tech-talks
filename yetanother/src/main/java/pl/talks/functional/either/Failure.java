package pl.talks.functional.either;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import java.util.Objects;
import java.util.Set;

/**
 * Container of constraints. It could be used in Either.
 */
public final class Failure {

  private Set<Constraint> constraints;

  public Failure(Set<Constraint> constraints) {
    this.constraints = ImmutableSet.copyOf(constraints);
  }

  public Failure(Constraint constraint) {
    this.constraints = ImmutableSet.of(constraint);
  }

  public Set<Constraint> getConstraints() {
    return ImmutableSet.copyOf(constraints);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Failure failure = (Failure) o;
    return Objects.equals(constraints, failure.constraints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(constraints);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("constraints", constraints)
        .toString();
  }
}
