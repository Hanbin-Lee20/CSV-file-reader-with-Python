/* Author: Stanley Pieda
 * Date: Jan 1, 2020
 * Modified Jan 8, 2020
 * Description: Presentation Layer, also called View Layer.
 * Responsible to interact with the user and send data to, and retrieve data from the Business Layer
 * This should be lightweight, data validation for fields would be inside the RecordObject, while higher-level
 * data validation would be within the Business Layer.
 * 
 * Note that this class does not need to know the inner workings of the buisiness layer, not even if the List of
 * CheeseRecords is cached in memory or re-generated each time it is accessed. It also should never instantiate or 
 * call any methods directly on the persistence layer class. All calls from presentation are sent via the business
 * layer. Lastly, do not modify the referenced list in the presentation layer only read data from it. 
 * Inserting, updating, or deleting records from the referenced list is not the role of the presentation. 
 * The presentation should call the business layer methods to insert, update, delete against the list of records.
 * 
 * Testing the presentation layer is outside the scope of our course, however like the other layers this layer can also
 * be instantitaed and given a contrived business layer to permit independent testing.
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
package com.algonquincollege.cst8333.demo.presentation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.algonquincollege.cst8333.demo.model.CheeseRecord;
import com.algonquincollege.cst8333.demo.business.CheeseService;
// NOTE: No import statement for Persistence Layer, Presentation does not use Persistence Layer!

/**
 * A simple console based view for user interaction.
 * @author piedas
 *
 */
public class CheeseConsoleView {
    /** instance of business layer */
    private final CheeseService service;
    
    /** instance of scanner for System.in inputs */
    private final Scanner scanner;
    
    /** constant for menu system, ToDo replace with a proper Enum */
    private final static String SHOW_ALL_CHEESE = "a";
    
    /** constant for menu system, ToDo replace with a proper Enum */
    private final static String SHOW_ONE_CHEESE = "v";
    
    /** constant for menu system, ToDo replace with a proper Enum */
    private final static String INSERT_CHEESE = "i";
    
    /** constant for menu system, ToDo replace with a proper Enum */
    private final static String UPDATE_CHEESE = "u";
    
    /** constant for menu system, ToDo replace with a proper Enum */
    private final static String DELETE_CHEESE = "d";
    
    /** constant for menu system, ToDo replace with a proper Enum */
    private final static String EXIT_PROGRAM = "x";
    
    /* ToDo See this resource for a reminder of Enums: https://howtodoinjava.com/java/enum/java-enum-string-example/ */
    
    /**
     * Constructor instantiates one instance of business layer, as well as initializes Scanner.
     */
    public CheeseConsoleView() {
        service = new CheeseService();
        scanner = new Scanner(System.in);
    }
    
    /**
     * Runs a menu sytem on a loop, exiting on proper tolken from user EXIT_PROGRAM
     */
    public void showMenu() {
        boolean exit = false;
        String response = null;
        while(exit != true) {
            printMenu();
            response = readString();
            processResponse(response);
            if(response != null && response.equalsIgnoreCase(EXIT_PROGRAM)) {
                exit = true;
            }
        }
        scanner.close();
    }
    
    /**
     * Prints a menu, also shows author name as per requirements for screen shots
     */
    private void printMenu() {
        System.out.println("Program by Professor Stanley Pieda");
        System.out.printf("%s %s%n","Please enter an option: \n(a) all cheeses, (v) view one cheese, \n",
                                    "(i) insert cheese, (u) update cheese, \n(d) delete cheese, (x) exit program");
    }
    /**
     * Prints out the list of records on screen line by line using toString as defined in the Record object,
     * also prints authors full name every 10 records to meet requirements for screen shots.
     */
    private void printList() {
        List<CheeseRecord> list = service.getAllCheeses();
        int recordCount = 0;
        for (CheeseRecord cheese : list) {
            System.out.println(cheese);
            recordCount++;
            if(recordCount % 10 == 0) { // print out program author name every 10 records.
                System.out.println("Program by Professor Stanley Pieda");
            }
        }
    }
    
    /**
     * Delgates to other methods to process task requests based on user inputs.
     * ToDo: Replace the parameter as String, with an Enum instead.
     * @param entry String with menu options.
     */
    private void processResponse(String entry) {
        switch(entry) {
        case SHOW_ALL_CHEESE:
            printList();
            break;
        case SHOW_ONE_CHEESE:
            viewOne();
        break;
        case INSERT_CHEESE:
            insert();
            break;
        case UPDATE_CHEESE:
            update();
            break;
        case DELETE_CHEESE:
            delete();
            break;
        case EXIT_PROGRAM:
            System.out.println("Exiting program");
            break;
        default:
            System.out.println("Please enter a valid menu option");
        }
    }
    
