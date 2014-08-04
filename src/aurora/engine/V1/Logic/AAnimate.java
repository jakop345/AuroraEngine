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
package aurora.engine.V1.Logic;

import java.awt.Graphics;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JComponent;
import org.apache.log4j.Logger;

/**
 *
 * @author Sammy
 * @version 0.2
 */
public class AAnimate implements Runnable {

    private Thread runner;

    private float Alpha;

    private int AnimationID;

    private JComponent component;

    private Graphics g;

    private APostHandler postAnimate;

    private final ArrayList<APostHandler> postListenerList;

    private int x = 0;

    private int y = 0;

    private int XPos;

    private int speed;

    private int YPos;

    private int YSpeed;

    private int XSpeed;

    private boolean Animating = false;

    private int acc;

    private float alphaAcc;

    static final Logger logger = Logger.getLogger(AAnimate.class);

    private Window frame;

    public AAnimate(JComponent component) {
        this.postListenerList = new ArrayList<>();
        this.component = component;
        this.component.setDoubleBuffered(true);
        this.component.setVisible(false);
    }

    public AAnimate() {
        this.postListenerList = new ArrayList<>();
    }

    public AAnimate(JComponent component, APostHandler e) {
        this.postListenerList = new ArrayList<>();
        this.component = component;
        this.component.setDoubleBuffered(true);
        this.postAnimate = e;
        this.component.setVisible(false);
    }

    public AAnimate(Window frame) {
        this.frame = frame;
        this.postListenerList = new ArrayList<>();
        this.frame.setVisible(false);
    }

    public void setInitialLocation(int x, int y) {

        this.x = x;
        this.y = y;
        if (component != null) {
            this.component.setBounds(x, y, component.getWidth(), component
                    .getHeight());
        } else {
            frame.setLocation(x, y);
        }

    }

    public void addPostAnimationListener(APostHandler e) {
        this.postAnimate = e;
    }

    public void appendPostAnimationListener(APostHandler e) {
        postListenerList.add(e);
    }

    public boolean isAnimating() {
        return Animating;
    }

    public void setComponent(JComponent component) {
        this.component = component;
        this.component.setDoubleBuffered(true);
    }

    /**
     * Fades in a component. not functioning
     *
     * EFFECT ID = 0;
     *
     * @param component
     */
    public void fadeIn(Window component) {
        //Get Graphics from Commponent

        this.frame = component;

        Alpha = 0.0f;

        //start fade effect
        AnimationID = 0;
        start();

    }

    /**
     * Fades out a component. not functioning
     *
     * EFFECT ID = 1;
     *
     * @param component
     */
    public void fadeOut(Window component) {
        //Get Graphics from Commponent

        this.frame = component;

        Alpha = 1.0f;

        //Enable Anti-Alias
        AnimationID = 1;
        start();
    }

    /**
     * Moves Component Horizontally Vertically and Diagonally
     *
     * EFFECT ID = 2,3,4;
     *
     */
    public void moveHorizontal(int XPosition, int MoveSpeed) {
        this.XPos = XPosition;
        this.speed = MoveSpeed;
        if (XPos < 0) {
            speed = (-1 * speed);

        }
        AnimationID = 2;

        if (logger.isDebugEnabled()) {
            logger.debug("move horz with X at: " + x);
        }

        start();
    }

    public void moveVertical(int YPosition, int MoveSpeed) {
        this.YPos = YPosition;
        this.speed = MoveSpeed - 10;

        AnimationID = 3;
        start();
    }

    public void moveDiagonal(int XPosition, int YPosition, int XMoveSpeed,
                             int YMoveSpeed) {
        this.YPos = YPosition;
        this.XPos = XPosition;
        this.YSpeed = YMoveSpeed;
        this.XSpeed = XMoveSpeed;

        AnimationID = 4;
        start();
    }

    public void start() {
        runner = null;
        runner = new Thread(this);
        runner.start();

        if (logger.isDebugEnabled()) {
            logger.debug("Running animation");
        }

    }

