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
import net.fortuna.ical4j.model.ComponentContainer;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Completed;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Due;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Geo;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.PercentComplete;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.model.property.immutable.ImmutableMethod;
import net.fortuna.ical4j.model.property.immutable.ImmutableStatus;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.validate.component.VPollValidator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;

import static net.fortuna.ical4j.model.Property.ATTACH;
import static net.fortuna.ical4j.model.Property.ATTENDEE;
import static net.fortuna.ical4j.model.Property.CATEGORIES;
import static net.fortuna.ical4j.model.Property.CLASS;
import static net.fortuna.ical4j.model.Property.CONTACT;
import static net.fortuna.ical4j.model.Property.CREATED;
import static net.fortuna.ical4j.model.Property.DESCRIPTION;
import static net.fortuna.ical4j.model.Property.DTEND;
import static net.fortuna.ical4j.model.Property.DTSTAMP;
import static net.fortuna.ical4j.model.Property.DTSTART;
import static net.fortuna.ical4j.model.Property.DURATION;
import static net.fortuna.ical4j.model.Property.EXDATE;
import static net.fortuna.ical4j.model.Property.EXRULE;
import static net.fortuna.ical4j.model.Property.GEO;
import static net.fortuna.ical4j.model.Property.LAST_MODIFIED;
import static net.fortuna.ical4j.model.Property.LOCATION;
import static net.fortuna.ical4j.model.Property.ORGANIZER;
import static net.fortuna.ical4j.model.Property.PRIORITY;
import static net.fortuna.ical4j.model.Property.RDATE;
import static net.fortuna.ical4j.model.Property.RECURRENCE_ID;
import static net.fortuna.ical4j.model.Property.RELATED_TO;
import static net.fortuna.ical4j.model.Property.REQUEST_STATUS;
import static net.fortuna.ical4j.model.Property.RESOURCES;
import static net.fortuna.ical4j.model.Property.RRULE;
import static net.fortuna.ical4j.model.Property.SEQUENCE;
import static net.fortuna.ical4j.model.Property.STATUS;
import static net.fortuna.ical4j.model.Property.SUMMARY;
import static net.fortuna.ical4j.model.Property.TRANSP;
import static net.fortuna.ical4j.model.Property.UID;
import static net.fortuna.ical4j.model.Property.URL;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.None;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.One;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrMore;

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
public class VPoll extends CalendarComponent implements
        ComponentContainer<Component> {

    private static final long serialVersionUID = -269658210065896668L;

    private static final Map<Method,
            Validator<? extends CalendarComponent>> methodValidators =
            new HashMap<>();
    static {
        methodValidators.put(ImmutableMethod.ADD, new VPollValidator(
                new ValidationRule(One, DTSTAMP, ORGANIZER,
                                   SEQUENCE, SUMMARY, UID),
                new ValidationRule(OneOrLess, CATEGORIES, CLASS,
                                   CREATED, DESCRIPTION, DTEND,
                                   DTSTART, DURATION, GEO, LAST_MODIFIED,
                                   LOCATION, PRIORITY, RESOURCES,
                                   STATUS, TRANSP, URL),
                new ValidationRule(None, RECURRENCE_ID, REQUEST_STATUS)));

        methodValidators.put(ImmutableMethod.CANCEL, new VPollValidator(
                new ValidationRule(
                        One, DTSTAMP, ORGANIZER,
                        SEQUENCE, UID),
                new ValidationRule(
                        OneOrLess, CATEGORIES, CLASS, CREATED,
                        DESCRIPTION, DTEND, DTSTART, DURATION, GEO,
                        LAST_MODIFIED, LOCATION, PRIORITY,
                        RECURRENCE_ID, RESOURCES, STATUS, SUMMARY,
                        TRANSP, URL),
                new ValidationRule(None, REQUEST_STATUS)));

        methodValidators.put(ImmutableMethod.COUNTER, new VPollValidator(
                new ValidationRule(One, DTSTAMP, SEQUENCE,
                                   SUMMARY, UID),
                new ValidationRule(One, true, ORGANIZER),
                new ValidationRule(OneOrLess, CATEGORIES, CLASS,
                                   CREATED, DESCRIPTION, DTEND,
                                   DTSTART, DURATION, GEO, LAST_MODIFIED,
                                   LOCATION, PRIORITY, RECURRENCE_ID,
                                   RESOURCES, STATUS, TRANSP, URL)));

        methodValidators.put(ImmutableMethod.DECLINE_COUNTER, new VPollValidator(
                new ValidationRule(One, DTSTAMP, ORGANIZER, UID),
                new ValidationRule(OneOrLess, RECURRENCE_ID, SEQUENCE),
                new ValidationRule(None, ATTACH, ATTENDEE, CATEGORIES,
                                   CLASS, CONTACT, CREATED,
                                   DESCRIPTION, DTEND, DTSTART,
                                   DURATION, EXDATE, EXRULE, GEO,
                                   LAST_MODIFIED, LOCATION, PRIORITY,
                                   RDATE, RELATED_TO, RESOURCES,
                                   RRULE, STATUS, SUMMARY, TRANSP,
                                   URL)));

        methodValidators.put(ImmutableMethod.PUBLISH, new VPollValidator(
                new ValidationRule(One, DTSTAMP, UID),
                new ValidationRule(One, true, ORGANIZER, SUMMARY),
                new ValidationRule(OneOrLess, RECURRENCE_ID, SEQUENCE,
                                   CATEGORIES, CLASS, CREATED,
                                   DESCRIPTION, DTEND, DTSTART,
                                   DURATION, GEO,
                                   LAST_MODIFIED, LOCATION, PRIORITY,
                                   RESOURCES, STATUS, TRANSP, URL),
                new ValidationRule(None, true, ATTENDEE),
                new ValidationRule(None, REQUEST_STATUS)));

        methodValidators.put(ImmutableMethod.REFRESH, new VPollValidator(
                new ValidationRule(One, ATTENDEE, DTSTAMP, ORGANIZER,
                                   UID),
                new ValidationRule(OneOrLess, RECURRENCE_ID),
                new ValidationRule(None, ATTACH, CATEGORIES, CLASS,
                                   CONTACT, CREATED, DESCRIPTION,
                                   DTEND, DTSTART, DURATION, EXDATE,
                                   EXRULE, GEO, LAST_MODIFIED,
                                   LOCATION, PRIORITY, RDATE,
                                   RELATED_TO, REQUEST_STATUS,
                                   RESOURCES, RRULE, SEQUENCE, STATUS,
                                   SUMMARY, TRANSP, URL)));

        methodValidators.put(ImmutableMethod.REPLY, new VPollValidator(
                new ValidationRule(One, ATTENDEE, DTSTAMP, ORGANIZER,
                                   UID),
                new ValidationRule(OneOrLess, RECURRENCE_ID, SEQUENCE,
                                   CATEGORIES, CLASS, CREATED,
                                   DESCRIPTION, DTEND, DTSTART,
                                   DURATION, GEO, LAST_MODIFIED,
                                   LOCATION, PRIORITY, RESOURCES,
                                   STATUS, SUMMARY, TRANSP, URL)));

        methodValidators.put(ImmutableMethod.REQUEST, new VPollValidator(
                new ValidationRule(OneOrMore, true, ATTENDEE),
                new ValidationRule(One, DTSTAMP, ORGANIZER,
                                   SUMMARY, UID),
                new ValidationRule(OneOrLess, SEQUENCE, CATEGORIES,
                                   CLASS, CREATED, DESCRIPTION, DTEND,
                                   DTSTART, DURATION, GEO,
                                   LAST_MODIFIED, LOCATION, PRIORITY,
                                   RECURRENCE_ID, RESOURCES, STATUS,
                                   TRANSP, URL)));
    }

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
    public VPoll(final PropertyList<Property> properties) {
        super(VPOLL, properties);
    }

    /**
     * Constructor.
     * @param properties a list of properties
     */
    public VPoll(final PropertyList<Property> properties,
                 final ComponentList<Component> subComponents) {
        super(VPOLL, properties);

        subComponents.forEach(this::add);

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
    public VPoll(final Date start,
                 final TemporalAmount duration,
                 final String summary) {
        this();
        getProperties().add(new DtStart(start));
        getProperties().add(new Duration(duration));
        getProperties().add(new Summary(summary));
    }

    public final void add(final Component val) {
        getComponents().add(val);
    }

    /**
     * Returns the list of participants for this poll.
     * @return a component list
     */
    public final ComponentList<Participant> getParticipants() {
        return getComponents().getComponents(PARTICIPANT);
    }

    /**
     * Returns the list of candidates for this poll.
     * @return a component list
     */
    public final ComponentList<Component> getCandidates() {
        final ComponentList<Component> components =
                new ComponentList<>();
        for (final Component c: getComponents()) {
            if (c.getName().equals(PARTICIPANT)) {
                continue;
            }

            if (c.getName().equals(VALARM)) {
                continue;
            }

            components.add(c);
        }
        return components;
    }

    /**
     * Returns the list of alarms for this poll.
     * @return a component list
     */
    public final ComponentList<VAlarm> getAlarms() {
        return getComponents().getComponents(VALARM);
    }

    @Override
    public ComponentList<Component> getComponents() {
        return (ComponentList<Component>) components;
    }

    /**
     * {@inheritDoc}
     */
    public final void validate(final boolean recurse)
            throws ValidationException {

        // validate that getAlarms() only contains VAlarm components
        for (final VAlarm vAlarm : getAlarms()) {
            vAlarm.validate(recurse);
        }

        for (final Participant participant: getParticipants()) {
            participant.validate(recurse);
        }

        if (!CompatibilityHints
                .isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {

            // From "4.8.4.7 Unique Identifier":
            // Conformance: The property MUST be specified in the "VEVENT", "VTODO",
            // "VJOURNAL" or "VFREEBUSY" calendar components.
            PropertyValidator.assertOne(Property.UID,
                    getProperties());

            // From "4.8.7.2 Date/Time Stamp":
            // Conformance: This property MUST be included in the "VEVENT", "VTODO",
            // "VJOURNAL" or "VFREEBUSY" calendar components.
            PropertyValidator.assertOne(Property.DTSTAMP,
                    getProperties());
        }

        /*
         * ; the following are optional, ; but MUST NOT occur more than once class / completed / created / description /
         * dtstamp / dtstart / geo / last-mod / location / organizer / percent / priority / recurid / seq / status /
         * summary / uid / url /
         */
        PropertyValidator.assertOneOrLess(Property.CLASS,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.COMPLETED,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.CREATED,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.DESCRIPTION,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.DTSTAMP,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.DTSTART,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.GEO,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.LAST_MODIFIED,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.LOCATION,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.ORGANIZER,
                getProperties());
        PropertyValidator.assertOneOrLess(
                Property.PERCENT_COMPLETE, getProperties());
        PropertyValidator.assertOneOrLess(Property.PRIORITY,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.RECURRENCE_ID,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.SEQUENCE,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.STATUS,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.SUMMARY,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.UID,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.URL,
                getProperties());

        final Status status = getProperty(Property.STATUS);
        if (status != null && !ImmutableStatus.VTODO_NEEDS_ACTION.getValue().equalsIgnoreCase(status.getValue())
                && !ImmutableStatus.VTODO_COMPLETED.getValue().equalsIgnoreCase(status.getValue())
                && !ImmutableStatus.VTODO_IN_PROCESS.getValue().equalsIgnoreCase(status.getValue())
                && !ImmutableStatus.VTODO_CANCELLED.getValue().equalsIgnoreCase(status.getValue())) {
            throw new ValidationException("Status property ["
                    + status.toString() + "] may not occur in VTODO");
        }

        /*
         * ; either 'due' or 'duration' may appear in ; a 'todoprop', but 'due' and 'duration' ; MUST NOT occur in the
         * same 'todoprop' due / duration /
         */
        try {
            PropertyValidator.assertNone(Property.DUE,
                    getProperties());
        } catch (final ValidationException ve) {
            PropertyValidator.assertNone(Property.DURATION,
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
    protected Validator<CalendarComponent> getValidator(final Method method) {
        return (Validator<CalendarComponent>) methodValidators.get(method);
    }

    /**
     * @return the optional access classification property
     */
    public final Clazz getClassification() {
        return getProperty(Property.CLASS);
    }

    /**
     * @return the optional date completed property
     */
    public final Completed getDateCompleted() {
        return getProperty(Property.COMPLETED);
    }

    /**
     * @return the optional creation-time property
     */
    public final Created getCreated() {
        return getProperty(Property.CREATED);
    }

    /**
     * @return the optional description property
     */
    public final Description getDescription() {
        return getProperty(Property.DESCRIPTION);
    }

    /**
     * Convenience method to pull the DTSTART out of the property list.
     * @return The DtStart object representation of the start Date
     */
    public final DtStart getStartDate() {
        return getProperty(Property.DTSTART);
    }

    /**
     * @return the optional geographic position property
     */
    public final Geo getGeographicPos() {
        return getProperty(Property.GEO);
    }

    /**
     * @return the optional last-modified property
     */
    public final LastModified getLastModified() {
        return getProperty(Property.LAST_MODIFIED);
    }

    /**
     * @return the optional location property
     */
    public final Location getLocation() {
        return getProperty(Property.LOCATION);
    }

    /**
     * @return the optional organizer property
     */
    public final Organizer getOrganizer() {
        return getProperty(Property.ORGANIZER);
    }

    /**
     * @return the optional percentage complete property
     */
    public final PercentComplete getPercentComplete() {
        return getProperty(Property.PERCENT_COMPLETE);
    }

    /**
     * @return the optional priority property
     */
    public final Priority getPriority() {
        return getProperty(Property.PRIORITY);
    }

    /**
     * @return the optional date-stamp property
     */
    public final DtStamp getDateStamp() {
        return getProperty(Property.DTSTAMP);
    }

    /**
     * @return the optional sequence number property
     */
    public final Sequence getSequence() {
        return getProperty(Property.SEQUENCE);
    }

    /**
     * @return the optional status property
     */
    public final Status getStatus() {
        return getProperty(Property.STATUS);
    }

    /**
     * @return the optional summary property
     */
    public final Summary getSummary() {
        return getProperty(Property.SUMMARY);
    }

    /**
     * @return the optional URL property
     */
    public final Url getUrl() {
        return getProperty(Property.URL);
    }

    /**
     * @return the optional recurrence identifier property
     */
    public final RecurrenceId getRecurrenceId() {
        return getProperty(Property.RECURRENCE_ID);
    }

    /**
     * @return the optional Duration property
     */
    public final Duration getDuration() {
        return getProperty(Property.DURATION);
    }

    /**
     * @return the optional due property
     */
    public final Due getDue() {
        return getProperty(Property.DUE);
    }

    /**
     * Returns the UID property of this component if available.
     * @return a Uid instance, or null if no UID property exists
     */
    public final Uid getUid() {
        return getProperty(Property.UID);
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(final Object arg0) {
        if (arg0 instanceof VPoll) {
            return super.equals(arg0)
                    && ObjectUtils.equals(getComponents(),
                                          ((VPoll) arg0).getComponents());
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

    public static class Factory extends Content.Factory implements ComponentFactory<VPoll> {

        public Factory() {
            super(VPOLL);
        }

        @Override
        public VPoll createComponent() {
            return new VPoll();
        }

        @Override
        public VPoll createComponent(PropertyList properties) {
            return new VPoll(properties);
        }

        @Override
        public VPoll createComponent(PropertyList properties, ComponentList subComponents) {
            return new VPoll(properties, subComponents);
        }
    }
}
