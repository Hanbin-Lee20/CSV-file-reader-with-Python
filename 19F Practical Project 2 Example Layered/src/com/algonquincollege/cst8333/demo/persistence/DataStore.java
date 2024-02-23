/* Author: Stanley Pieda
 * Date: Jan 1, 2019
 * Modified Dec 27, 2019
 * Description: Persistence Layer, that uses Apache Commons CSV library to open
 * and records from the CSV dataset.
 * 
 * The persistence layer holds the logic for saving data to permanent storage so the data is not lost when the program
 * is shut down. In this case it would be a Comma Separated Value file, but it could be refactored to use a database or
 * even a web service.
 * 
 * Note that the public methods used here are patterned after a simple Data Access Objects Design pattern, to faciliate
 * a move to a database at a later time. Not all methods are implemented, using a thrown exception as demonstrated
 * here is controversial, some programmers use this technique others avoid it: 
 * check with any future employer to follow their recommended practice.
 * 
 * Also Note that this class can be instantiated and unit tested independent from the rest of the program.
 * 
 * This class should never instantiate the Business Layer, and never instantiate the Presentation Layer or make
 * call to them. Either return a communcation object (simple POJO with status messages) or throw exceptions that
 * contain the messages if things go wrong.
 * 
 * Not all parts of the program are complete.
 * 
 * Apache Commons CSV version 1.6 downloaded from:
 * http://commons.apache.org/proper/commons-csv/download_csv.cgi
 * License is packaged within the downloadable binary zip archive,
 *  as well as included in this project folder.
 * 
 * This program was created with help from Appache Commons API documents:
 * http://commons.apache.org/proper/commons-csv/archives/1.6/apidocs/index.html
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
package com.algonquincollege.cst8333.demo.persistence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.algonquincollege.cst8333.demo.model.CheeseRecord;
// NOTE: No import for Business or Presentation Layer packages, Persistence does not need to know about them!

/**
 * Persistence Layer of the application
 * @author piedas
 *
 */
public class DataStore {
    /** Name of the data set file */
    private final static String FILE_NAME = "canadianCheeseDirectory.csv";
    
    /**
     * Returns result of loading and parsing a csv file that contains data for CheeseRecords.
     * @return List of CheeseRecords obtained from file
     */
    public List<CheeseRecord> getAll(){
        return loadRecordsFromFile();
    }
    
    /**
     * Batch processing to insert many records into data store.
     * Not yet implemented.
     * @param list containing the CheeseRecords to insert.
     */
    public void insertAll(List<CheeseRecord> list) {
        // ToDo: 
        // Generate a GUID (UUID in Java Speak) for a file name with extension .csv
        // send the file name and list to the saveRecordsToFile(string, List) method.
        // later on this could become a batch job against a database.
        throw new RuntimeException("com.algonquincollege.cst8333.demo.persistence.DataStore.getRecord() not yet implemented.");
    }
    
    /**
     * Obtains one record from data store based on CheeseId
     * @param cheeseID identifier of CheeseRecord
     * @return matching CheeseRecord or null if no match found.
     */
    public CheeseRecord getRecord(Integer cheeseID) {
        throw new RuntimeException("com.algonquincollege.cst8333.demo.persistence.DataStore.getRecord() not yet implemented.");
        // return null;
    }
    
    /**
     * Inserts CheeseRecord into data strore.
     * @param record to be inserted into data store.
     */
    public void insertRecord(CheeseRecord record) {
        // ToDo: Append to end of file
        throw new RuntimeException("com.algonquincollege.cst8333.demo.persistence.DataStore.insertRecord() not yet implemented.");  
    }
    
    /**
     * Updates CheeseRecord in data strore.
     * @param record to be updated in data store.
     */
    public void updateRecord(CheeseRecord record) {
        // ToDo: Scan file, then update existing record if found
        throw new RuntimeException("com.algonquincollege.cst8333.demo.persistence.DataStore.updateRecord() not yet implemented.");  
    }
    
