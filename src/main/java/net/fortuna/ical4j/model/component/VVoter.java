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
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Voter;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * Defines an iCalendar VVOTER component for VPOLL.
 *
 *
 * @author Mike Douglass
 */
public class VVoter extends CalendarComponent {

    private static final long serialVersionUID = 5629679741050917815L;

    private final Validator itipValidator = new ITIPValidator();

    private ComponentList votes;

    /**
     * Default constructor.
     */
    public VVoter() {
        super(VVOTER);
        this.votes = new ComponentList();
    }

    /**
     * Constructs a new instance containing the specified properties.
     * @param properties a list of properties
     */
    public VVoter(final PropertyList properties) {
        super(VVOTER, properties);
        this.votes = new ComponentList();
    }

    /**
     * Constructs a new vtimezone component with no properties and the specified list of type components.
     * @param votes a list of VOTE components
     */
    public VVoter(final ComponentList votes) {
        super(VVOTER);
        this.votes = votes;
    }

    /**
     * Constructor.
     * @param properties a list of properties
     * @param votes a list of VOTE components
     */
    public VVoter(final PropertyList properties,
                  final ComponentList votes) {
        super(VVOTER, properties);
        this.votes = votes;
    }

    /**
     * {@inheritDoc}
     */
    public final String toString() {
        final StringBuffer b = new StringBuffer();
        b.append(BEGIN);
        b.append(':');
        b.append(getName());
        b.append(Strings.LINE_SEPARATOR);
        b.append(getProperties());
        b.append(votes);
        b.append(END);
        b.append(':');
        b.append(getName());
        b.append(Strings.LINE_SEPARATOR);
        return b.toString();
    }

    /**
     * {@inheritDoc}
     */
    public final void validate(final boolean recurse)
            throws ValidationException {
        /*
         * ; 'last-mod' and 'voter' are optional, but MUST NOT occur more than once
         */
        PropertyValidator.getInstance().assertOneOrLess(Property.LAST_MODIFIED,
                getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.VOTER,
                getProperties());

        /*
         * ; the following is optional, ; and MAY occur more than once x-prop
         */

        if (recurse) {
            validateProperties();
        }
    }

    /**
     * {@inheritDoc}
     */
    protected Validator getValidator(Method method) {
        return itipValidator;
    }

    /**
     * Common validation for all iTIP methods.
     *
     * <pre>
     *    Component/Property  Presence
     *    ------------------- ----------------------------------------------
     *    VTIMEZONE           0+      MUST be present if any date/time refers
     *                                to timezone
     *        DAYLIGHT        0+      MUST be one or more of either STANDARD or
     *                                DAYLIGHT
     *           COMMENT      0 or 1
     *           DTSTART      1       MUST be local time format
     *           RDATE        0+      if present RRULE MUST NOT be present
     *           RRULE        0+      if present RDATE MUST NOT be present
     *           TZNAME       0 or 1
     *           TZOFFSET     1
     *           TZOFFSETFROM 1
     *           TZOFFSETTO   1
     *           X-PROPERTY   0+
     *        LAST-MODIFIED   0 or 1
     *        STANDARD        0+      MUST be one or more of either STANDARD or
     *                                DAYLIGHT
     *           COMMENT      0 or 1
     *           DTSTART      1       MUST be local time format
     *           RDATE        0+      if present RRULE MUST NOT be present
     *           RRULE        0+      if present RDATE MUST NOT be present
     *           TZNAME       0 or 1
     *           TZOFFSETFROM 1
     *           TZOFFSETTO   1
     *           X-PROPERTY   0+
     *        TZID            1
     *        TZURL           0 or 1
     *        X-PROPERTY      0+
     * </pre>
     */
    private class ITIPValidator implements Validator<VVoter> {

		private static final long serialVersionUID = 1L;

        /**
         * {@inheritDoc}
         */
        public void validate(final VVoter target) throws ValidationException {
        }
    }

    /**
     * @return Returns the votes.
     */
    public final ComponentList getVotes() {
        return votes;
    }

    /**
     * @return the optional last-modified property
     */
    public final LastModified getLastModified() {
        return (LastModified) getProperty(Property.LAST_MODIFIED);
    }

    /**
     * @return the optional voter property
     */
    public final Voter getVoter() {
        return (Voter) getProperty(Property.VOTER);
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(final Object arg0) {
        if (arg0 instanceof VVoter) {
            return super.equals(arg0)
                    && ObjectUtils.equals(votes, ((VVoter) arg0)
                            .getVotes());
        }
        return super.equals(arg0);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return new HashCodeBuilder().append(getName()).append(getProperties())
                .append(getVotes()).toHashCode();
    }

    /**
     * Overrides default copy method to add support for copying observance sub-components.
     * @return a copy of the instance
     * @throws java.text.ParseException where an error occurs parsing data
     * @throws java.io.IOException where an error occurs reading data
     * @throws java.net.URISyntaxException where an invalid URI is encountered
     * @see net.fortuna.ical4j.model.Component#copy()
     */
    public Component copy() throws ParseException, IOException, URISyntaxException {
        final VVoter copy = (VVoter) super.copy();
        copy.votes = new ComponentList(votes);
        return copy;
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VVoter> {

        public Factory() {
            super(VVOTER);
        }

        @Override
        public VVoter createComponent() {
            return new VVoter();
        }

        @Override
        public VVoter createComponent(PropertyList properties) {
            return new VVoter(properties);
        }

        @Override
        public VVoter createComponent(PropertyList properties, ComponentList subComponents) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", VVOTER));
        }
    }
}
