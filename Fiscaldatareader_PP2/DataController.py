# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 02
# File name: DataController.py

from DataRecord import DataCentreRecord
import pandas as pd

# Load the dataset from a CSV file into a pandas DataFrame.
datasetData = pd.read_csv("Fiscaldatareader_PP2\DataCentreAvailability.csv")


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

    def reload_data(self):
        """
        Reloads the dataset from the source file into the model's data structure.
        """
        self.model.read_data()

    def write_new_file(self, output_file):
        """
        Writes the current state of the 'DataCenterAvailability.csv' to a new file.
        
        :param output_file: The file path where the data will be written.
        """
        self.model.write_data(output_file)

    def get_records(self):
        """
        Retrieves all records currently loaded in the model.
        
        :return: A list of all records.
        """
        return self.model.records

    def delete_record(self, record_id):
        """
        Deletes a specific record by its ID.
        
        :param record_id: The ID of the record to be deleted.
        :return: Boolean indicating the success of the deletion.
        """
        return self.model.delete_record(record_id)
    
    def get_row_by_id(self, row_id):
        """
        Retrieves a single record by its ID.
        
        :param row_id: The ID of the record to retrieve.
        :return: The requested record
        """
        return self.model.get_row_by_id(row_id)

    def edit_row(self, row_id, updates):
        """
        Edits a specific record with new data provided by the user.
        
        :param row_id: The ID of the record to edit.
        :param updates: A dictionary containing the column names and their new values.
        :return: Boolean indicating the success of the update.
        """
        return self.model.edit_row(row_id, updates)

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


    def add_row(self, new_row):
        """
        Adds a new record to the dataset.
        
        :param new_row: A dictionary representing the new record to add, where keys are column names.
        :return: Boolean indicating the success of the addition.
        """
        return self.model.add_row(new_row)
