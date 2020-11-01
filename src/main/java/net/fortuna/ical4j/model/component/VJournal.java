/**
 * Copyright (c) 2012, Ben Fortuna
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
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ComponentValidator;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static net.fortuna.ical4j.model.Property.ATTENDEE;
import static net.fortuna.ical4j.model.Property.CATEGORIES;
import static net.fortuna.ical4j.model.Property.CLASS;
import static net.fortuna.ical4j.model.Property.CREATED;
import static net.fortuna.ical4j.model.Property.DESCRIPTION;
import static net.fortuna.ical4j.model.Property.DTSTAMP;
import static net.fortuna.ical4j.model.Property.DTSTART;
import static net.fortuna.ical4j.model.Property.LAST_MODIFIED;
import static net.fortuna.ical4j.model.Property.ORGANIZER;
import static net.fortuna.ical4j.model.Property.RECURRENCE_ID;
import static net.fortuna.ical4j.model.Property.REQUEST_STATUS;
import static net.fortuna.ical4j.model.Property.SEQUENCE;
import static net.fortuna.ical4j.model.Property.STATUS;
import static net.fortuna.ical4j.model.Property.SUMMARY;
import static net.fortuna.ical4j.model.Property.UID;
import static net.fortuna.ical4j.model.Property.URL;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.None;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.One;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;

/**
 * $Id$ [Apr 5, 2004]
 *
 * Defines an iCalendar VJOURNAL component.
 *
 * <pre>
 *    4.6.3 Journal Component
 *
 *       Component Name: VJOURNAL
 *
 *       Purpose: Provide a grouping of component properties that describe a
 *       journal entry.
 *
 *       Formal Definition: A &quot;VJOURNAL&quot; calendar component is defined by the
 *       following notation:
 *
 *         journalc   = &quot;BEGIN&quot; &quot;:&quot; &quot;VJOURNAL&quot; CRLF
 *                      jourprop
 *                      &quot;END&quot; &quot;:&quot; &quot;VJOURNAL&quot; CRLF
 *
 *         jourprop   = *(
 *
 *                    ; the following are optional,
 *                    ; but MUST NOT occur more than once
 *
 *                    class / created / description / dtstart / dtstamp /
 *                    last-mod / organizer / recurid / seq / status /
 *                    summary / uid / url /
 *
 *                    ; the following are optional,
 *                    ; and MAY occur more than once
 *
 *                    attach / attendee / categories / comment /
 *                    contact / exdate / exrule / related / rdate /
 *                    rrule / rstatus / x-prop
 *
 *                    )
 * </pre>
 *
 * Example 1 - Creating a journal associated with an event:
 *
 * <pre><code>
 * DtStart meetingDate = (DtStart) meeting.getProperties().getProperty(
 *         Property.DTSTART);
 *
 * VJournal minutes = new VJournal(meetingDate.getTime(),
 *         &quot;Progress Meeting - Minutes&quot;);
 *
 * // add timezone information..
 * TzId tzParam = meetingDate.getParameters().getParmaeter(Parameter.TZID);
 * minutes.getProperties().getProperty(Property.DTSTART).getParameters().add(
 *         tzParam);
 *
 * // add description..
 * minutes.getProperties().add(new Description(&quot;1. Agenda.., 2. Action Items..&quot;));
 * </code></pre>
 *
 * @author Ben Fortuna
 */
public class VJournal extends CalendarComponent {

    private static final long serialVersionUID = -7635140949183238830L;

    private final Map<Method, Validator> methodValidators = new HashMap<Method, Validator>();
    {
        methodValidators.put(Method.ADD, new ComponentValidator<VJournal>(new ValidationRule(One, DESCRIPTION, DTSTAMP, DTSTART, ORGANIZER, SEQUENCE, UID),
                                                                          new ValidationRule(OneOrLess, CATEGORIES, CLASS, CREATED, LAST_MODIFIED, STATUS, SUMMARY, URL),
                                                                          new ValidationRule(None, ATTENDEE, RECURRENCE_ID)));
        methodValidators.put(Method.CANCEL, new ComponentValidator(new ValidationRule(One, DTSTAMP, ORGANIZER, SEQUENCE, UID),
                new ValidationRule(OneOrLess, CATEGORIES, CLASS, CREATED, DESCRIPTION, DTSTART, LAST_MODIFIED,
                        RECURRENCE_ID, STATUS, SUMMARY, URL),
                new ValidationRule(None, REQUEST_STATUS)));
        methodValidators.put(Method.PUBLISH, new ComponentValidator(new ValidationRule(One, DESCRIPTION, DTSTAMP, DTSTART, ORGANIZER, UID),
                new ValidationRule(OneOrLess, CATEGORIES, CLASS, CREATED, LAST_MODIFIED, RECURRENCE_ID, SEQUENCE, STATUS,
                        SUMMARY, URL),
                new ValidationRule(None, ATTENDEE)));
    }
    
    /**
     * Default constructor.
     */
    public VJournal() {
        this(true);
    }

