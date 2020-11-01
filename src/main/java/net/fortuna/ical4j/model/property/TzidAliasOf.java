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
package net.fortuna.ical4j.model.property;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 *
 * Created: [Mar 19, 2017]
 *
 * Defines a TZID-ALIAS-OF component property as specified in tzdist - rfc7808.
 *
 * <pre>
 *    7.2.  Time Zone Identifier Alias Property
 *   
 *    Property Name:  TZID-ALIAS-OF
 *   
 *    Purpose:  This property specifies a time zone identifier for which
 *    the main time zone identifier is an alias.
 *   
 *    Value Type:  TEXT
 *   
 *    Property Parameters:  IANA and non-standard property parameters can
 *    be specified on this property.
 *   
 *    Conformance:  This property can be specified zero or more times
 *    within &quot;VTIMEZONE&quot; calendar components.
 *   
 *    Description:  When the &quot;VTIMEZONE&quot; component uses a time zone
 *    identifier alias for the &quot;TZID&quot; property value, the &quot;TZID-ALIAS-
 *    OF&quot; property is used to indicate the time zone identifier of the
 *    other time zone (see Section 3.7).
 *   
 *    Format Definition:  This property is defined by the following
 *    notation in ABNF [RFC5234]:
 *   
 *    tzid-alias-of    = &quot;TZID-ALIAS-OF&quot; tzidaliasofparam &quot;:&quot;
 *                        [tzidprefix] text CRLF
 *   
 *    tzidaliasofparam = *(&quot;;&quot; other-param)
 *   
 *                       ;tzidprefix defined in [RFC5545].
 *   
 *    Example:  The following is an example of this property:
 *   
 *    TZID-ALIAS-OF:America/New_York
 * </pre>
 *
 * @author Mike Douglass
 */
public class TzidAliasOf extends Property implements Escapable {

    private static final long serialVersionUID = -522764921502407137L;

    /**
     * Timezone identifier prefix.
     */
    public static final String PREFIX = "/";

    private String value;

    /**
     * Default constructor.
     */
    public TzidAliasOf() {
        super(TZID_ALIAS_OF, new Factory());
    }

    /**
     * @param aValue a value string for this component
     */
    public TzidAliasOf(final String aValue) {
        super(TZID_ALIAS_OF, new Factory());
        setValue(aValue);
    }

    /**
     * @param aList a list of parameters for this component
     * @param aValue a value string for this component
     */
    public TzidAliasOf(final ParameterList aList,
                       final String aValue) {
        super(TZID_ALIAS_OF, aList, new Factory());
        setValue(aValue);
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String aValue) {
        this.value = aValue;
    }

    @Override
    public void validate() throws ValidationException {

    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(TZID_ALIAS_OF);
        }

        public Property createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new TzidAliasOf(parameters, value);
        }

        public Property createProperty() {
            return new TzidAliasOf();
        }
    }
}
