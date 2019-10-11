/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j.validate.component;

import net.fortuna.ical4j.model.component.Participant;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VPoll;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;

import static net.fortuna.ical4j.model.property.ParticipantType.VOTER;
public abstract class VPollItipValidator implements Validator<VPoll> {

    private static final long serialVersionUID = 1L;

    public void validateAlarms(final VPoll target,
                               final Method method) throws ValidationException {
        // validate that getAlarms() only contains VAlarm components
        for (final VAlarm vAlarm : target.getAlarms()) {
            vAlarm.validate(method);
        }
    }

    public void validateParticipants(final VPoll target,
                                     final Method method) throws ValidationException {
        for (final Participant participant : target.getVoters()) {
            if (!VOTER.equals(participant.getParticipantType())) {
                throw new ValidationException("VPOLL voter" +
                        " must have type "
                        + VOTER + ": found " +
                        participant.getParticipantType());
            }
            participant.validate(method);
        }
    }
}
