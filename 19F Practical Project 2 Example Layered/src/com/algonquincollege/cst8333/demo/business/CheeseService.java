/* Author: Stanley Pieda
 * Date: Jan 1, 2020
 * Modified Jan 8, 2020
 * Description: Problem Domain Layer, also called Business Layer.
 * Responsible to process data between persistence and view layers.
 * E.g. Business rules may include validating that a record is correct, above just checking data types, e.g.
 * is a company URL a valid web address, a valid province code, any Food Regulatory Requiremnts not met etc.
 * 
 * Note that this class does not need to know the inner workings of the persistence layer, not even if there is a csv
 * file. It also has no idea what client will be using it, Console? Swing GUI? JavaFX? NetworkSockets? REST? Android?
 * 
 * Also note that this class could be instantiated, and provided a dummy instance of the persistence layer to do
 * unit testing, independent from the persistence layer.
 * 
 * This class uses an instance of the persistence layer class, but should not instantiate or class the presentation
 * layer, again if a validation fails send it to the presentation layer as a status message object (custom POJO), or
 * throw an exception (nested exception if needed).
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
package com.algonquincollege.cst8333.demo.business;

import com.algonquincollege.cst8333.demo.model.CheeseRecord;
import com.algonquincollege.cst8333.demo.persistence.DataStore;

// NOTE: No import statement for Presentation package! Business Layer does not need to know about Presentation Layer

import java.util.List;

/**
 * Business Layer program logic, this class is designed to cache the records from the Persistence Layer and is currently
 * incomplete. It will eventually request the Persistence layer to perform insert update delete and batch saving
 * of records.
 * @author piedas
 *
 */
public class CheeseService {
    /** an instance of the Persitence Layer */
    private DataStore dataStore = null;
    
    /** List of references to CheeseRecords */
    private List<CheeseRecord> list = null;
    
    /** Constant used to indicate a search of the List did not locate a matching CheeseRecord */
    private static final int NOT_FOUND = -1;
    
    /**
     * Instantiates the one instance of the Persitence Layer, eagerly loads the records from the Persistence layer.
     */
    public CheeseService() {
        dataStore = new DataStore();
        list = dataStore.getAll();
    }
    
    /** Returns a reference to the List of CheeseRecords, note this should be modified to return a copy to 
     *  prevent callers from manipulating the List outside of this Classes perview.
     * @return Reference to the internal List
     */
    public List<CheeseRecord> getAllCheeses(){
        return list;
    }
    
    /**
     * Accepts a CheeseRecord which should have a CheeseId set.
     * @param record containing a CheeseId as the basis for the search
     * @return a reference to a CheeseRecord or null if a match is not found.
     */
    public CheeseRecord getCheese(CheeseRecord record) {
        CheeseRecord value = null;
        int index = searchList(record.getCheeseId());
            if(index > NOT_FOUND) {
                value = list.get(index);
            }
        return value;
    }
    
    /**
     * Inserts the CheeseRecord into the List.
     * ToDo:Passes the CheeseRecord down to the Persistence Layer for insertion into the data store
     * @param record The record to insert.
     */
    public void insertCheeseRecord(CheeseRecord record) {
        list.add(record);
    }
    
    /**
     * Updates the CheeseRecord in the list, based on CheeseId for a match.
     * ToDo: Passes the CheeseRecord down to the Persistence Layer to update against the data store
     * @param record
     */
    public void updateCheeseRecord(CheeseRecord record) {
        int index = searchList(record.getCheeseId());
        if( NOT_FOUND == index) {
            throw new RuntimeException("Attempt to update cheese failed, cheese not found in list");
        }
        else {
            list.set(index, record);
        }
    }
    
    /**
     * Removes the CheeseRecord from the list, based on CheeseId for a match.
     * ToDo: Pass the CheeseId to the Persistence Layer to remove from the data store.
     * @param record
     */
    public void deleteCheeseRecord(CheeseRecord record) {
        int index = searchList(record.getCheeseId());
        if( NOT_FOUND == index) {
            throw new RuntimeException("Attempt to delete cheese failed, cheese not found in list");
        }
        else {
            list.remove(index);
        }
    }
    
    /**
     * Sends a file name as well as the list of records to persist to the data store. i.e. batch save
     * ToDo: Decide if Persitence layer names the files using UUID or if user will specify the name, 
     * this wasn't determined before coding started and now oddly one-half of each approach is implented.
     * Need to trace and refactor to use one or the other approach, or give an option to select a UUID name if
     * a valid file name is not specified.
     * @param fileName Filename to save the data to.
     */
    public void saveAllCheeses(String fileName) {
        dataStore.saveDataToFile(fileName, list);
    }
    
    /**
     * Simple sequential search of the list, runs with Big O (n).
     * If the dataset record count scales up to 10's of thousands of records this will need to be revisted,
     * possibly replace List with a Sorted Binary Tree ( O(log n) ) or Hashtable ( O(1) ) depending on memory 
     * constraints vs fastest 
     * performance. Alternatively explore Java 8 Stream API see a discussion here for future learning: 
     * https://www.baeldung.com/find-list-element-java
     * 
     * @param id The CheeseId of the CheeseRecord we are searching for.
     * @return the index position of the matching CheeseRecord in the List, or NOT_FOUND if it is not located.
     */
    private int searchList(Integer id) {
        int result = NOT_FOUND;
        for(int index = 0; index < list.size(); index++) {
            if(list.get(index).getCheeseId().equals(id)) {
                result = index;
                break;
            }
        }
        return result;
    }
    /* Closing thought, the List<CheeseRecord> here could be replaced in this class with a Data Structure more suitable 
     * for searching. As long as the List obtained from the Persistence Layer is converted into the new Data Structure, 
     * and a List<CheeseRecord> is generated when requested by the Persistence Layer no changes need to occur outside of
     * this class.
     */
}
