/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;

/**
 * User: mike
 * Date: 11/27/19
 * Time: 10:52
 */
public abstract class TestComponent extends Component {
    public TestComponent() {
        super("test");
    }

    @Override
    public ComponentList<Component> getComponents() {
        return null;
    }
}