    /**
     * Permits user to view one record based on an identifier supplied by user.
     * Will send request to fetch a record to the Business Layer.
     * Not yet implemented.
     */
    private void viewOne() {
        // ToDO:
        // Ask user for identifier, e.g. CheeseId
        // Create a Cheese record object, set the CheeseId leaving other fields null
        // Call cheeseService.getCheese(cheeseRecord)
        // show record returned or message if not found
        System.out.println("View One Chese: Feature not implemented");
    }
    
    /**
     * Permits user to insert a record, after prompting for data for fields.
     * Requests Business Layer perform the insert.
     * Not yet implemented.
     */
    private void insert() {
        // ToDo:
        // Create new CheeseRecord instance
        // Interact with user to set values into the record
        // Call cheeseService.insert(record);
        // catch or report problems or display success.
        System.out.println("Insert Cheese: Feature not impemented");
    }
    
    /**
     * Permits user to update an existing record, based on idenfier value provided by user.
     * Requests Business Layer perform the update.
     * Will prompt for data for updates and alter fields in record.
     * Not yet implemented.
     */
    private void update() {
        // ToDo:
        // Ask user for identifier, e.g. CheeseId
        // Create a Cheese record object, set the CheeseId leaving other fields null
        // Call cheeseService.getCheese(cheeseRecord)
        // Interact with user to update values in the record
        // Call cheeseService.update(record);
        // catch or report problems or display success.
        System.out.println("Update Cheese: Feature not implemented");
    }
    
    /**
     * Permits user to delete an existing record, based on idenfier value provided by user.
     * Requests Business Layer perform the delete.
     * Not yet implemented.
     */
    private void delete() {
        // ToDo:
        // Create new CheeseRecord instance
        // Interact with user to set values into the record
        // Call cheeseService.delete(record);
        // catch or report problems or display success.
        System.out.println("Feature not impemented");
    }
    
    /**
     * Utility method to help with standard input stream processing via Scanner instance.
     * @return String that the user entered, note this may be an empty string.
     */
    private String readString() {
        String result = null;
        try {
            result = scanner.nextLine();
        }
        catch(RuntimeException ex) {
            System.out.println("Could not read in text entry from console");
        }
        return result;
    }
    
    /**
     * Utility method to help with standard input stream processing via Scanner instance.
     * Removes leftover newline character(s) from stream after removing a double to prevent logic bugs.
     * CAUTION: Not tested.
     * @return A Double representing the double value entered by the user or null if not successful.
     */
    private Double readDouble() {
        Double result = null;
        try {
            result = scanner.nextDouble();
            scanner.nextLine(); // remove leftover line terminator
        }
        catch(RuntimeException ex) {
            System.out.println("Could not read in number entry from console");
        }
        return result;   
    }
    
    /**
     * Utility method to help with standard input stream processing via Scanner instance.
     * Removes leftover newline character(s) from stream after removing a boolean value to prevent logic bugs.
     * CAUTION: Not tested.
     * @return A Boolean representing the boolean value entered by the user or null if not successful.
     */
    private Boolean readBoolean() {
        Boolean result = null;
        try {
            result = scanner.nextBoolean();
            scanner.nextLine(); // remove leftover line terminator
        }
        catch(RuntimeException ex) {
            System.out.println("Could not read in true / false entry from console");
        }
        return result;   
    }
    
    /**
     * Utility method to help with standard input stream processing via Scanner instance.
     * Removes leftover newline character(s) from stream after removing an int value to prevent logic bugs.
     * CAUTION: Not tested.
     * @return An Integer representing the int value entered by the user or null if not successful.
     */
    private Integer readInteger() {
        Integer result = null;
        try {
            result = scanner.nextInt();
            scanner.nextLine(); // remove leftover line terminator
        }
        catch(RuntimeException ex) {
            System.out.println("Could not read in number entry from console");
        }
        return result; 
    }
    
    /**
     * Utility method to help with standard input stream processing via Scanner instance.
     * CAUTION: Not tested.
     * @return A LocalDate representing the date value entered by the user or null if parsing was not successful.
     */
    private LocalDate readLocalDate() {
        LocalDate result = null;
        try {
            String input = scanner.nextLine();
            result = LocalDate.parse(input);
        }
        catch(RuntimeException ex) {
            System.out.println("Could not read in date entry from console");
        }
        return result;
    }
}
