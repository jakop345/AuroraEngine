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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.log4j.Logger;


/**
 * .-----------------------------------------------------------------------.
 * | AImageLoader
 * .-----------------------------------------------------------------------.
 * |
 * | Used to load custom images outside of the Surface
 * | using an absolute path
 * |
 * .........................................................................
 * <p/>
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * <p/>
 *
 */
public class AImageLoader extends JLabel implements Cloneable {

    private String ImageURl;

    private Image image;

    private int width;

    private int height;

    private BufferedImage originalImage = null;

    static final Logger logger = Logger.getLogger(AImageLoader.class);

    public AImageLoader(String path) {
        this.ImageURl = path;
        this.image = null;
        this.setDoubleBuffered(true);
        createImage(false);
    }

    public AImageLoader(String path, int Width, int Height) {
        this.ImageURl = path;
        this.image = null;
        this.width = Width;
        this.height = Height;
        this.setDoubleBuffered(true);
        createImage(true);

    }

    private void createImage(Boolean Resize) {

        if (Resize) {

            if (originalImage == null) {
                try {

                    originalImage = ImageIO.read(new File(ImageURl));

                } catch (IOException e) {
                	logger.debug("Error rendering image");
                }
            }

            //Check if resize required
            if (originalImage.getHeight(null) != height
                && originalImage.getWidth(null) != width) {
                BufferedImage resizeImageJpg = resizeImageWithHint(originalImage);
                image = Toolkit.getDefaultToolkit().createImage(
                        resizeImageJpg.getSource());
            }
            this.setIcon(new ImageIcon(image));

        } else {
            try {
                originalImage = ImageIO.read(new File(ImageURl));
            } catch (IOException e) {
            }

            image = Toolkit.getDefaultToolkit().createImage(
                    originalImage.getSource());

            this.setIcon(new ImageIcon(image));


        }

        originalImage.flush();
        originalImage = null;

    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    private BufferedImage resizeImageWithHint(BufferedImage originalImage) {

        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_RGB :
                originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(this.width, this.height,
                type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, this.width, this.height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    public int getIHeight() {
        return this.getIcon().getIconHeight();

    }

    public int getIWidth() {
        return this.getIcon().getIconWidth();

    }

    public String getImgURl() {
        return ImageURl;
    }

    public int getImgHeight() {
        return image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public int getImgWidth() {
        return image.getHeight(null);
    }

    public void setImgURl(String ImgURl) {
        this.ImageURl = ImgURl;
        createImage(true);
    }

    public void setH(int height) {
        this.height = height;
        createImage(true);
    }

    public void setImg(ImageIcon img) {
        this.image = img.getImage();
        createImage(true);
    }

    public void setW(int width) {
        this.width = width;
        createImage(true);
    }

    public void setImageSize(int w, int h) {

        this.width = w;
        this.height = h;
        createImage(true);

    }
}
