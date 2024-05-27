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
package net.fortuna.ical4j.util;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.immutable.ImmutableAction;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableClazz;
import net.fortuna.ical4j.model.property.immutable.ImmutableMethod;
import net.fortuna.ical4j.model.property.immutable.ImmutablePriority;
import net.fortuna.ical4j.model.property.immutable.ImmutableStatus;
import net.fortuna.ical4j.model.property.immutable.ImmutableTransp;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;

import static net.fortuna.ical4j.model.property.immutable.ImmutableAction.AUDIO;

/**
 * $Id$
 *
 * Created on 5/07/2005
 *
 * Provides some convenience methods for working with constant
 * parameters and properties.
 * @author Ben Fortuna
 */
public final class Constants {

    /**
     * Constructor made private to enforce static nature.
     */
    private Constants() {
    }
    
    /**
     * Returns a constant equivalent to the specified property
     * if one is applicable. Otherwise will return the specified
     * property.
     * @param property a property instance
     * @return an equivalent constant property, or the specified property if no equivalent
     * constant exists
     */
    public static Property forProperty(final Property property) {
        Property retVal = property;
        if (AUDIO.equals(property)) {
            retVal = AUDIO;
        }
        else if (ImmutableAction.DISPLAY.equals(property)) {
            retVal = ImmutableAction.DISPLAY;
        }
        else if (ImmutableAction.EMAIL.equals(property)) {
            retVal = ImmutableAction.EMAIL;
        }
        else if (ImmutableAction.PROCEDURE.equals(property)) {
            retVal = ImmutableAction.PROCEDURE;
        }
        else if (ImmutableCalScale.GREGORIAN.equals(property)) {
            retVal = ImmutableCalScale.GREGORIAN;
        }
        else if (ImmutableClazz.CONFIDENTIAL.equals(property)) {
            retVal = ImmutableClazz.CONFIDENTIAL;
        }
        else if (ImmutableClazz.PRIVATE.equals(property)) {
            retVal = ImmutableClazz.PRIVATE;
        }
        else if (ImmutableClazz.PUBLIC.equals(property)) {
            retVal = ImmutableClazz.PUBLIC;
        }
        else if (ImmutableMethod.ADD.equals(property)) {
            retVal = ImmutableMethod.ADD;
        }
        else if (ImmutableMethod.CANCEL.equals(property)) {
            retVal = ImmutableMethod.CANCEL;
        }
        else if (ImmutableMethod.COUNTER.equals(property)) {
            retVal = ImmutableMethod.COUNTER;
        }
        else if (ImmutableMethod.DECLINE_COUNTER.equals(property)) {
            retVal = ImmutableMethod.DECLINE_COUNTER;
        }
        else if (ImmutableMethod.PUBLISH.equals(property)) {
            retVal = ImmutableMethod.PUBLISH;
        }
        else if (ImmutableMethod.REFRESH.equals(property)) {
            retVal = ImmutableMethod.REFRESH;
        }
        else if (ImmutableMethod.REPLY.equals(property)) {
            retVal = ImmutableMethod.REPLY;
        }
        else if (ImmutableMethod.REQUEST.equals(property)) {
            retVal = ImmutableMethod.REQUEST;
        }
        else if (ImmutablePriority.HIGH.equals(property)) {
            retVal = ImmutablePriority.HIGH;
        }
        else if (ImmutablePriority.LOW.equals(property)) {
            retVal = ImmutablePriority.LOW;
        }
        else if (ImmutablePriority.MEDIUM.equals(property)) {
            retVal = ImmutablePriority.MEDIUM;
        }
        else if (ImmutablePriority.UNDEFINED.equals(property)) {
            retVal = ImmutablePriority.UNDEFINED;
        }
        else if (ImmutableStatus.VEVENT_CANCELLED.equals(property)) {
            retVal = ImmutableStatus.VEVENT_CANCELLED;
        }
        else if (ImmutableStatus.VEVENT_CONFIRMED.equals(property)) {
            retVal = ImmutableStatus.VEVENT_CONFIRMED;
        }
        else if (ImmutableStatus.VEVENT_TENTATIVE.equals(property)) {
            retVal = ImmutableStatus.VEVENT_TENTATIVE;
        }
        else if (ImmutableStatus.VJOURNAL_CANCELLED.equals(property)) {
            retVal = ImmutableStatus.VJOURNAL_CANCELLED;
        }
        else if (ImmutableStatus.VJOURNAL_DRAFT.equals(property)) {
            retVal = ImmutableStatus.VJOURNAL_DRAFT;
        }
        else if (ImmutableStatus.VJOURNAL_FINAL.equals(property)) {
            retVal = ImmutableStatus.VJOURNAL_FINAL;
        }
        else if (ImmutableStatus.VTODO_CANCELLED.equals(property)) {
            retVal = ImmutableStatus.VTODO_CANCELLED;
        }
        else if (ImmutableStatus.VTODO_COMPLETED.equals(property)) {
            retVal = ImmutableStatus.VTODO_COMPLETED;
        }
        else if (ImmutableStatus.VTODO_IN_PROCESS.equals(property)) {
            retVal = ImmutableStatus.VTODO_IN_PROCESS;
        }
        else if (ImmutableStatus.VTODO_NEEDS_ACTION.equals(property)) {
            retVal = ImmutableStatus.VTODO_NEEDS_ACTION;
        }
        else if (ImmutableTransp.OPAQUE.equals(property)) {
            retVal = ImmutableTransp.OPAQUE;
        }
        else if (ImmutableTransp.TRANSPARENT.equals(property)) {
            retVal = ImmutableTransp.TRANSPARENT;
        }
        else if (ImmutableVersion.VERSION_2_0.equals(property)) {
            retVal = ImmutableVersion.VERSION_2_0;
        }
        return retVal;
    }
}
