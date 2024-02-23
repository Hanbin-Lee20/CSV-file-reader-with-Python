# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 02
# File name: DataModel.py

import pandas as pd

# Load the dataset from a CSV file into a pandas DataFrame.
datasetData = pd.read_csv("Fiscaldatareader_PP2\DataCentreAvailability.csv")

class Record:
    """
    Represents a single record in the dataset. This class is designed to encapsulate all
    the attributes of a record and provide a structured way to instantiate records with
    specific properties.
    """

    """
    Class variable that acts as a global counter for all instances of Record.
    It is used to assign a unique ID to each new record created.
    """
    _id_counter = 1

    def __init__(self, **kwargs):
        """
        Initializes a new instance of the Record class. Assigns a unique ID to the record
        and sets attributes based on the provided keyword arguments.
        
        :param kwargs: A variable number of keyword arguments. Each key represents the name
                       of an attribute, and the corresponding value is the attribute's value.
        """
        self._id = Record._id_counter
        Record._id_counter += 1
        for key, value in kwargs.items():
            """
            Spaces in keys are replaced with '_'.
            """
            setattr(self, key.replace(' ', '_'), value)

class Model:

    """
    The Model class handles all data-related logic. It interacts with the dataset, performing operations such as loading,
    saving, editing, adding, and deleting records from the 'DataCentreAvailability.csv' file.
    """

    # This is file path for given dataset.
    file_path = "Fiscaldatareader_PP2\DataCentreAvailability.csv"

    def __init__(self, file_path):

        """
        Initializes the Model with the dataset file path and loads the data into a pandas DataFrame.
        
        :param file_path: String path to the dataset CSV file.
        """

        # Path to the CSV file.
        self.file_path = file_path
        # DataFrame holding the dataset using pandas API.
        self.datasetData = pd.read_csv(self.file_path)
        # List to hold Record objects
        self.records = []
        # Loads data from the CSV file
        self.load_data()

    def load_data(self):
        """
        Loads the dataset from the CSV file into the DataFrame.
        This method can be used to refresh the data in case of external changes to the file.
        """
        self.datasetData = pd.read_csv(self.file_path)

    def save_data(self):
        """
        Saves the current state of the DataFrame back to the CSV file.
        This method is called after any modifications to ensure changes are persisted.
        """
        self.datasetData.to_csv(self.file_path, index=False)

    def get_dataset_data(self):
        """
        Returns the current dataset DataFrame.
        
        :return: pandas DataFrame of the dataset.
        """
        return self.datasetData

    def read_data(self):
        """
        Reads data from the CSV file, creating Record objects for each row in the dataset.
        
        Exceptions are handled to address potential issues with file access or data integrity.
        """
        try:
            with open(self.file_path, encoding='utf-8-sig') as file:
                data = pd.read_csv(file)
                self.records = [Record(**row) for row in data.to_dict('records')]
        except FileNotFoundError:
            print("File not found.")
        except Exception as err:
            print(err)

    def write_data(self, output_file):
        """
        Creates a new CSV file from the current Record objects held in the 'records' list.
        
        :param output_file: The path/name of the output file to be created.
        """
        try:
            pd.DataFrame([vars(record) for record in self.records]).to_csv(output_file, index=False)
            print("new file created.")
        except Exception as e:
            print(e)

    def get_row_by_id(self, row_id):
        """
        Retrieves a specific row by the ID.
        
        :param row_id: The ID of the row to retrieve.
        :return: The row as a Series if found, None otherwise.
        """
        # Ensure row_id is an integer
        row_id = int(row_id)
        row = self.datasetData[self.datasetData['_id'] == row_id]
        if not row.empty:
            # Return the first row as a Series if multiple rows have the same ID
            return row.iloc[0]  
        else:
            return None

    
    def edit_row(self, row_id, updates):
        """
        Edits specific fields of a row identified by row_id with new values.
        
        :param row_id: The ID of the row to edit.
        :param updates: A dictionary where each key is a column name and each value is the new value for that column.
        """
        row_index = self.datasetData.index[self.datasetData['_id'] == int(row_id)].tolist()
        if row_index:
            for column, new_value in updates.items():
                if column in self.datasetData.columns:
                    self.datasetData.at[row_index[0], column] = new_value
            self.datasetData.to_csv(self.file_path, index=False)  # Save changes to the CSV file
            return True
        else:
            return False
    
    def add_row(self, new_row):
        """
        Adds a new row to the DataFrame and saves it to the CSV file.
        
        :param new_row: A dictionary with keys as column names and values as row values.
        """
        # Ensure new_row is a dictionary and has at least one key-value pair
        if not isinstance(new_row, dict) or not new_row:
            print("Invalid row data provided.")
            return False
        
        # Append the new row to the DataFrame
        self.datasetData = self.datasetData._append(new_row, ignore_index=True)
        
        # Save the updated DataFrame back to the CSV
        self.save_data()
        return True
    
    
    def delete_record(self, row_id):
        """
        Deletes a row from the DataFrame based on the given row_id.
        
        :param row_id: The ID of the row to delete.
        :return: Boolean indicating success of the deletion.
        """
        row_id = int(row_id)  # Ensure row_id is an integer
        if row_id in self.datasetData['_id'].values:
            self.datasetData = self.datasetData[self.datasetData['_id'] != row_id]
            self.save_data()
            return True
        else:
            return False
