/* Author: Stanley Pieda
 * Date: Jan 1, 2020
 * Modified Jan 8, 2020
 * Description: Start up file.
 * 
 * Tip: 
 * For most applications, design and build your code from the bottom up...
 * Model, then Persistence, then Business, then Presentation layers
 * 
 * Then start things up in the Presentation Layer:
 * Presentation Instantiated, which then instantiates Business layer, which then instantiates Persistence layer in turn.
 * 
 * The Presentation then calls for all records against business, business calls for all records against persistence,
 * persistence loads the records and returns them to business, business returns them to presentation, presentation
 * shows the records to the user... and / or a menu etc.
 * 
 * Another Tip:
 * Keep in mind that for GUI programs, the GUI API libraries will use threads internally. Having the GUI thread
 * instantiate the business layer (etc) will side-step headaches if the start-up program thread attempts to
 * instantiate the different parts of the application separately. i.e. the business and persistence layer objects are
 * running on the main thread, while the user interface logic is running on a separate messaging loop thread in the GUI.
 * 
 * For Mobile and / or Web applications follow the recommended practice for the API and framework you are using.
 * 
 *
 * Dataset Attribution and License:
 * The dataset for use in CST8333 19F Section 350, 351 comes from the Open Government of Canada, published by 
 * Agriculture and Agri-Food Canada.
 * You can obtain the dataset here:
 * Agriculture and Agri-Food Canada. (March 29, 2018). Canadian Cheese Directory [webpage] Retrieved on August 21, 2019 
 * from https://open.canada.ca/data/en/dataset/3c16cd48-3ac3-453f-8260-6f745181c83b 
 *
 * You need to also review the Open Government License which is found here before using this dataset:
 * http://open.canada.ca/en/open-government-licence-canada 
 *
 */

package com.algonquincollege.cst8333.demo;

import com.algonquincollege.cst8333.demo.presentation.CheeseConsoleView;
// NOTE: No import for either Business or Persistence Layers.

/**
 * A class that opens a csv file and outputs the data to the screen.
 * @author spieda
 */
public class CheeseProgramStartUp {

    /**
     * Entry point to the program
     * @param args command-line arguments, not used in this program
     */
    public static void main(String[] args) {
        CheeseConsoleView view = new CheeseConsoleView();
        view.showMenu();
    }

    





    

}
