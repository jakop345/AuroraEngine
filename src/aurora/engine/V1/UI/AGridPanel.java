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
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @version 0.1
 * @author Sammy
 */
public class AGridPanel extends JPanel {

    private int col;
    private int row;
    private boolean full;
    private ArrayList<JComponent> componentList = new ArrayList<JComponent>();
    private int numberOfComponentsAdded;

    public AGridPanel() {
        this.setLayout(new GridLayout());
        numberOfComponentsAdded = 0;
        full = false;

    }

    public AGridPanel(int row, int col) {
        this.row = row;
        this.col = col;
        numberOfComponentsAdded = 0;
        full = false;

        this.setLayout(new GridLayout(this.row, this.col, 0, 0));

    }

    public AGridPanel(int row, int col, boolean Transparent) {
        this.row = row;
        this.col = col;
        numberOfComponentsAdded = 0;
        full = false;

        this.setOpaque(!Transparent);

        this.setLayout(new GridLayout(this.row, this.col, 0, 0));

    }

    /**
     * Add a component to the GridArray As well as the Panel
     *
     * @param comp an Image to be added to the grid panel
     *
     */
    public void addToGrid(JComponent comp) {
        if (!isGridFull()) {
            if (!componentList.contains(comp)) {
                componentList.add(comp);
                this.add(componentList.get(componentList.indexOf(comp)));
                numberOfComponentsAdded++;
                System.out.println("Adding to grid... Size = " + componentList.size());
            }
        }
    }

    /**
     * Updates Whole Panel with all entities inside the GridArray
     *
     */
    public void update() {
        this.removeAll();
        this.revalidate();
        for (int i = 0; i < componentList.size(); i++) {
            this.setPreferredSize(new Dimension(componentList.get(i).getWidth(), componentList.get(i).getHeight()));
            this.add(componentList.get(i));
        }

    }

    /**
     * Checks if Grid is full
     *
     */
    public boolean isGridFull() {
        if (componentList.size() < (row * col)) {
            return false;
        }
        return true;
    }

    public void setToFull() {
    	full = true;
    }

    public int getLastIndexOf(Object o) {
    	return componentList.lastIndexOf(o);
    }

    public Object getFirstComponent() {
    	return componentList.get(0);
    }

    /**
     * Calculates the column and row of the index passed
     * @param index index of component in AGridPanel
     * @return an integer array containing the column and row
     */
    public int[] getColumnAndRow(int index) {

    	int[] columnAndRow = {0, 0};
    	int elementColumn = index % col;

    	if (elementColumn == 0) {
    		elementColumn = col;
    	}

    	double quotient = ((double) 1) / ((double) col);
    	double element_divby_cols = ((double) index) / ((double) col);

    	int elementRow = (int) (((double) element_divby_cols) + (((double) quotient) * (((double) col) - ((double) elementColumn))));

    	columnAndRow[0] = elementColumn;
    	columnAndRow[1] = elementRow;

         return columnAndRow;
    }



    public void removeComp(JComponent comp) {
        componentList.remove(comp);
    }

    /**
     * Finds element in grid array
     *
     */
    public int find(JComponent comp) {

        return componentList.indexOf(comp);
    }

    /**
     * Get everything contained in this specific GridPanel
     * @return Component List
     */
    public ArrayList getArray() {
        return componentList;


    }

    public void clear() {
        this.removeAll();
    }

    //Getters And Setters
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
