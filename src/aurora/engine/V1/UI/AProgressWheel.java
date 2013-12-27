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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Sammy
 */
public class AProgressWheel extends JPanel {

	private Timer runner;
	private String URL;
	private AImage img;
	private int rotate = 0;
	private int speed = 15;
	private boolean isClockwiseRotating = true;
	private boolean stop = false;

	public AImage getImg() {
		return img;
	}

	public AProgressWheel(String URL) {

		this.URL = URL;
		img = new AImage(this.URL);
		setOpaque(false);

		this.setPreferredSize(new Dimension(img.getImgIcon().getIconWidth(),
				img.getImgIcon().getIconHeight()));
		start();

	}

	public AProgressWheel(String URL, String SurfaceName) {

		this.URL = URL;
		img = new AImage(this.URL);
		setOpaque(false);

		this.setPreferredSize(new Dimension(img.getImgIcon().getIconWidth(),
				img.getImgIcon().getIconHeight()));
		start();

	}

	public AProgressWheel(String URL, int speed) {

		this.URL = URL;
		img = new AImage(this.URL);
		setOpaque(false);

		this.speed = speed;
		this.setPreferredSize(new Dimension(img.getImgIcon().getIconWidth(),
				img.getImgIcon().getIconHeight()));

		if (speed != 0) {
			start();
		}

	}

	public AProgressWheel(String URL, int speed, String SurfaceName) {

		this.URL = URL;
		img = new AImage(this.URL);
		setOpaque(false);

		this.speed = speed;
		this.setPreferredSize(new Dimension(img.getImgIcon().getIconWidth(),
				img.getImgIcon().getIconHeight()));
		start();

	}

	private void start() {
		if (speed != 0) {
			if (runner == null) {
				runner = new Timer(16, new StartProgressWheel());
			}
			runner.start();
		}
	}

	public class StartProgressWheel implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!stop && SwingUtilities.isEventDispatchThread()) {

				repaint();

			} else {
				if (runner != null) {
					runner.stop();
				}
				Timer time = (Timer) e.getSource();
				time.stop();
				runner = null;
				return;
			}

		}

	}

	public void stop() {
		this.setVisible(false);
		stop = true;
	}

	public void restart() {
		this.setVisible(true);
		stop = false;
		start();
	}

	public void setSpeed(int Speed) {
		this.speed = Speed;
		if (speed != 0) {
			stop = false;
			if (runner == null) {
				start();
			}
		} else {
			stop = true;
			runner = null;
		}
	}

	public void setClockwise(boolean Clockwise) {

		this.isClockwiseRotating = Clockwise;

	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);

		rotate += (speed - 5);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		AffineTransform origXform = g2d.getTransform();
		AffineTransform newXform = (AffineTransform) (origXform.clone());

		// center of rotation is center of the panel
		int xRot = this.getWidth() / 2;
		int yRot = this.getHeight() / 2;
		if (isClockwiseRotating) {
			newXform.rotate(Math.toRadians(Math.abs(rotate)), xRot, yRot);
		} else if (!isClockwiseRotating) {
			newXform.rotate(-Math.toRadians(Math.abs(rotate)), xRot, yRot);
		}
		g2d.setTransform(newXform);

		// draw image centered in panel
		int x = (getWidth() - img.getImgIcon().getIconWidth()) / 2;
		int y = (getHeight() - img.getImgIcon().getIconHeight()) / 2;
		g2d.drawImage(img.getImgIcon().getImage(), x, y, this);
		g2d.setTransform(origXform);

	}

}
