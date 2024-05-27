/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j.model.property.immutable;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.property.PollMode; /**
 * Constant for basic poll-mode.
 */
/**
 * @author Ben Fortuna An immutable instance of PollMode.
 */
public final class ImmutablePollMode extends PollMode {

  private static final long serialVersionUID = 5978394762293365042L;

  public static final PollMode BASIC = new ImmutablePollMode("BASIC");

  /**
   * @param value
   */
  private ImmutablePollMode(final String value) {
    super(new ParameterList(true), value);
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(final String aValue) {
    throw new UnsupportedOperationException(
            "Cannot modify constant instances");
  }
}