    public VJournal(boolean initialise) {
        super(VJOURNAL);
        if (initialise) {
            getProperties().add(new DtStamp());
        }
    }

    /**
     * Constructor.
     * @param properties a list of properties
     */
    public VJournal(final PropertyList properties) {
        super(VJOURNAL, properties);
    }

    /**
     * Constructs a new VJOURNAL instance associated with the specified time with the specified summary.
     * @param start the date the journal entry is associated with
     * @param summary the journal summary
     */
    public VJournal(final Date start, final String summary) {
        this();
        getProperties().add(new DtStart(start));
        getProperties().add(new Summary(summary));
    }

    @Override
    public ComponentList<Component> getComponents() {
        return new ComponentList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void validate(final boolean recurse)
            throws ValidationException {

        if (!CompatibilityHints
                .isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {

            // From "4.8.4.7 Unique Identifier":
            // Conformance: The property MUST be specified in the "VEVENT", "VTODO",
            // "VJOURNAL" or "VFREEBUSY" calendar components.
            PropertyValidator.assertOne(UID,
                                        getProperties());

            // From "4.8.7.2 Date/Time Stamp":
            // Conformance: This property MUST be included in the "VEVENT", "VTODO",
            // "VJOURNAL" or "VFREEBUSY" calendar components.
            PropertyValidator.assertOne(DTSTAMP,
                                        getProperties());
        }

        /*
         * ; the following are optional, ; but MUST NOT occur more than once class / created / description / dtstart /
         * dtstamp / last-mod / organizer / recurid / seq / status / summary / uid / url /
         */
        Arrays.asList(CLASS, CREATED, DESCRIPTION, DTSTART,
                      DTSTAMP, LAST_MODIFIED, ORGANIZER, RECURRENCE_ID, SEQUENCE,
                      STATUS, SUMMARY, UID, URL).forEach(property -> PropertyValidator.assertOneOrLess(property, getProperties()));

        final Status status = getProperty(STATUS);
        if (status != null && !Status.VJOURNAL_DRAFT.getValue().equals(status.getValue())
                && !Status.VJOURNAL_FINAL.getValue().equals(status.getValue())
                && !Status.VJOURNAL_CANCELLED.getValue().equals(status.getValue())) {
            throw new ValidationException("Status property ["
                    + status.toString() + "] may not occur in VJOURNAL");
        }

        /*
         * ; the following are optional, ; and MAY occur more than once attach / attendee / categories / comment /
         * contact / exdate / exrule / related / rdate / rrule / rstatus / x-prop
         */

        if (recurse) {
            validateProperties();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Validator getValidator(Method method) {
        return methodValidators.get(method);
    }

    /**
     * @return the optional access classification property for a journal entry
     */
    public final Clazz getClassification() {
        return getProperty(CLASS);
    }

    /**
     * @return the optional creation-time property for a journal entry
     */
    public final Created getCreated() {
        return getProperty(CREATED);
    }

    /**
     * @return the optional description property for a journal entry
     */
    public final Description getDescription() {
        return getProperty(DESCRIPTION);
    }

    /**
     * Convenience method to pull the DTSTART out of the property list.
     * @return The DtStart object representation of the start Date
     */
    public final DtStart getStartDate() {
        return getProperty(DTSTART);
    }

    /**
     * @return the optional last-modified property for a journal entry
     */
    public final LastModified getLastModified() {
        return getProperty(LAST_MODIFIED);
    }

    /**
     * @return the optional organizer property for a journal entry
     */
    public final Organizer getOrganizer() {
        return getProperty(ORGANIZER);
    }

    /**
     * @return the optional date-stamp property
     */
    public final DtStamp getDateStamp() {
        return getProperty(DTSTAMP);
    }

    /**
     * @return the optional sequence number property for a journal entry
     */
    public final Sequence getSequence() {
        return getProperty(SEQUENCE);
    }

    /**
     * @return the optional status property for a journal entry
     */
    public final Status getStatus() {
        return getProperty(STATUS);
    }

    /**
     * @return the optional summary property for a journal entry
     */
    public final Summary getSummary() {
        return getProperty(SUMMARY);
    }

    /**
     * @return the optional URL property for a journal entry
     */
    public final Url getUrl() {
        return getProperty(URL);
    }

    /**
     * @return the optional recurrence identifier property for a journal entry
     */
    public final RecurrenceId getRecurrenceId() {
        return getProperty(RECURRENCE_ID);
    }

    /**
     * Returns the UID property of this component if available.
     * @return a Uid instance, or null if no UID property exists
     */
    public final Uid getUid() {
        return getProperty(UID);
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VJournal> {

        public Factory() {
            super(VJOURNAL);
        }

        @Override
        public VJournal createComponent() {
            return new VJournal(false);
        }

        @Override
        public VJournal createComponent(PropertyList properties) {
            return new VJournal(properties);
        }

        @Override
        public VJournal createComponent(PropertyList properties, ComponentList subComponents) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", VJOURNAL));
        }
    }
}
