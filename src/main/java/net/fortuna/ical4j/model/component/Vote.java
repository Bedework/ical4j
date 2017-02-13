/**
 * Copyright (c) 2010, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.model.component;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Completed;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Geo;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.PercentComplete;
import net.fortuna.ical4j.model.property.PollItemId;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Voter;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Defines an iCalendar VOTE component for use inside a VPOLL component.
 *
 * <pre>
 * </pre>
 *
 * @author Ben Fortuna
 * @author Mike Douglass
 */
public class Vote extends CalendarComponent {

    private static final long serialVersionUID = -269658210065896668L;

    private final Map<Method, Validator> methodValidators = new HashMap<Method, Validator>();
    {
        methodValidators.put(Method.CANCEL, new CancelValidator());
        methodValidators.put(Method.CONFIRM, new ConfirmValidator());
        methodValidators.put(Method.POLLSTATUS, new PollStatusValidator());
        methodValidators.put(Method.PUBLISH, new PublishValidator());
        methodValidators.put(Method.REFRESH, new RefreshValidator());
        methodValidators.put(Method.REPLY, new ReplyValidator());
        methodValidators.put(Method.REQUEST, new RequestValidator());
    }

    /**
     * Default constructor.
     */
    public Vote() {
        this(true);
    }

    public Vote(boolean initialise) {
        super(VOTE);
        if (initialise) {
            getProperties().add(new DtStamp());
        }
    }

  /**
     * Constructor.
     * @param properties a list of properties
     */
    public Vote(final PropertyList properties) {
        super(VOTE, properties);
    }

