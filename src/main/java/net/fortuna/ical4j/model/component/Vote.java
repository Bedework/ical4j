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
import net.fortuna.ical4j.model.property.Comment;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.PollItemId;
import net.fortuna.ical4j.model.property.Response;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;

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
public class Vote extends Component {

    private static final long serialVersionUID = -269658210065896668L;

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
        final StringBuilder buffer = new StringBuilder();
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

    @Override
    public ComponentList<Component> getComponents() {
        return new ComponentList<>();
    }

    /**
     * {@inheritDoc}
     */
    public final void validate(final boolean recurse)
            throws ValidationException {

        if (!CompatibilityHints
                .isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {

          PropertyValidator.assertOne(Property.POLL_ITEM_ID,
                                      getProperties());
        }

        /*
         * ; the following are optional, ; but MUST NOT occur more than once class / completed / created / description /
         * dtstamp / dtstart / geo / last-mod / location / organizer / percent / priority / recurid / seq / status /
         * summary / uid / url /
         */
        PropertyValidator.assertOneOrLess(Property.COMMENT,
                getProperties());
        PropertyValidator.assertOneOrLess(Property.RESPONSE,
                getProperties());

        if (recurse) {
            validateProperties();
        }
    }

    /**
     * @return the optional access classification property
     */
    public final Comment getComment() {
        return getProperty(Property.COMMENT);
    }

    /**
     * @return the optional date completed property
     */
    public final Response getResponse() {
        return getProperty(Property.RESPONSE);
    }

    /**
     * @return the required poll-item-id property
     */
    public final PollItemId getPollItemId() {
        return getProperty(Property.POLL_ITEM_ID);
    }

    public static class Factory extends Content.Factory implements ComponentFactory<Vote> {

        public Factory() {
            super(VOTE);
        }

        @Override
        public Vote createComponent() {
            return new Vote(false);
        }

        @Override
        public Vote createComponent(final PropertyList properties) {
            return new Vote(properties);
        }

        @Override
        public Vote createComponent(final PropertyList properties,
                                    final ComponentList subComponents) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", VOTE));
        }
    }
}
