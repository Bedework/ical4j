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
package net.fortuna.ical4j.model.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import static net.fortuna.ical4j.model.property.immutable.ImmutableRelativeTo.END;
import static net.fortuna.ical4j.model.property.immutable.ImmutableRelativeTo.START;

/**
 * $Id$
 * <p/>
 * Created: [Apr 6, 2004]
 * <p/>
 * Defines a CLASS iCalendar property.
 * <p/>
 * <pre>
 *     4.8.1.3 Classification
 *
 *        Property Name: CLASS
 *
 *        Purpose: This property defines whether a vlocation is related
 *                  to dtstart or dtend.
 *
 *        Value Type: TEXT
 *
 *        Property Parameters: Non-standard property parameters can be
 *        specified on this property.
 *
 *        Conformance: The property can be specified once in a
 *        &quot;VLOCATION&quot; components
 *
 *        Description:
 *
 *        Format Definition: The property is defined by the following notation:
 *
 *          relative-to = &quot;RELATIVE-TO&quot; rtparam &quot;:&quot; rtvalue CRLF
 *
 *          rtparam     = *(&quot;;&quot; xparam)
 *
 *          rtvalue     = &quot;START&quot; / &quot;END&quot; / iana-token
 *                     / x-name
 *          ;No default
 *
 *        Example: The following is an example of this property:
 *
 *          RELATIVE-TO:START
 * </pre>
 *
 * @author Ben Fortuna
 */
public class RelativeTo extends Property {

    private static final long serialVersionUID = 4939943639175551481L;

    private String value;

    /**
     * Default constructor.
     */
    public RelativeTo() {
        super(CLASS, new Factory());
    }

    /**
     * @param aValue a value string for this component
     */
    public RelativeTo(final String aValue) {
        super(CLASS, new Factory());
        this.value = aValue;
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public RelativeTo(final ParameterList aList, final String aValue) {
        super(CLASS, aList, new Factory());
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final String aValue) {
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory implements PropertyFactory<RelativeTo> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(CLASS);
        }

        @Override
        public RelativeTo createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {

            RelativeTo rt;
            if (START.getValue().equals(value)) {
                rt = START;
            }
            else if (END.getValue().equals(value)) {
                rt = END;
            } else {
                rt = new RelativeTo(parameters, value);
            }
            return rt;
        }

        @Override
        public RelativeTo createProperty() {
            return new RelativeTo();
        }
    }

    @Override
    public void validate() throws ValidationException {

    }
}
