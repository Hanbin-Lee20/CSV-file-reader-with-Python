# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 04
# File name: DataController.py

from DataRecord import DataCentreRecord
import threading
import pandas as pd

# Load the dataset from a CSV file into a pandas DataFrame.
datasetData = pd.read_csv("FiscaldataReader_PP4\DataCentreAvailability.csv")

class Controller:

    """
    The Controller class manages the interaction between the model and the view. It handles user requests,
    manipulates data through the model, and updates the view accordingly.
    """

    def __init__(self, model):
        """
        Initialize the controller with a reference to the model.
        
        :param model: An instance of the Model class.
        """
        self.model = model

    # Option 1
    # This function reads data with multithreading feature
    def display_threaded(self):
        """
        Reloads the dataset from the csv file into the model's data structure,
        using multithreading feature as a PP3 requirement.
        """
        thread = threading.Thread(target=self.model.read_data)
        thread.start()
        thread.join()

    # Option 2
    # This function reloads (refresh) data from the csv file into the model's data structure.
    def reloading_threaded(self):
        thread = threading.Thread(target=self.model.reload_data)
        thread.start()
        thread.join()

    # Option 3
    def add_row_threaded(self, new_row):
        """
        Adds a new record to the dataset using multithreading.
        
        :param new_row: New record to add, where keys are column names.
        :return: Boolean indicating the success of the addition.
        """
        def thread_add():
            return self.model.add_row(new_row)
        
        thread = threading.Thread(target=thread_add)
        thread.start()
        thread.join()

    # Option 4
    def edit_row_threaded(self, row_id, updates):
        """
        Edits a specific record with new data provided by the user.
        
        :param row_id: The ID of the record to edit.
        :param updates: The column names and their new values.
        :return: Boolean indicating the success of the update.
        """
        def thread_edit():
            return self.model.edit_row(row_id, updates)
        
        thread = threading.Thread(target=thread_edit)
        thread.start()
        thread.join()

    # Option 5
    def delete_record(self, record_id):
        """
        Deletes a specific record by its ID.
        
        :param record_id: The ID of the record to be deleted.
        :return: Boolean indicating the success of the deletion.
        """
        def thread_delete():
            return self.model.delete_record(record_id)
        
        thread = threading.Thread(target=thread_delete)
        thread.start()
        thread.join()
    
    def get_records(self):
        """
        Retrieves all records currently loaded in the model.
        
        :return: A list of all records.
        """
        return self.model.records

    def get_row_by_id(self, row_id):
        """
        Retrieves a single record by its ID.
        
        :param row_id: The ID of the record to retrieve.
        :return: The requested record
        """
        return self.model.get_row_by_id(row_id)

    def get_max_id(self):
        """
        Calculates the maximum ID value among all records in the dataset.
        
        :return: The highest '_id' value. It will return 0 if there is no data.
        """
        datasetData = self.model.get_dataset_data()
        if not datasetData.empty:
            return datasetData['_id'].max()
        else:
            return 0

    def sort_records(self, columns, ascending):
        """Sorts the records based on the given columns and orders."""
        try:
            sorted_df = self.model.sort_record(columns, ascending)
            return sorted_df
        except Exception as e:
            print(f"Error while sorting records: {e}")
            return None
    
