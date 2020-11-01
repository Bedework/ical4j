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
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attach;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.ParticipantType;
import net.fortuna.ical4j.model.property.Repeat;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;

import static net.fortuna.ical4j.model.Property.PARTICIPANT_TYPE;

/**
 * $Id$ [May 1 2017]
 *
 * Defines an iCalendar PARTICIPANT component.
 *
 * <pre>
 Component name:  PARTICIPANT

 Purpose:  This component provides information about a participant in
 an event or optionally a plain text typed value.

 Conformance:  This component MAY be appear in any iCalendar
 component.

 Description:  This component provides information about an
 participant in an event, task or poll.  A participant may be an
 attendee in a scheduling sense and the ATTENDEE property may be
 specified in addition.  Participants in events can be individuals
 or organizations, for example a soccer team, the spectators, or
 the musicians.

 The SOURCE property if present may refer to an external definition
 of the participant - such as a vcard.

 The STRUCTURED-ADDRESS property if present will provide a cal-
 address.  If an ATTENDEE property has the same value the
 participant is considered schedulable.  The PARTICIPANT component
 can be used to contain additional meta-data related to the
 attendee.

 Format Definition:

 This property is defined by the following notation:


 participantc  = "BEGIN" ":" "PARTICIPANT" CRLF
                     partprop *alarmc
                     "END" ":" "PARTICIPANT" CRLF

 partprop      = *(
                 ;
                 ; The following are REQUIRED,
                 ; but MUST NOT occur more than once.
                 ;
                 dtstamp  / participanttype /
                 ;
                 ; The following are OPTIONAL,
                 ; but MUST NOT occur more than once.
                 ;
                 created / description / last-mod / seq /
                 source / status / structuredaddress / summary / url /
                 ;
                 ; The following are OPTIONAL,
                 ; and MAY occur more than once.
                 ;
                 attach / categories / comment /
                 contact / rstatus / related /
                 resources / x-prop / iana-prop
                 ;
                 )


 Note:  When the PRIORITY is supplied it defines the ordering of
 PARTICIPANT components with the same value for the TYPE parameter.
 * </pre>
 *
 * @author Ben Fortuna
 */
public class Participant extends Component {

    private static final long serialVersionUID = -8193965477414653802L;
    
    private ComponentList components = new ComponentList();

    /**
     * Default constructor.
     */
    public Participant() {
        super(PARTICIPANT);
    }

    /**
     * Constructor.
     * @param properties a list of properties
     */
    public Participant(final PropertyList properties) {
        super(PARTICIPANT, properties);
    }

    public ParticipantType getParticipantType() {
        return getProperty(PARTICIPANT_TYPE);
    }

    public ComponentList getComponents() {
        return components;
    }

    /**
     * {@inheritDoc}
     */
    public final void validate(final boolean recurse)
            throws ValidationException {

        /*
         * ; 'dtstamp' and participanttype' are both REQUIRED, 
         * ; but MUST NOT occur more than once 
         */
        PropertyValidator.assertOne(Property.DTSTAMP, getProperties());
        PropertyValidator.assertOne(PARTICIPANT_TYPE, getProperties());

        PropertyValidator.assertOneOrLess(Property.CALENDAR_ADDRESS, getProperties());
        PropertyValidator.assertOneOrLess(Property.DURATION, getProperties());
        PropertyValidator.assertOneOrLess(Property.SOURCE, getProperties());
        PropertyValidator.assertOneOrLess(Property.STATUS, getProperties());
        PropertyValidator.assertOneOrLess(Property.SUMMARY, getProperties());
        PropertyValidator.assertOneOrLess(Property.URL, getProperties());
        
        
        if (recurse) {
            validateProperties();
        }
    }

    /**
     * Returns the mandatory action property.
     * @return the ACTION property or null if not specified
     */
    public final Action getAction() {
        return getProperty(Property.ACTION);
    }

    /**
     * Returns the mandatory trigger property.
     * @return the TRIGGER property or null if not specified
     */
    public final Trigger getTrigger() {
        return getProperty(Property.TRIGGER);
    }

    /**
     * Returns the optional duration property.
     * @return the DURATION property or null if not specified
     */
    public final Duration getDuration() {
        return getProperty(Property.DURATION);
    }

    /**
     * Returns the optional repeat property.
     * @return the REPEAT property or null if not specified
     */
    public final Repeat getRepeat() {
        return getProperty(Property.REPEAT);
    }

    /**
     * Returns the optional attachment property.
     * @return the ATTACH property or null if not specified
     */
    public final Attach getAttachment() {
        return getProperty(Property.ATTACH);
    }

    /**
     * Returns the optional description property.
     * @return the DESCRIPTION property or null if not specified
     */
    public final Description getDescription() {
        return getProperty(Property.DESCRIPTION);
    }

    /**
     * Returns the optional summary property.
     * @return the SUMMARY property or null if not specified
     */
    public final Summary getSummary() {
        return getProperty(Property.SUMMARY);
    }

    public static class Factory extends Content.Factory implements ComponentFactory<Participant> {

        public Factory() {
            super(PARTICIPANT);
        }

        @Override
        public Participant createComponent() {
            return new Participant();
        }

        @Override
        public Participant createComponent(PropertyList properties) {
            return new Participant(properties);
        }

        @Override
        public Participant createComponent(PropertyList properties, ComponentList subComponents) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", PARTICIPANT));
        }
    }
}
