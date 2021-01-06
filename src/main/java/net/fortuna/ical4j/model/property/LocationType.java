/*
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
import net.fortuna.ical4j.model.LocationTypeList;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id$
 * <p/>
 * Created: [Apr 6, 2004]
 * <p/>
 * Defines a LOCATION_TYPE iCalendar component property.
 *
 * @author benf
 */
public class LocationType extends Property {
    private static final long serialVersionUID = -3541686430899510312L;

    private LocationTypeList locationTypes;

    /* Location types are defined in RFC4589 */

    public final static String aircraft = "aircraft";
    /* A device that is used or intended to be used for flight in
    the air, such as an airplane, helicopter, gyroplane, glider, or
    lighter-than-air devices like a balloon.
    */

    public final static String airport = "airport";
    /* A place from which aircraft operate, such as an airport or
    heliport.
    */

    public final static String arena = "arena";
    /* Enclosed area used for sports events.
     */

    public final static String automobile = "automobile";
    /* An automotive vehicle, usually four-wheeled, designed
      for passenger transportation, such as a car.
    */

    public final static String bank = "bank";
    /* Business establishment in which money is kept for saving,
    commercial purposes, is invested, supplied for loans, or
    exchanged.
    */

    public final static String bar = "bar";
    /* A bar or saloon.
     */

    public final static String bicycle = "bicycle";
    /* A vehicle with two wheels tandem, a steering handle, a
    saddle seat, and pedals by which it is propelled.
    */

    public final static String bus = "bus";
    /* A large motor vehicle designed to carry passengers.
     */

    public final static String busStation = "bus-station";
    /* Terminal that serves bus passengers, such as a bus
    depot or bus terminal.
    */

    public final static String cafe = "cafe";
    /* Usually a small and informal establishment that serves various
    refreshments (such as coffee); coffee shop.
    */

    public final static String classroom = "classroom";
    /* Academic classroom or lecture hall.
     */

    public final static String club = "club";
    /* Dance club, nightclub, or discotheque.
     */

    public final static String construction = "construction";
    /* Construction site.
     */

    public final static String conventionCenter = "convention-center";
    /* Convention center or exhibition hall.
     */

    public final static String government = "government";
    /* Government building, such as those used by the
    legislative, executive, or judicial branches of governments,
    including court houses, police stations, and military
    installations.
    */

    public final static String hospital = "hospital";
    /* Hospital, hospice, medical clinic, mental institution, or
    doctor's office.
    */

    public final static String hotel = "hotel";
    /* Hotel, motel, inn, or other lodging establishment.
     */

    public final static String industrial = "industrial";
    /* Industrial setting, such as a manufacturing floor or
    power plant.
    */

    public final static String library = "library";
    /* Library or other public place in which literary and
    artistic materials, such as books, music, periodicals, newspapers,
    pamphlets, prints, records, and tapes, are kept for reading,
    reference, or lending.
    */

    public final static String motorcycle = "motorcycle";
    /* A two-wheeled automotive vehicle, including a scooter.
     */

    public final static String office = "office";
    /* Business setting, such as an office.
     */

    public final static String other = "other";
    /* A place without a registered place type representation.
     */

    public final static String outdoors = "outdoors";
    /* Outside a building, in or into the open air, such as a
    park or city streets.
    */

    public final static String parking = "parking";
    /* A parking lot or parking garage.
     */

    public final static String placeOfWorship = "place-of-worship";
    /* A religious site where congregations gather for
    religious observances, such as a church, chapel, meetinghouse,
    mosque, shrine, synagogue, or temple.
    */

    public final static String prison = "prison";
    /* Correctional institution where persons are confined while on
    trial or for punishment, such as a prison, penitentiary, jail,
    brig.
    */

    public final static String publick = "public";
    /* Public area such as a shopping mall, street, park, public
    building, train station, or airport or in public conveyance such
    as a bus, train, plane, or ship.  This general description
    encompasses the more precise descriptors 'street', 'public-
    transport', 'aircraft', 'bus', 'bus-station', 'train', 'train-
    station', 'airport', 'shopping-area', 'outdoors', and
            'watercraft'.
    */

