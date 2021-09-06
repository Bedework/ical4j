package net.fortuna.ical4j.data;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.property.AcceptResponse;
import net.fortuna.ical4j.model.property.Acknowledged;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attach;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.BusyType;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.CalendarAddress;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Color;
import net.fortuna.ical4j.model.property.Comment;
import net.fortuna.ical4j.model.property.Completed;
import net.fortuna.ical4j.model.property.Concept;
import net.fortuna.ical4j.model.property.Conference;
import net.fortuna.ical4j.model.property.Contact;
import net.fortuna.ical4j.model.property.Country;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Due;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.ExDate;
import net.fortuna.ical4j.model.property.ExRule;
import net.fortuna.ical4j.model.property.ExtendedAddress;
import net.fortuna.ical4j.model.property.FreeBusy;
import net.fortuna.ical4j.model.property.Geo;
import net.fortuna.ical4j.model.property.Image;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Link;
import net.fortuna.ical4j.model.property.Locality;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.LocationType;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Name;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ParticipantType;
import net.fortuna.ical4j.model.property.PercentComplete;
import net.fortuna.ical4j.model.property.PollCompletion;
import net.fortuna.ical4j.model.property.PollItemId;
import net.fortuna.ical4j.model.property.PollMode;
import net.fortuna.ical4j.model.property.PollProperties;
import net.fortuna.ical4j.model.property.PollWinner;
import net.fortuna.ical4j.model.property.Postalcode;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RDate;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.RefreshInterval;
import net.fortuna.ical4j.model.property.Region;
import net.fortuna.ical4j.model.property.RelatedTo;
import net.fortuna.ical4j.model.property.Repeat;
import net.fortuna.ical4j.model.property.ReplyUrl;
import net.fortuna.ical4j.model.property.RequestStatus;
import net.fortuna.ical4j.model.property.ResourceType;
import net.fortuna.ical4j.model.property.Resources;
import net.fortuna.ical4j.model.property.Response;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Source;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.StreetAddress;
import net.fortuna.ical4j.model.property.StructuredData;
import net.fortuna.ical4j.model.property.StyledDescription;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Tel;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.model.property.TzId;
import net.fortuna.ical4j.model.property.TzName;
import net.fortuna.ical4j.model.property.TzOffsetFrom;
import net.fortuna.ical4j.model.property.TzOffsetTo;
import net.fortuna.ical4j.model.property.TzUntil;
import net.fortuna.ical4j.model.property.TzUrl;
import net.fortuna.ical4j.model.property.TzidAliasOf;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.model.property.Version;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class DefaultPropertyFactorySupplier implements Supplier<List<PropertyFactory<? extends Property>>> {

    @Override
    public List<PropertyFactory<? extends Property>> get() {
        List<PropertyFactory<? extends Property>> rfc5545 =
                Arrays.asList(
                        new Acknowledged.Factory(),
                        new Action.Factory(),
                        new Attach.Factory(),
                        new Attendee.Factory(),
                        new CalScale.Factory(),
                        new Categories.Factory(),
                        new Clazz.Factory(),
                        new Comment.Factory(),
                        new Completed.Factory(),
                        new Contact.Factory(),
                        new Created.Factory(),
                        new Description.Factory(),
                        new DtEnd.Factory(),
                        new DtStamp.Factory(),
                        new DtStart.Factory(),
                        new Due.Factory(),
                        new Duration.Factory(),
                        new ExDate.Factory(),
                        new ExRule.Factory(),
                        new FreeBusy.Factory(),
                        new Geo.Factory(),
                        new LastModified.Factory(),
                        new Location.Factory(),
                        new Method.Factory(),
                        new Organizer.Factory(),
                        new PercentComplete.Factory(),
                        new Priority.Factory(),
                        new ProdId.Factory(),
                        new RDate.Factory(),
                        new RecurrenceId.Factory(),
                        new RelatedTo.Factory(),
                        new Repeat.Factory(),
                        new RequestStatus.Factory(),
                        new Resources.Factory(),
                        new RRule.Factory(),
                        new Sequence.Factory(),
                        new Status.Factory(),
                        new Summary.Factory(),
                        new Transp.Factory(),
                        new Trigger.Factory(),
                        new TzId.Factory(),
                        new TzName.Factory(),
                        new TzOffsetFrom.Factory(),
                        new TzOffsetTo.Factory(),
                        new TzUrl.Factory(),
                        new Uid.Factory(),
                        new Url.Factory(),
                        new Version.Factory());

        // Time Zone Data Distribution Service
        List<PropertyFactory<? extends Property>> rfc7808 =
                Arrays.asList(
                        new TzidAliasOf.Factory(),
                        new TzUntil.Factory());

        // New properties
        List<PropertyFactory<? extends Property>> rfc7986 =
                Arrays.asList(
                        new Color.Factory(),
                        new Conference.Factory(),
                        new Image.Factory(),
                        new Name.Factory(),
                        new RefreshInterval.Factory(),
                        new Source.Factory());

        List<PropertyFactory<? extends Property>> vvenue =
                Arrays.asList(
                        new Country.Factory(),
                        new ExtendedAddress.Factory(),
                        new Locality.Factory(),
                        new Postalcode.Factory(),
                        new Region.Factory(),
                        new StreetAddress.Factory(),
                        new Tel.Factory()
                );

        final List<PropertyFactory<? extends Property>> vpollDraft =
                Arrays.asList(
                        new AcceptResponse.Factory(),
                        new PollCompletion.Factory(),
                        new PollItemId.Factory(),
                        new PollMode.Factory(),
                        new PollProperties.Factory(),
                        new PollWinner.Factory(),
                        new ReplyUrl.Factory(),
                        new Response.Factory());

        final List<PropertyFactory<? extends Property>> relationsDraft =
                Arrays.asList(
                        new Concept.Factory(),
                        new Link.Factory());

        // Availability
        final List<PropertyFactory<? extends Property>> rfc7953 =
                Collections.singletonList(new BusyType.Factory());

        // Event pub
        final List<PropertyFactory<? extends Property>> rfc9073 =
                Arrays.asList(
                        new CalendarAddress.Factory(),
                        new LocationType.Factory(),
                        new ParticipantType.Factory(),
                        new ResourceType.Factory(),
                        new StructuredData.Factory(),
                        new StyledDescription.Factory());

        final List<PropertyFactory<? extends Property>> factories =
                new ArrayList<>(rfc5545);

        factories.addAll(rfc7808);
        factories.addAll(rfc7953);
        factories.addAll(rfc7986);
        factories.addAll(relationsDraft);
        factories.addAll(rfc9073);
        factories.addAll(vpollDraft);
        // factories.addAll(vvenue);

        return factories;
    }
}
