/**
 * Created by AZbest on 22.02.2016.
 */

public abstract class Rule {

    @Override
    public abstract String toString();
    public abstract boolean apply(Possibilities possibilities);
    public abstract void draw(int x, int y, IconSet iconSet, boolean highlight);

    public boolean applyOnStart() {
        return false;
    }

    public ShowOptions getShowOpts() {
        return ShowOptions.SHOW_NOTHING;
    };

}