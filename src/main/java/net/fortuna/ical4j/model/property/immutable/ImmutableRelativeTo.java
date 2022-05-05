/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j.model.property.immutable;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.property.RelativeTo;

/**
 * @author Mike Douglass An immutable instance of RelativeTo.
 * User: mike Date: 5/5/22 Time: 13:50
 */
public class ImmutableRelativeTo extends RelativeTo {

  private static final long serialVersionUID = 5978394762293365042L;

  /**
   * Constant for public classification.
   */
  public static final RelativeTo START = new ImmutableRelativeTo("START");

  /**
   * Constant for private classification.
   */
  public static final RelativeTo END = new ImmutableRelativeTo("END");

  /**
   * @param value
   */
  private ImmutableRelativeTo(final String value) {
    super(new ParameterList(true), value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(final String aValue) {
    throw new UnsupportedOperationException(
            "Cannot modify constant instances");
  }
}
