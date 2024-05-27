/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j.model.property.immutable;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.property.Kind;

/**
 * User: mike Date: 5/26/24 Time: 22:57
 */
public class ImmutableKind extends Kind {

  /**
   * An individual
   */
  public static final Kind INDIVIDUAL = new ImmutableKind(
          VALUE_INDIVIDUAL);

  /**
   * A group
   */
  public static final Kind GROUP = new ImmutableKind(
          VALUE_GROUP);

  /**
   * A group
   */
  public static final Kind RESOURCE = new ImmutableKind(
          VALUE_RESOURCE);

  /**
   * A group
   */
  public static final Kind LOCATION = new ImmutableKind(
          VALUE_LOCATION);

  private static final long serialVersionUID = 7771868877237685612L;

  private ImmutableKind(final String value) {
    super(new ParameterList(true), value);
  }

  @Override
  public void setValue(final String aValue) {
    throw new UnsupportedOperationException(
            "Cannot modify constant instances");
  }
}
