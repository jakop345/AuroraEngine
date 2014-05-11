package aurora.engine.V1.UI;

import java.awt.event.MouseListener;
import org.apache.log4j.Logger;


/**
 * The AInfoFeedLabel is used to scroll components from the right edge of the
 * panel to the left edge. Scrolling is continuous. To simulate the scrolling of
 * text you can simply add a JLabel to the panel.
 * <p/>
 * Various properties control the scrolling of the components on the panel.
 * Changes to the properties are dynamic and will take effect the next time the
 * components are scrolled.
 */
public class AInfoFeedLabel extends ASlickLabel implements MouseListener{

    /*
     * The URL associated with this label text
     */
    private String url;

    private String sourceName;

    private int startPosition;

    /*
     * The position along the x-axis where this JLabel ends
     */
    private int endPosition;

    private String customToolTip;

    static final Logger logger = Logger.getLogger(AInfoFeedLabel.class);

    public AInfoFeedLabel(String title, String url) {

        super(title);
        this.url = url;

    }

    public void setUrl(String url) {
         this.url = url;
    }

    public void setToolTip(String customToolTip){
        this.customToolTip = customToolTip;
    }

    public String getUrl() {

        return url;
    }

    public void setStartPosition(int position) {

        this.startPosition = position;
    }

    public int getStartPosition() {

        return startPosition;
    }

    public void setEndPosition(int position) {

        this.endPosition = position;

    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setSourceName(String name) {
        this.sourceName = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getToolTip() {
        if (customToolTip == null) {
            return sourceName;
        } else {
            return customToolTip;
        }
    }

    @Override
    public String toString() {

        return "Title:= " + this.getText(); // + " URL:= " + url.toString();
    }


}