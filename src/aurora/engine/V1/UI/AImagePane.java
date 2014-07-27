/*
 * Copyright 2012 Sardonix Creative.
 *
 * This work is licensed under the
 * Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/
 *
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aurora.engine.V1.UI;

import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.ASurface;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 * Image on a panel
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 */
public class AImagePane extends JPanel {

    private String ImageURL;

    private ImageIcon image;

    private int imageHeight = 0;

    private int imageWidth = 0;

    private float heightRatio = 1;

    private float widthRatio = 1;

    private final ASurface ressource;

    private String SurfaceName;

    static final Logger logger = Logger.getLogger(AImagePane.class);

    private String ImageFileName;

    //////////////
    //Constructors
    //////////////
    public AImagePane(String path, int W, int H) {
        super(true);
        ressource = new ASurface("");
        ImageURL = path;
        imageHeight = H;
        imageWidth = W;
        this.setOpaque(false);

        setUpImage(ImageURL);

    }

    public AImagePane() {
        ressource = new ASurface("");
    }

    public AImagePane(String path) {
        super(true);
        ressource = new ASurface("");
        ImageURL = path;
        imageHeight = 0;
        imageWidth = 0;
        this.setOpaque(false);

        setUpImage(ImageURL);
    }

    public AImagePane(String path, LayoutManager layout) {

        super(layout, true);
        ressource = new ASurface("");
        ImageURL = path;
        imageHeight = 0;
        imageWidth = 0;
        this.setOpaque(false);
        setUpImage(ImageURL);
    }

    public AImagePane(String path, int W, int H, LayoutManager layout) {
        super(layout, true);
        ressource = new ASurface("");
        ImageURL = path;
        imageHeight = H;
        imageWidth = W;
        this.setOpaque(false);
        setUpImage(ImageURL);
    }

    public AImagePane(String path, int W, int H, LayoutManager layout,
                      boolean DoubleBuffered) {
        super(layout, DoubleBuffered);
        ressource = new ASurface("");
        ImageURL = path;
        imageHeight = H;
        imageWidth = W;
        this.setOpaque(false);

        setUpImage(ImageURL);

    }

    public AImagePane(String path, int W, int H, boolean DoubleBuffered) {
        super(DoubleBuffered);
        ressource = new ASurface("");
        ImageURL = path;
        imageHeight = H;
        imageWidth = W;
        this.setOpaque(false);

        setUpImage(ImageURL);

    }

    protected final void setUpImage(String URL) {
        try {
            image = new ImageIcon(new URL(ressource.getSurfacePath()
                                          + "/aurora/V1/resources/"
                                          + ImageURL));
        } catch (MalformedURLException ex) {
            try {
                image = new ImageIcon(getClass()
                        .getResource(
                                "/aurora/V1/resources/"
                                + ImageURL));
            } catch (Exception exx) {
                logger.error(exx);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        //  Dispaly  image on Panel
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                             RenderingHints.VALUE_RENDER_DEFAULT);

        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                             RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                             RenderingHints.VALUE_STROKE_NORMALIZE);

        if (image != null) {
            if (imageWidth == 0) {
                imageWidth = image.getIconWidth();
            }

            if (imageHeight == 0) {
                imageHeight = image.getIconHeight();
            }


            g2d.drawImage(image.getImage(), 0, 0, imageWidth, imageHeight, this);

        } else {
            g2d.clearRect(0, 0, imageWidth, imageHeight);
        }
    }

    public void setURL(String URL) {
        ImageURL = URL;

        // Allow size to be from image
        imageWidth = 0;
        imageHeight = 0;
        try {
            image = new ImageIcon(new URL(ImageURL));
        } catch (MalformedURLException ex) {
            image = new ImageIcon(ImageURL);
        }
        this.revalidate();
        this.repaint();
    }

    public void setImageSize(int width, int height) {
        imageHeight = height;
        imageWidth = width;
        revalidate();
        repaint();
    }

    public void setImage(ImageIcon img, int H, int W) {
        this.image = null;
        this.repaint();
        this.revalidate();
        image = img;
        imageHeight = H;
        imageWidth = W;
        this.repaint();
        this.revalidate();
    }

    public void setImage(String ImageURL, int H, int W) {
        this.image = null;
        this.repaint();
        this.revalidate();

        this.ImageURL = ImageURL;
        imageHeight = H;
        imageWidth = W;
        this.setOpaque(false);
        try {
            image = new ImageIcon(new URL(ressource.getSurfacePath()
                                          + "/aurora/V1/resources/"
                                          + this.ImageURL));
        } catch (MalformedURLException ex) {
            try {
                image = new ImageIcon(getClass()
                        .getResource(
                                "/aurora/V1/resources/"
                                + ImageURL));
            } catch (Exception exx) {
                logger.error(exx);
            }
        }
        this.revalidate();
        this.repaint();
    }

    public Boolean checkImageExists(String ImageURL) {

        if (AFileManager.checkFile(ImageURL)) {
            return false;
        } else {
            return true;
        }

    }

    public void setImage(String ImageURL) {
        this.image = null;

        this.ImageURL = ImageURL;

        try {
            this.image = new ImageIcon(new URL(ressource.getSurfacePath()
                                               + "/aurora/V1/resources/"
                                               + ImageURL));
        } catch (MalformedURLException ex) {
            try {
                image = new ImageIcon(getClass()
                        .getResource(
                                "/aurora/V1/resources/"
                                + ImageURL));
            } catch (Exception exx) {
                logger.error(exx);
            }
        }

        imageWidth = image.getIconWidth();
        imageHeight = image.getIconHeight();

        this.revalidate();
        this.repaint();

    }

    public void setImage(AImagePane image) {
        if (image != null) {
            this.image = image.getImgIcon();
            imageWidth = image.getImageWidth();
            imageHeight = image.getImageHeight();
        } else {
            this.image = image.getImgIcon();
        }

        this.revalidate();
        this.repaint();

    }

    public void setImageURL(String ImageURL) throws MalformedURLException {
        this.image = null;

        if (logger.isDebugEnabled()) {
            logger.debug("URL " + ImageURL);
        }
        this.ImageURL = ImageURL;
        setImage(ImageURL);

        imageWidth = image.getIconWidth();
        imageHeight = image.getIconHeight();

        this.revalidate();
        this.repaint();

    }

    public void clearImage() {
        this.image = null;
        this.setOpaque(false);
        this.revalidate();
        this.repaint();
    }

    public ImageIcon getImgIcon() {
        return image;
    }

    public String getImageURL() {
        if (ImageURL != null) {
            return ImageURL;
        } else {
            return ImageFileName;
        }
    }

    public void setImageFileName(String ImageFileName) {
        this.ImageFileName = ImageFileName;
    }

    public int getRealImageWidth() {
        return this.getImgIcon().getIconWidth();
    }

    public int getRealImageHeight() {
        return this.getImgIcon().getIconHeight();
    }

    public Dimension getRealImageSize() {
        return new Dimension(getRealImageWidth(), getRealImageHeight());
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
        this.repaint();
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
        this.repaint();
    }

    public int getImageHeight() {
        return imageHeight;

    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setHeightRatio(float heightRatio) {
        this.heightRatio = heightRatio;
    }

    public float getHeightRatio() {
        return heightRatio;
    }

    public void setWidthRatio(float widthRatio) {
        this.widthRatio = widthRatio;
    }

    public float getWidthRatio() {
        return widthRatio;
    }

    public void setSurface(String SurfaceName) {

        this.SurfaceName = SurfaceName;

        ressource.setSurfaceName(this.SurfaceName);
        this.repaint();
    }

}
