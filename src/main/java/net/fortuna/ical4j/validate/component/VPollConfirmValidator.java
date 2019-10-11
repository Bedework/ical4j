/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j.validate.component;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VPoll;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;

/**
 * <pre>
 * Component/Property  Presence
 * ------------------- ----------------------------------------------
 * METHOD                1       MUST be "CONFIRM"
 * VPOLL                 1
 *     COMPLETED         1
 *     DTSTAMP           1
 *     ORGANIZER         1
 *     PRIORITY          1
 *     SEQUENCE          1       MUST be greater than 0
 *     SUMMARY           1       Can be null.
 *     UID               1       MUST match that of the original to-do
 *
 *     ATTACH            0+
 *     ATTENDEE          0+
 *     CATEGORIES        0 or 1  This property may contain a list of
 *                               values
 *     CLASS             0 or 1
 *     COMMENT           0 or 1
 *     CONTACT           0+
 *     CREATED           0 or 1
 *     DESCRIPTION       0 or 1  Can be null
 *     DTSTART           0 or 1
 *     DUE               0 or 1  If present DURATION MUST NOT be present
 *     DURATION          0 or 1  If present DUE MUST NOT be present
 *     EXDATE            0+
 *     EXRULE            0+
 *     GEO               0 or 1
 *     LAST-MODIFIED     0 or 1
 *     LOCATION          0 or 1
 *     PERCENT-COMPLETE  0 or 1
 *     RDATE             0+
 *     RELATED-TO        0+
 *     RESOURCES         0 or 1  This property may contain a list of
 *                               values
 *     RRULE             0+
 *     STATUS            0 or 1  MAY be one of COMPLETED/NEEDS ACTION/IN-
 *                               PROCESS
 *     URL               0 or 1
 *     X-PROPERTY        0+
 *
 *     RECURRENCE-ID     0
 *     REQUEST-STATUS    0
 *
 * VALARM                0+
 * VTIMEZONE             0+      MUST be present if any date/time refers
 *                               to a timezone
 * X-COMPONENT           0+
 *
 * VEVENT                0
 * VJOURNAL              0
 * VFREEBUSY             0
 * </pre>
 *
 */
public class VPollConfirmValidator extends VPollItipValidator {

    private static final long serialVersionUID = 1L;

    public void validate(final VPoll target) throws ValidationException {
        PropertyValidator.getInstance().assertOne(Property.DTSTAMP, target.getProperties());
        PropertyValidator.getInstance().assertOne(Property.ORGANIZER, target.getProperties());
        PropertyValidator.getInstance().assertOne(Property.SUMMARY, target.getProperties());
        PropertyValidator.getInstance().assertOne(Property.UID, target.getProperties());

        PropertyValidator.getInstance().assertOneOrLess(Property.CONTACT, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.CREATED, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.DESCRIPTION, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.DTSTART, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.DUE, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.DURATION, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.LAST_MODIFIED, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.PRIORITY, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.SEQUENCE, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.STATUS, target.getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.URL, target.getProperties());

        PropertyValidator.getInstance().assertNone(Property.ACCEPT_RESPONSE, target.getProperties());
        PropertyValidator.getInstance().assertNone(Property.ATTACH, target.getProperties());
        PropertyValidator.getInstance().assertNone(Property.CATEGORIES, target.getProperties());
        PropertyValidator.getInstance().assertNone(Property.CLASS, target.getProperties());
        PropertyValidator.getInstance().assertNone(Property.RECURRENCE_ID, target.getProperties());
        PropertyValidator.getInstance().assertNone(Property.REQUEST_STATUS, target.getProperties());
        PropertyValidator.getInstance().assertNone(Property.VOTER, target.getProperties());

        validateAlarms(target, Method.CONFIRM);
        validateParticipants(target, Method.CONFIRM);
    }
}
