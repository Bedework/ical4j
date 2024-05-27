/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j.model.property.immutable;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.property.PollCompletion;

/**
 * User: mike Date: 5/26/24 Time: 23:23
 */
public class ImmutablePollCompletion extends
        PollCompletion {
  // Poll completion values for a "VPOLL"
  /**
   * Server responsible for choosing and submitting winner.
   */
  public static final PollCompletion POLL_COMPLETION_SERVER =
          new ImmutablePollCompletion("SERVER");

  /**
   * Server responsible for submitting winner.
   */
  public static final PollCompletion POLL_COMPLETION_SERVER_SUBMIT =
          new ImmutablePollCompletion("SERVER-SUBMIT");

  /**
   * Server responsible for choosing winner.
   */
  public static final PollCompletion POLL_COMPLETION_SERVER_CHOICE =
          new ImmutablePollCompletion("SERVER-CHOICE");
  /**
   * Client responsible for choosing and submitting winner.
   */
  public static final PollCompletion POLL_COMPLETION_CLIENT =
          new ImmutablePollCompletion("CLIENT");


  private static final long serialVersionUID = 7771868877237685612L;

  private ImmutablePollCompletion(final String value) {
    super(new ParameterList(true), value);
  }

  public void setValue(final String aValue) {
    throw new UnsupportedOperationException(
            "Cannot modify constant instances");
  }
}