    public void run() {

        while (runner == Thread.currentThread()) {
            Animating = true;

            if (AnimationID == 0) {

                if (logger.isDebugEnabled()) {
                    logger.debug("Alpha of Animation: " + Alpha);
                }

                //Decrease Alpha
                Alpha += (0.05F + alphaAcc);
                frame.repaint();
                frame.revalidate();

                frame.setOpacity(Alpha);

                if (Alpha > 0.9F) {
                    Alpha = 1F;
                    frame.setOpacity(Alpha);
                    break;
                }

                alphaAcc = alphaAcc + 0.01f;

            } else if (AnimationID == 1) {

                if (logger.isDebugEnabled()) {
                    logger.debug("Alpha of Animation: " + Alpha);
                }

                //Decrease Alpha
                Alpha -= (0.05F + alphaAcc);
                frame.repaint();
                frame.revalidate();

                frame.setOpacity(Alpha);

                if (Alpha < 0.05F) {
                    Alpha = 0.0F;
                    frame.setOpacity(Alpha);
                    break;
                }

                //HORIZONTAL
            } else if (AnimationID == 2) {

                x += (speed) + acc;

                component.setBounds(x, component.getLocation().y, component
                        .getWidth(), component.getHeight());

                if (speed > 0) {

                    if (component.getLocation().x >= XPos) {
                        break;
                    }

                    if (component.getLocation().x > XPos / 2) {
                        if (logger.isDebugEnabled()) {
//                            logger.debug("Slow Down Pos!!");
                        }

                        acc--;
                        acc--;
                    } else if (component.getLocation().x < XPos / 2) {
                        if (logger.isDebugEnabled()) {
//                            logger.debug("Accelerate Pos!!");
                        }

                        acc++;
                    }

                } else {
                    if (component.getLocation().x <= XPos) {
                        break;
                    }

                    if (component.getLocation().x <= XPos / 2) {
                        if (logger.isDebugEnabled()) {
//                            logger.debug("Slow Down Neg!!");
                        }

                        acc++;
                        acc++;
                    } else if (component.getLocation().x >= XPos / 2) {
                        if (logger.isDebugEnabled()) {
//                            logger.debug("Accelerate Neg!!");
                        }

                        acc--;
                    }
                }
                this.component.setVisible(true);

                //VERTICAL
            } else if (AnimationID == 3) {
                if (YPos >= 0 && speed > 0) {
                    y = y + speed;
                    if (YPos <= y) {
                        break;
                    }
                    if (component != null) {
                        component.setBounds(component.getLocation().x, y,
                                component
                                .getWidth(), component.getHeight());
                    } else {
                        frame.setLocation(x, y);
                    }
                } else {
                    if (speed < 0) {
                        y = y + speed;
                    } else {
                        y = y - speed;
                    }

                    if (component != null) {
                        component.setBounds(component.getLocation().x, y,
                                component
                                .getWidth(), component.getHeight());
                    } else {
                        frame.setLocation(x, y);
                    }

                    if (YPos >= y) {
                        break;
                    }

                    if (speed < 0) {
                        if (YPos >= y + speed) {
                            speed = -(y - YPos);
                        }
                    } else {
                        if (YPos >= y - speed) {
                            speed = YPos - y;
                        }
                    }

                }
                if (component != null) {
                    component.repaint();
                } else {
                    frame.repaint();
                }

                //DIAGONAL
            } else if (AnimationID == 4) {
                if (YPos > 0 || XPos > 0) {
                    y = y + YSpeed;
                    x = x + XSpeed;
                } else {
                    y = y - YSpeed;
                    x = x - XSpeed;
                }

                //if one or the other positions attained wait untill other
                //axis is attained
                if (component.getLocation().y >= YPos) {
                    y = YPos;
                } else if (component.getLocation().x >= XPos) {
                    x = XPos;
                }

                if (component.getLocation().x >= YPos
                    && component.getLocation().y >= YPos) {
                    break;
                }

                component.setBounds(x, y, component.getWidth(), component
                        .getHeight());

            }

            //pause
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }

            alphaAcc = alphaAcc + 0.01f;

            if (logger.isDebugEnabled()) {
//                System.out.println("X Val " + x);
//                System.out.println("Y Val " + y);
            }

            if (component != null) {
                this.component.repaint();
                this.component.setVisible(true);
            } else {
                frame.repaint();
                frame.setVisible(true);
            }
        }


        Animating = false;
        runner = null;
        if (component != null) {
            this.component.setLocation(x, y);
            this.component.revalidate();
        } else {
            this.frame.setLocation(x, y);
            this.frame.revalidate();
        }
        doneAnimation();

    }


    ///Run when animation is complete
    private void doneAnimation() {
        if (postAnimate != null) {
            postAnimate.doAction();
        }

        if (postListenerList.size() != 0) {
            for (int i = 0; i < postListenerList.size(); i++) {
                postListenerList.get(i).doAction();
            }

            postListenerList.clear();
        }
    }

    public void removeAllListeners() {
        this.postAnimate = null;
    }
}