    public final static String publicTransport = "public-transport";
    /* Any form of public transport, including aircraft,
    bus, train, or ship.
    */

    public final static String residence = "residence";
    /* A private or residential setting, not necessarily the
    personal residence of the entity, e.g., including a friend's home.
    */

    public final static String restaurant = "restaurant";
    /* Restaurant, coffee shop, or other public dining
    establishment.
    */

    public final static String school = "school";
    /* School or university property, but not necessarily a
    classroom or library.
    */

    public final static String shoppingArea = "shopping-area";
    /* Shopping mall or shopping area.  This area is a
    large, often enclosed, shopping complex containing various stores,
    businesses, and restaurants usually accessible by common
    passageways.
    */

    public final static String stadium = "stadium";
    /* Large, usually open structure for sports events, including
    a racetrack.
    */

    public final static String store = "store";
    /* Place where merchandise is offered for sale, such as a shop.
     */

    public final static String street = "street";
    /* A public thoroughfare, such as an avenue, street, alley,
    lane, or road, including any sidewalks.
    */

    public final static String theater = "theater";
    /* Theater, lecture hall, auditorium, classroom, movie
    theater, or similar facility designed for presentations, talks,
    plays, music performances, and other events involving an audience.
    */

    public final static String train = "train";
    /* Train, monorail, maglev, cable car, or similar conveyance.
     */

    public final static String trainStation = "train-station";
    /* Terminal where trains load or unload passengers or
    goods; railway station, railroad station, railroad terminal, train
    depot.
    */

    public final static String truck = "truck";
    /* An automotive vehicle suitable for hauling, used primarily to
    carry goods rather than people.
    */

    public final static String underway = "underway";
    /* In a land-, water-, or aircraft that is underway (in motion).
     */

    public final static String unknown = "unknown";
    /* The type of place is unknown.
     */

    public final static String warehouse = "warehouse";
    /* Place in which goods or merchandise are stored, such as a
    storehouse or self-storage facility.
    */

    public final static String water = "water";
    /* In, on, or above bodies of water, such as an ocean, lake,
    river, canal, or other waterway.
    */

    public final static String watercraft = "watercraft";
    /* On a vessel for travel on water such as a boat or ship.
    */

    /**
     * Default constructor.
     */
    public LocationType() {
        super(LOCATION_TYPE, new ParameterList(), new Factory());
        locationTypes = new LocationTypeList();
    }

    /**
     * @param aValue a value string for this component
     */
    public LocationType(final String aValue) {
        super(LOCATION_TYPE, new ParameterList(), new Factory());
        setValue(aValue);
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public LocationType(final ParameterList aList,
                        final String aValue) {
        super(LOCATION_TYPE, aList, new Factory());
        setValue(aValue);
    }

    /**
     * @param cList a list of locationTypes
     */
    public LocationType(final LocationTypeList cList) {
        super(LOCATION_TYPE, new ParameterList(), new Factory());
        locationTypes = cList;
    }

    /**
     * @param aList a list of parameters for this component
     * @param cList a list of locationTypes
     */
    public LocationType(final ParameterList aList, final LocationTypeList cList) {
        super(LOCATION_TYPE, aList, new Factory());
        locationTypes = cList;
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String aValue) {
        locationTypes = new LocationTypeList(aValue);
    }

    /**
     * @return Returns the locationTypes.
     */
    public final LocationTypeList getLocationTypes() {
        return locationTypes;
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return getLocationTypes().toString();
    }

    @Override
    public void validate() throws ValidationException {
    }

    public static class Factory extends Content.Factory
            implements PropertyFactory<Property> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(LOCATION_TYPE);
        }

        public Property createProperty(final ParameterList parameters,
                                       final String value)
                throws IOException, URISyntaxException, ParseException {
            return new LocationType(parameters, value);
        }

        public Property createProperty() {
            return new LocationType();
        }
    }

}
