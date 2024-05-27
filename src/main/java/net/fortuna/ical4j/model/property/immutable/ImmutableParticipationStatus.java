/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j.model.property.immutable;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.property.ParticipationStatus;

/**
 * User: mike Date: 5/26/24 Time: 16:49
 */
public class ImmutableParticipationStatus extends
        ParticipationStatus {
  public static final ImmutableParticipationStatus ACCEPTED =
          new ImmutableParticipationStatus(VALUE_ACCEPTED);

  public static final ImmutableParticipationStatus DECLINED =
          new ImmutableParticipationStatus(VALUE_DECLINED);

  public static final ImmutableParticipationStatus TENTATIVE =
          new ImmutableParticipationStatus(VALUE_TENTATIVE);

  public static final ImmutableParticipationStatus DELEGATED =
          new ImmutableParticipationStatus(VALUE_DELEGATED);

  public static final ImmutableParticipationStatus COMPLETED =
          new ImmutableParticipationStatus(VALUE_COMPLETED);

  public static final ImmutableParticipationStatus IN_PROCESS =
          new ImmutableParticipationStatus(VALUE_IN_PROCESS);

  private static final long serialVersionUID = 7771868877237685612L;

  private ImmutableParticipationStatus(final String value) {
    super(new ParameterList(true), value);
  }

  @Override
  public void setValue(final String aValue) {
    throw new UnsupportedOperationException(
            "Cannot modify constant instances");
  }
}
