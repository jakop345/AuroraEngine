/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aurora.engine.V1.UI;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public interface AuroraScreenUI {


    /**
      .------------------------------------------------------------------------.
      |     loadUI() Method
      .------------------------------------------------------------------------.
      |
      |
      | This method performs the initial loading of all UI components in this
      | screen. This is the first method that is called on creation of a
      | screen.
      |
      | In this method the following should be
      | allowed to happen:
      | * Creation of UI components
      | * Creation of UI components dependencies
      | * In-line configuration of components only (via parameters)
      | * Obligatory pre-configuration should visibly be created and
      |   configured first.
      |
      |
      .
     */
     void loadUI();


     /**
      .------------------------------------------------------------------------.
      |    buildUI() Method
      .------------------------------------------------------------------------.
      |
      |
      | This method creates the UI by first configuring it to the UI needs
      | then it should add the UI components to the appropriate Canvas.
      |
      |
      | The method will perform this after the loadUI() method has
      | accomplished creating all necessary objects to allow for
      | the building process to occur more then one time.
      | Divide the method into two:
      | 1. Configure components in the order they are created
      | 2. Add the components to the canvas in the order they are seen
      |
      |
      .
     */

     void buildUI();

    /**
    * .------------------------------------------------------------------------.
    * |    surface{}
    * .------------------------------------------------------------------------.
    * |
    * | This is an enumeration containing constants with regards to the specific
    * | file name of UI assets from the Surface currently being used
    * |
    * |
    * |
    * |
    * ..........................................................................
    *
    */

    enum surface {

        /** A Large Hex Panel. */
        HexPanel ("HexPane.png");

        /** the file name of the asset found in the surface. */
        private String fileName;

        /**
         * set the name of the surface asset to be re-used.
         * @param assetName name of asset
         */
        private surface(final String assetName) {
            this.fileName = assetName;
        }
    }

}