    /**
     * Deletes CheeseRecord in data strore.
     * @param cheeseID identifier of CheeseRecord.
     */
    public void deleteRecord(Integer cheeseID) {
        // ToDo: Scan file, 
        throw new RuntimeException("com.algonquincollege.cst8333.demo.persistence.DataStore.deleteRecord() not yet implemented."); 
    }
    
    /**
     * Opens and parses csv file to obtain records. Uses Apache Commons API library.
     * 
     * This code is based on an example(s) from:
     * Apache Commons. (2019). Apache Commons CSV User Guide. Retrieved from
     * http://commons.apache.org/proper/commons-csv/user-guide.html [Accessed On: Jan 8, 2020]
     * @return a List of CheeseRecords generated from file.
     */
    private List<CheeseRecord> loadRecordsFromFile() {
        BufferedReader csvFile = null;
        List<CheeseRecord> list = new ArrayList<CheeseRecord>();
        try {
            csvFile = openFile(FILE_NAME);
            

            
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvFile);
            for(CSVRecord record: records) {
                CheeseRecord cheese = new CheeseRecord(
                record.get("CheeseId"),
                record.get("CheeseNameEn"),
                record.get("ManufacturerNameEn"),
                record.get("ManufacturerProvCode"),
                record.get("ManufacturingTypeEn"),
                record.get("WebSiteEn"),
                record.get("FatContentPercent"),
                record.get("MoisturePercent"),
                record.get("ParticularitiesEn"),
                record.get("FlavourEn"),
                record.get("CharacteristicsEn"),
                record.get("RipeningEn"),
                record.get("Organic"),
                record.get("CategoryTypeEn"),
                record.get("MilkTypeEn"),
                record.get("MilkTreatmentTypeEn"),
                record.get("RindTypeEn"),
                record.get("LastUpdateDate"));
                list.add(cheese);
            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            closeFile(csvFile);
        }
        return list;
    }
    
    /**
     * Writes records within the array list to the specified file name using comma separated value format. (UTF-8).
     * Not yet implemented.
     * @param fileName Name of file to write out as comma separated value file (*.csv)
     * @param records Container with the record objects to be written to storage
     */
    public void saveDataToFile(String fileName, List<CheeseRecord> records) {
        throw new RuntimeException("com.algonquincollege.cst8333.demo.persistence.DataStore.saveDataToFile(string, List) not yet implemented.");
        
    }
    
    /**
     * Opens the file, initializes the class-level Scanner object to use the file for reading.
     * Make sure to call method closeFile() of this class before closing down the program.
     * The file used in (18F) seems to have a UTF-8 byte order mark (BOM), the code was not changed for the 19F dataset
     * See https://stackoverflow.com/questions/17405165/first-character-of-the-reading-from-the-text-file-%C3%AF/17405840
     * as answered by author Nayuki on Jul 1, 2013.
     */
    private BufferedReader openFile(String fileName) {
        BufferedReader in = null;
        try {
            //[start quoted code] Nayuki (2013). Explanatory comments by Stanley Pieda
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            in.mark(1); // mark current position, while allowing to read-ahead 1 character.
            if(in.read() != 0xFEFF) { // read (remove) that one character, if it is 0xFEFF skip the reset as BOM was 1st character
                in.reset(); // the 1st character is not the BOM, so reset stream back to 1st character so it will be read correctly
            }
            //[end quoted code]
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found: "
                    + ex.getMessage());
        }
        catch(IOException ex){
            System.out.println("Problem opening file: "
                    + ex.getMessage());
        }
        return in;
    }

    /**
     * Closes the class-level Scanner object, which also closes the underlying file stream.
     */
    private void closeFile(BufferedReader reader) {
        try {
            if(reader != null) {reader.close();}
        }
        catch(Exception ex) {
            System.out.println("Problem closing file: "
                    + ex.getMessage());
        }
    }

}
