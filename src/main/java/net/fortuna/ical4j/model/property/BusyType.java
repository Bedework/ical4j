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

import static net.fortuna.ical4j.model.property.immutable.ImmutableBusyType.BUSY;
import static net.fortuna.ical4j.model.property.immutable.ImmutableBusyType.BUSY_TENTATIVE;
import static net.fortuna.ical4j.model.property.immutable.ImmutableBusyType.BUSY_UNAVAILABLE;

/**
 * $Id$
 *
 * Created: [Apr 6, 2004]
 *
 * Defines a BUSYTYPE iCalendar component property.
 *
 *    Format Definition:  This property is defined by the following
 *    notation:
 *
 *      busytype      = "BUSYTYPE" busytypeparam ":" busytypevalue CRLF
 *
 *      busytypeparam = *(";" xparam)
 *
 *      busytypevalue = "BUSY" / "BUSY-UNAVAILABLE" /
 *                      "BUSY-TENTATIVE" / iana-token / x-name
 *                      ; Default is "BUSY-UNAVAILABLE"
 *
 * @author Ben Fortuna
 * @author Mike Douglass
 */
public class BusyType extends Property {

    private static final long serialVersionUID = -5140360270562621159L;

    public static final String VALUE_BUSY = "BUSY";
    public static final String VALUE_BUSY_UNAVAILABLE = "BUSY-UNAVAILABLE";
    public static final String VALUE_BUSY_TENTATIVE = "BUSY-TENTATIVE";

    private String value;

    /**
     * Default constructor.
     */
    public BusyType() {
        super(BUSYTYPE, new Factory());
    }

    /**
     * @param aValue a value string for this component
     */
    public BusyType(final String aValue) {
        super(BUSYTYPE, new Factory());
        this.value = aValue;
    }

    /**
     * @param aList a list of parameters for this component
     * @param aValue a value string for this component
     */
    public BusyType(final ParameterList aList, final String aValue) {
        super(BUSYTYPE, aList, new Factory());
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

    @Override
    public void validate() throws ValidationException {

    }

    public static class Factory extends Content.Factory implements PropertyFactory<BusyType> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(BUSYTYPE);
        }

        @Override
        public BusyType createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {

            BusyType busyType;
            if (BUSY.getValue().equals(value)) {
                busyType = BUSY;
            } else if (BUSY_TENTATIVE.getValue().equals(value)) {
                busyType = BUSY_TENTATIVE;
            } else if (BUSY_UNAVAILABLE.getValue().equals(value)) {
                busyType = BUSY_UNAVAILABLE;
            } else {
                busyType = new BusyType(parameters, value);
            }
            return busyType;
        }

        @Override
        public BusyType createProperty() {
            return new BusyType();
        }
    }

}