    /**
     * {@inheritDoc}
     */
    public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(BEGIN);
        buffer.append(':');
        buffer.append(getName());
        buffer.append(Strings.LINE_SEPARATOR);
        buffer.append(getProperties());
        buffer.append(END);
        buffer.append(':');
        buffer.append(getName());
        buffer.append(Strings.LINE_SEPARATOR);
        return buffer.toString();
    }

    /**
     * {@inheritDoc}
     */
    public final void validate(final boolean recurse)
            throws ValidationException {

        if (!CompatibilityHints
                .isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {

            PropertyValidator.getInstance().assertOne(Property.POLL_ITEM_ID,
                                                      getProperties());
        }

        /*
         * ; the following are optional, ; but MUST NOT occur more than once class / completed / created / description /
         * dtstamp / dtstart / geo / last-mod / location / organizer / percent / priority / recurid / seq / status /
         * summary / uid / url /
         */
        PropertyValidator.getInstance().assertOneOrLess(Property.CLASS,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.COMPLETED,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.CREATED,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.DESCRIPTION,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.DTSTAMP,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.DTSTART,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.GEO,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.LAST_MODIFIED,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.LOCATION,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.ORGANIZER,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(
                Property.PERCENT_COMPLETE, getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.PRIORITY,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.RECURRENCE_ID,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.SEQUENCE,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.STATUS,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.SUMMARY,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.UID,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.URL,
                getProperties());

        final Status status = (Status) getProperty(Property.STATUS);
        if (status != null && !Status.VTODO_NEEDS_ACTION.getValue().equals(status.getValue())
                && !Status.VTODO_COMPLETED.getValue().equals(status.getValue())
                && !Status.VTODO_IN_PROCESS.getValue().equals(status.getValue())
                && !Status.VTODO_CANCELLED.getValue().equals(status.getValue())) {
            throw new ValidationException("Status property ["
                    + status.toString() + "] may not occur in VTODO");
        }

        /*
         * ; either 'due' or 'duration' may appear in ; a 'todoprop', but 'due' and 'duration' ; MUST NOT occur in the
         * same 'todoprop' due / duration /
         */
        try {
            PropertyValidator.getInstance().assertNone(Property.DUE,
                    getProperties());
        }
        catch (ValidationException ve) {
            PropertyValidator.getInstance().assertNone(Property.DURATION,
                    getProperties());
        }

        /*
         * ; the following are optional, ; and MAY occur more than once attach / attendee / categories / comment /
         * contact / exdate / exrule / rstatus / related / resources / rdate / rrule / x-prop
         */

        if (recurse) {
            validateProperties();
        }
    }

    /**
     * {@inheritDoc}
     */
    protected Validator getValidator(Method method) {
        return (Validator) methodValidators.get(method);
    }

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
    private class ConfirmValidator implements Validator<Vote> {

		private static final long serialVersionUID = 1L;

        public void validate(final Vote target) throws ValidationException {
            PropertyValidator.getInstance().assertOne(Property.DTSTAMP, target.getProperties());
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
            PropertyValidator.getInstance().assertOneOrLess(Property.VOTER, target.getProperties());

            PropertyValidator.getInstance().assertNone(Property.ACCEPT_RESPONSE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.ATTACH, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.CATEGORIES, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.CLASS, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.ORGANIZER, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.RECURRENCE_ID, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.REQUEST_STATUS, target.getProperties());
        }
    }

    /**
     * <pre>
     * Component/Property   Presence
     * -------------------  ---------------------------------------------
     * METHOD               1     MUST be "CANCEL"
     * VTODO                1
     *     ATTENDEE         0+    include all "Attendees" being removed from
     *                            the todo. MUST include all "Attendees" if
     *                            the entire todo is cancelled.
     *     UID              1     MUST echo original UID
     *     DTSTAMP          1
     *     ORGANIZER        1
     *     SEQUENCE         1
     *
     *     ATTACH           0+
     *     CATEGORIES       0 or 1 This property MAY contain a list of values
     *     CLASS            0 or 1
     *     COMMENT          0 or 1
     *     CONTACT          0+
     *     CREATED          0 or 1
     *     DESCRIPTION      0 or 1
     *     DTSTART          0 or 1
     *     DUE              0 or 1  If present DURATION MUST NOT be present
     *     DURATION         0 or 1  If present DUE MUST NOT be present
     *     EXDATE           0+
     *     EXRULE           0+
     *     GEO              0 or 1
     *     LAST-MODIFIED    0 or 1
     *     LOCATION         0 or 1
     *     PERCENT-COMPLETE 0 or 1
     *     RDATE            0+
     *     RECURRENCE-ID    0 or 1 MUST only if referring to one or more
     *                             instances of a recurring calendar
     *                             component. Otherwise it MUST NOT be
     *                             present.
     *     RELATED-TO       0+
     *     RESOURCES        0 or 1 This property MAY contain a list of values
     *     RRULE            0+
     *     PRIORITY         0 or 1
     *     STATUS           0 or 1  If present it MUST be set to "CANCELLED".
     *                              MUST NOT be used if purpose is to remove
     *                              "ATTENDEES" rather than cancel the entire
     *                              VTODO.
     *     URL              0 or 1
     *     X-PROPERTY       0+
     *
     *     REQUEST-STATUS   0
     *
     * VTIMEZONE            0 or 1  MUST be present if any date/time refers to
     *                              a timezone
     * X-COMPONENT          0+
     *
     * VALARM               0
     * VEVENT               0
     * VFREEBUSY            0
     * </pre>
     *
     */
    private class CancelValidator implements Validator<Vote> {

		private static final long serialVersionUID = 1L;

        public void validate(final Vote target) throws ValidationException {
            PropertyValidator.getInstance().assertOne(Property.UID, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.DTSTAMP, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.ORGANIZER, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.SEQUENCE, target.getProperties());

            PropertyValidator.getInstance().assertOneOrLess(Property.CATEGORIES, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CLASS, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.COMMENT, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CREATED, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DESCRIPTION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DTSTART, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DUE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DURATION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.GEO, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.LAST_MODIFIED, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.LOCATION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.PERCENT_COMPLETE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.RECURRENCE_ID, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.RESOURCES, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.PRIORITY, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.STATUS, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.URL, target.getProperties());

            PropertyValidator.getInstance().assertNone(Property.REQUEST_STATUS, target.getProperties());
        }
    }

    private class PollStatusValidator implements Validator<Vote> {

		private static final long serialVersionUID = 1L;

        public void validate(final Vote target) throws ValidationException {
        }
    }

    /**
     * <pre>
     * Component/Property  Presence
     * ------------------- ----------------------------------------------
     * METHOD               1       MUST be "PUBLISH"
     * VTODO                1+
     *     DTSTAMP          1
     *     DTSTART          1
     *     ORGANIZER        1
     *     PRIORITY         1
     *     SEQUENCE         0 or 1  MUST be present if value is greater than
     *                              0, MAY be present if 0
     *     SUMMARY          1       Can be null.
     *     UID              1
     *
     *     ATTACH           0+
     *     CATEGORIES       0 or 1  This property may contain a list of values
     *     CLASS            0 or 1
     *     COMMENT          0 or 1
     *     CONTACT          0+
     *     CREATED          0 or 1
     *     DESCRIPTION      0 or 1  Can be null
     *     DUE              0 or 1  If present DURATION MUST NOT be present
     *     DURATION         0 or 1  If present DUE MUST NOT be present
     *     EXDATE           0+
     *     EXRULE           0+
     *     GEO              0 or 1
     *     LAST-MODIFIED    0 or 1
     *     LOCATION         0 or 1
     *     PERCENT-COMPLETE 0 or 1
     *     RDATE            0+
     *     RECURRENCE-ID    0 or 1  MUST only if referring to an instance of a
     *                              recurring calendar component.  Otherwise
     *                              it MUST NOT be present.
     *
     *     RELATED-TO       0+
     *     RESOURCES        0 or 1  This property may contain a list of values
     *     RRULE            0+
     *     STATUS           0 or 1  MAY be one of COMPLETED/NEEDS ACTION/IN-
     *                              PROCESS/CANCELLED
     *     URL              0 or 1
     *     X-PROPERTY       0+
     *
     *     ATTENDEE         0
     *     REQUEST-STATUS   0
     *
     * VTIMEZONE            0+    MUST be present if any date/time refers to
     *                            a timezone
     * VALARM               0+
     * X-COMPONENT          0+
     *
     * VFREEBUSY            0
     * VEVENT               0
     * VJOURNAL             0
     * </pre>
     *
     */
    private class PublishValidator implements Validator<Vote> {

		private static final long serialVersionUID = 1L;

        public void validate(final Vote target) throws ValidationException {
            PropertyValidator.getInstance().assertOne(Property.DTSTAMP, target.getProperties());

            if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
                PropertyValidator.getInstance().assertOne(Property.ORGANIZER, target.getProperties());
                PropertyValidator.getInstance().assertOne(Property.PRIORITY, target.getProperties());
            }

            PropertyValidator.getInstance().assertOne(Property.SUMMARY, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.UID, target.getProperties());

            // DTSTART: RFC2446 conflicts with RCF2445..
            PropertyValidator.getInstance().assertOneOrLess(Property.DTSTART, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.SEQUENCE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CATEGORIES, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CLASS, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.COMMENT, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CREATED, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DESCRIPTION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DUE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DURATION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.GEO, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.LAST_MODIFIED, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.LOCATION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.PERCENT_COMPLETE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.RECURRENCE_ID, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.RESOURCES, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.STATUS, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.URL, target.getProperties());

            PropertyValidator.getInstance().assertNone(Property.ATTENDEE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.REQUEST_STATUS, target.getProperties());
        }
    }

    /**
     * <pre>
     * Component/Property   Presence
     * -------------------  ---------------------------------------------
     * METHOD               1      MUST be "REFRESH"
     * VTODO                1
     *     ATTENDEE         1
     *     DTSTAMP          1
     *     UID              1       MUST echo original UID
     *
     *     RECURRENCE-ID    0 or 1  MUST only if referring to an instance of a
     *                              Recurring calendar component. Otherwise it
     *                              MUST NOT be present
     *     X-PROPERTY       0+
     *
     *     ATTACH           0
     *     CATEGORIES       0
     *     CLASS            0
     *     COMMENT          0
     *     CONTACT          0
     *     CREATED          0
     *     DESCRIPTION      0
     *     DTSTART          0
     *     DUE              0
     *     DURATION         0
     *     EXDATE           0
     *     EXRULE           0
     *     GEO              0
     *     LAST-MODIFIED    0
     *     LOCATION         0
     *     ORGANIZER        0
     *     PERCENT-COMPLETE 0
     *     PRIORITY         0
     *     RDATE            0
     *     RELATED-TO       0
     *     REQUEST-STATUS   0
     *     RESOURCES        0
     *     RRULE            0
     *     SEQUENCE         0
     *     STATUS           0
     *     URL              0
     *
     * X-COMPONENT          0+
     *
     * VALARM               0
     * VEVENT               0
     * VFREEBUSY            0
     * VTIMEZONE            0
     * </pre>
     *
     */
    private class RefreshValidator implements Validator<Vote> {

		private static final long serialVersionUID = 1L;

        public void validate(final Vote target) throws ValidationException {
            PropertyValidator.getInstance().assertOne(Property.ATTENDEE, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.DTSTAMP, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.UID, target.getProperties());

            PropertyValidator.getInstance().assertOneOrLess(Property.RECURRENCE_ID, target.getProperties());

            PropertyValidator.getInstance().assertNone(Property.ATTACH, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.CATEGORIES, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.CLASS, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.COMMENT, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.CONTACT, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.CREATED, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.DESCRIPTION, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.DTSTART, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.DUE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.DURATION, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.EXDATE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.EXRULE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.GEO, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.LAST_MODIFIED, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.LOCATION, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.ORGANIZER, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.PERCENT_COMPLETE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.PRIORITY, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.RDATE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.RELATED_TO, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.REQUEST_STATUS, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.RESOURCES, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.RRULE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.SEQUENCE, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.STATUS, target.getProperties());
            PropertyValidator.getInstance().assertNone(Property.URL, target.getProperties());
        }
    }

    /**
     * <pre>
     * Component/Property   Presence
     * -------------------  ---------------------------------------------
     * METHOD               1      MUST be "REPLY"
     * VTODO                1+     All component MUST have the same UID
     *     ATTENDEE         1+
     *     DTSTAMP          1
     *     ORGANIZER        1
     *     UID              1      MUST must be the address of the replying
     *                             attendee
     *     REQUEST-STATUS   0+
     *     ATTACH           0+
     *     CATEGORIES       0 or 1 This property may contain a list of values
     *     CLASS            0 or 1
     *     COMMENT          0 or 1
     *     CONTACT          0+
     *     CREATED          0 or 1
     *     DESCRIPTION      0 or 1
     *     DTSTART          0 or 1
     *     DUE              0 or 1  If present DURATION MUST NOT be present
     *     DURATION         0 or 1  If present DUE MUST NOT be present
     *     EXDATE           0+
     *     EXRULE           0+
     *     GEO              0 or 1
     *     LAST-MODIFIED    0 or 1
     *     LOCATION         0 or 1
     *     PERCENT-COMPLETE 0 or 1
     *     PRIORITY         0 or 1
     *     RDATE            0+
     *     RELATED-TO       0+
     *     RESOURCES        0 or 1  This property may contain a list of values
     *     RRULE            0+
     *     RECURRENCE-ID    0 or 1  MUST only if referring to an instance of a
     *                              Recurring calendar component. Otherwise it
     *                              MUST NOT be present
     *     SEQUENCE         0 or 1  MUST be the sequence number of
     *                              the original REQUEST if greater than 0.
     *                              MAY be present if 0.
     *     STATUS           0 or 1
     *     SUMMARY          0 or 1  Can be null
     *     URL              0 or 1
     *     X-PROPERTY       0+
     *
     * VTIMEZONE            0 or 1  MUST be present if any date/time refers to
     *                              a timezone
     * X-COMPONENT          0+
     *
     * VALARM               0
     * VEVENT               0
     * VFREEBUSY            0
     * </pre>
     *
     */
    private class ReplyValidator implements Validator<Vote> {

		private static final long serialVersionUID = 1L;

        public void validate(final Vote target) throws ValidationException {
            PropertyValidator.getInstance().assertOneOrMore(Property.ATTENDEE, target.getProperties());

            PropertyValidator.getInstance().assertOne(Property.DTSTAMP, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.ORGANIZER, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.UID, target.getProperties());

            PropertyValidator.getInstance().assertOneOrLess(Property.CATEGORIES, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CLASS, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.COMMENT, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CREATED, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DESCRIPTION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DTSTART, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DUE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DURATION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.GEO, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.LAST_MODIFIED, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.LOCATION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.PERCENT_COMPLETE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.PRIORITY, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.RESOURCES, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.RECURRENCE_ID, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.SEQUENCE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.STATUS, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.SUMMARY, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.URL, target.getProperties());
        }
    }

    /**
     * <pre>
     * Component/Property  Presence
     * ------------------- ----------------------------------------------
     * METHOD                1       MUST be "REQUEST"
     * VTODO                 1+      All components must have the same UID
     *     ATTENDEE          1+
     *     DTSTAMP           1
     *     DTSTART           1
     *     ORGANIZER         1
     *     PRIORITY          1
     *     SEQUENCE          0 or 1  MUST be present if value is greater than
     *                               0, MAY be present if 0
     *     SUMMARY           1       Can be null.
     *     UID               1
     *
     *     ATTACH            0+
     *     CATEGORIES        0 or 1   This property may contain a list of
     *                                values
     *     CLASS             0 or 1
     *     COMMENT           0 or 1
     *     CONTACT           0+
     *     CREATED           0 or 1
     *     DESCRIPTION       0 or 1  Can be null
     *     DUE               0 or 1  If present DURATION MUST NOT be present
     *     DURATION          0 or 1  If present DUE MUST NOT be present
     *     EXDATE            0+
     *     EXRULE            0+
     *     GEO               0 or 1
     *     LAST-MODIFIED     0 or 1
     *     LOCATION          0 or 1
     *     PERCENT-COMPLETE  0 or 1
     *     RDATE             0+
     *     RECURRENCE-ID     0 or 1  present if referring to an instance of a
     *                               recurring calendar component.  Otherwise
     *                               it MUST NOT be present.
     *     RELATED-TO        0+
     *     RESOURCES         0 or 1  This property may contain a list of
     *                               values
     *     RRULE             0+
     *     STATUS            0 or 1  MAY be one of COMPLETED/NEEDS ACTION/IN-
     *                               PROCESS
     *     URL               0 or 1
     *     X-PROPERTY        0+
     *
     *     REQUEST-STATUS    0
     *
     * VALARM                0+
     *
     * VTIMEZONE             0+  MUST be present if any date/time refers
     *                           to a timezone
     * X-COMPONENT           0+
     *
     * VEVENT                0
     * VFREEBUSY             0
     * VJOURNAL              0
     * </pre>
     *
     */
    private class RequestValidator implements Validator<Vote> {

		private static final long serialVersionUID = 1L;

        public void validate(final Vote target) throws ValidationException {
            PropertyValidator.getInstance().assertOneOrMore(Property.ATTENDEE, target.getProperties());

            PropertyValidator.getInstance().assertOne(Property.DTSTAMP, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.DTSTART, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.ORGANIZER, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.PRIORITY, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.SUMMARY, target.getProperties());
            PropertyValidator.getInstance().assertOne(Property.UID, target.getProperties());

            PropertyValidator.getInstance().assertOneOrLess(Property.SEQUENCE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CATEGORIES, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CLASS, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.COMMENT, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.CREATED, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DESCRIPTION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DUE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.DURATION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.GEO, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.LAST_MODIFIED, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.LOCATION, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.PERCENT_COMPLETE, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.RECURRENCE_ID, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.RESOURCES, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.STATUS, target.getProperties());
            PropertyValidator.getInstance().assertOneOrLess(Property.URL, target.getProperties());

            PropertyValidator.getInstance().assertNone(Property.REQUEST_STATUS, target.getProperties());
        }
    }

    /**
     * @return the optional access classification property
     */
    public final Clazz getClassification() {
        return (Clazz) getProperty(Property.CLASS);
    }

    /**
     * @return the optional date completed property
     */
    public final Completed getDateCompleted() {
        return (Completed) getProperty(Property.COMPLETED);
    }

    /**
     * @return the optional creation-time property
     */
    public final Created getCreated() {
        return (Created) getProperty(Property.CREATED);
    }

    /**
     * @return the optional description property
     */
    public final Description getDescription() {
        return (Description) getProperty(Property.DESCRIPTION);
    }

    /**
     * Convenience method to pull the DTSTART out of the property list.
     * @return The DtStart object representation of the start Date
     */
    public final DtStart getStartDate() {
        return (DtStart) getProperty(Property.DTSTART);
    }

    /**
     * @return the optional geographic position property
     */
    public final Geo getGeographicPos() {
        return (Geo) getProperty(Property.GEO);
    }

    /**
     * @return the optional last-modified property
     */
    public final LastModified getLastModified() {
        return (LastModified) getProperty(Property.LAST_MODIFIED);
    }

    /**
     * @return the optional location property
     */
    public final Location getLocation() {
        return (Location) getProperty(Property.LOCATION);
    }

    /**
     * @return the optional percentage complete property
     */
    public final PercentComplete getPercentComplete() {
        return (PercentComplete) getProperty(Property.PERCENT_COMPLETE);
    }

    /**
     * @return the optional voter property
     */
    public final Voter getVoter() {
        return (Voter) getProperty(Property.VOTER);
    }

    /**
     * @return the optional date-stamp property
     */
    public final DtStamp getDateStamp() {
        return (DtStamp) getProperty(Property.DTSTAMP);
    }

    /**
     * @return the optional sequence number property
     */
    public final Sequence getSequence() {
        return (Sequence) getProperty(Property.SEQUENCE);
    }

    /**
     * @return the optional status property
     */
    public final Status getStatus() {
        return (Status) getProperty(Property.STATUS);
    }

    /**
     * @return the optional summary property
     */
    public final Summary getSummary() {
        return (Summary) getProperty(Property.SUMMARY);
    }

    /**
     * @return the required poll-item-id property
     */
    public final PollItemId getPollItemId() {
        return (PollItemId) getProperty(Property.POLL_ITEM_ID);
    }

    /**
     * @return the optional recurrence identifier property
     */
    public final RecurrenceId getRecurrenceId() {
        return (RecurrenceId) getProperty(Property.RECURRENCE_ID);
    }

    /**
     * Overrides default copy method to add support for copying alarm sub-components.
     * @return a copy of the instance
     * @throws java.text.ParseException where an error occurs parsing data
     * @throws java.io.IOException where an error occurs reading data
     * @throws java.net.URISyntaxException where an invalid URI is encountered
     * @see net.fortuna.ical4j.model.Component#copy()
     */
    public Component copy() throws ParseException, IOException, URISyntaxException {
        final Vote copy = (Vote) super.copy();
        return copy;
    }
}
