package net.fortuna.ical4j.agent;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.immutable.ImmutableMethod;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;
import net.fortuna.ical4j.transform.AddTransformer;
import net.fortuna.ical4j.transform.CancelTransformer;
import net.fortuna.ical4j.transform.CounterTransformer;
import net.fortuna.ical4j.transform.DeclineCounterTransformer;
import net.fortuna.ical4j.transform.PublishTransformer;
import net.fortuna.ical4j.transform.RefreshTransformer;
import net.fortuna.ical4j.transform.ReplyTransformer;
import net.fortuna.ical4j.transform.RequestTransformer;
import net.fortuna.ical4j.transform.Transformer;
import net.fortuna.ical4j.util.Calendars;
import net.fortuna.ical4j.util.UidGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fortuna on 19/07/2017.
 */
public abstract class AbstractUserAgent<T extends CalendarComponent> implements UserAgent<T> {

    private final ProdId prodId;

    private final Map<Method, Transformer<Calendar>> transformers;

    public AbstractUserAgent(ProdId prodId, Organizer organizer, UidGenerator uidGenerator) {
        this.prodId = prodId;
        transformers = new HashMap<>();
        transformers.put(ImmutableMethod.PUBLISH, new PublishTransformer(organizer, uidGenerator, true));
        transformers.put(ImmutableMethod.REQUEST, new RequestTransformer(organizer, uidGenerator));
        transformers.put(ImmutableMethod.ADD, new AddTransformer(organizer, uidGenerator));
        transformers.put(ImmutableMethod.CANCEL, new CancelTransformer(organizer, uidGenerator));
        transformers.put(ImmutableMethod.REPLY, new ReplyTransformer(uidGenerator));
        transformers.put(ImmutableMethod.REFRESH, new RefreshTransformer(uidGenerator));
        transformers.put(ImmutableMethod.COUNTER, new CounterTransformer(uidGenerator));
        transformers.put(ImmutableMethod.DECLINE_COUNTER, new DeclineCounterTransformer(organizer, uidGenerator));
    }

    @SafeVarargs
    protected final Calendar wrap(Method method, T... component) {
        Calendar calendar = Calendars.wrap(component);
        calendar.getProperties().add(prodId);
        calendar.getProperties().add(ImmutableVersion.VERSION_2_0);
        return transform(method, calendar);
    }

    protected Calendar transform(Method method, Calendar calendar) {
        Transformer<Calendar> transformer = transformers.get(method);
        transformer.transform(calendar);
        return calendar;
    }

    public ProdId getProdId() {
        return prodId;
    }
}
