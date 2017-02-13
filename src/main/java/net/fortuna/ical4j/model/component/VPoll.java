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

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.validate.component.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * Defines an iCalendar VPOLL component.
 *
 * <pre>
 * </pre>
 *
 * @author Ben Fortuna
 * @author Mike Douglass
 */
public class VPoll extends CalendarComponent {

    private static final long serialVersionUID = -269658210065896668L;

    private final Map<Method, Validator> methodValidators = new HashMap<Method, Validator>();
    {
        methodValidators.put(Method.CANCEL, new VPollCancelValidator());
        methodValidators.put(Method.CONFIRM, new VPollRequestValidator());
        methodValidators.put(Method.POLLSTATUS, new VPollPollStatusValidator());
        methodValidators.put(Method.PUBLISH, new VPollPublishValidator());
        methodValidators.put(Method.REFRESH, new VPollRefreshValidator());
        methodValidators.put(Method.REPLY, new VPollReplyValidator());
        methodValidators.put(Method.REQUEST, new VPollRequestValidator());
    }

    private ComponentList<VVoter> voters = new ComponentList<VVoter>();

    private ComponentList candidates = new ComponentList();

    private ComponentList<VAlarm> alarms = new ComponentList<VAlarm>();

    /**
     * Default constructor.
     */
    public VPoll() {
        super(VPOLL);
        getProperties().add(new DtStamp());
    }

    /**
     * Constructor.
     * @param properties a list of properties
     */
    public VPoll(final PropertyList properties) {
        super(VPOLL, properties);
    }

    /**
     * Constructs a new VPOLL instance starting at the specified time with the
     * specified summary.
     * @param start the start date of the new poll
     * @param summary the poll summary
     */
    public VPoll(final Date start, final String summary) {
        this();
        getProperties().add(new DtStart(start));
        getProperties().add(new Summary(summary));
    }

    /**
     * Constructs a new VPOLL instance starting and ending at the specified times with the specified summary.
     * @param start the start date of the new poll
     * @param end the end date of the new poll
     * @param summary the summary
     */
    public VPoll(final Date start, final Date end, final String summary) {
        this();
        getProperties().add(new DtStart(start));
        getProperties().add(new DtEnd(end));
        getProperties().add(new Summary(summary));
    }

    /**
     * Constructs a new VPOLL instance starting at the specified times, for the specified duration, with the specified
     * summary.
     * @param start the start date of the new poll
     * @param duration the duration of the new poll
     * @param summary the poll summary
     */
    public VPoll(final Date start, final Dur duration, final String summary) {
        this();
        getProperties().add(new DtStart(start));
        getProperties().add(new Duration(duration));
        getProperties().add(new Summary(summary));
    }

    /**
     * Returns the list of voters for this poll.
     * @return a component list
     */
    public final ComponentList<VVoter> getVoters() {
        return voters;
    }

    /**
     * Returns the list of candidates for this poll.
     * @return a component list
     */
    public final ComponentList getCandidates() {
        return candidates;
    }

    /**
     * Returns the list of alarms for this poll.
     * @return a component list
     */
    public final ComponentList<VAlarm> getAlarms() {
        return alarms;
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
        buffer.append(getAlarms());
        buffer.append(getVoters());
        buffer.append(getCandidates());
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

        // validate that getAlarms() only contains VAlarm components
        final Iterator iterator = getAlarms().iterator();
        while (iterator.hasNext()) {
            final Component component = (Component) iterator.next();
            if (component instanceof VAlarm) {
                ((VAlarm)component).validate(recurse);
                continue;
            }
            if (component instanceof VVoter) {
                ((VVoter)component).validate(recurse);
                continue;
            }
                throw new ValidationException("Component ["
                        + component.getName() + "] may not occur in VPOLL");
            }

        if (!CompatibilityHints
                .isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {

            // From "4.8.4.7 Unique Identifier":
            // Conformance: The property MUST be specified in the "VEVENT", "VTODO",
            // "VJOURNAL" or "VFREEBUSY" calendar components.
            PropertyValidator.getInstance().assertOne(Property.UID,
                    getProperties());

            // From "4.8.7.2 Date/Time Stamp":
            // Conformance: This property MUST be included in the "VEVENT", "VTODO",
            // "VJOURNAL" or "VFREEBUSY" calendar components.
            PropertyValidator.getInstance().assertOne(Property.DTSTAMP,
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
     * @return the optional organizer property
     */
    public final Organizer getOrganizer() {
        return (Organizer) getProperty(Property.ORGANIZER);
    }

    /**
     * @return the optional percentage complete property
     */
    public final PercentComplete getPercentComplete() {
        return (PercentComplete) getProperty(Property.PERCENT_COMPLETE);
    }

    /**
     * @return the optional priority property
     */
    public final Priority getPriority() {
        return (Priority) getProperty(Property.PRIORITY);
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
     * @return the optional URL property
     */
    public final Url getUrl() {
        return (Url) getProperty(Property.URL);
    }

    /**
     * @return the optional recurrence identifier property
     */
    public final RecurrenceId getRecurrenceId() {
        return (RecurrenceId) getProperty(Property.RECURRENCE_ID);
    }

    /**
     * @return the optional Duration property
     */
    public final Duration getDuration() {
        return (Duration) getProperty(Property.DURATION);
    }

    /**
     * @return the optional due property
     */
    public final Due getDue() {
        return (Due) getProperty(Property.DUE);
    }

    /**
     * Returns the UID property of this component if available.
     * @return a Uid instance, or null if no UID property exists
     */
    public final Uid getUid() {
        return (Uid) getProperty(Property.UID);
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(final Object arg0) {
        if (arg0 instanceof VPoll) {
            return super.equals(arg0)
                    && ObjectUtils.equals(alarms, ((VPoll) arg0).getAlarms());
        }
        return super.equals(arg0);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return new HashCodeBuilder().append(getName()).append(getProperties())
                .append(getAlarms()).toHashCode();
    }

    /**
     * Overrides default copy method to add support for copying alarm sub-components.
     * @return a copy of the instance
     * @throws ParseException where an error occurs parsing data
     * @throws IOException where an error occurs reading data
     * @throws URISyntaxException where an invalid URI is encountered
     * @see net.fortuna.ical4j.model.Component#copy()
     */
    public Component copy() throws ParseException, IOException, URISyntaxException {
        final VPoll copy = (VPoll) super.copy();
        copy.voters = new ComponentList(voters);
        copy.candidates = new ComponentList(candidates);
        copy.alarms = new ComponentList(alarms);
        return copy;
    }
}
