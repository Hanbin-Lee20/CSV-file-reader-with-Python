# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 04
# File name: DataView.py

from DataController import Controller
from DataModel import Model
import pandas as pd

class View:

    """
    The View class is responsible for all user interface and display logic. 
    It generates data to the console and collects user inputs.
    """

    def __init__(self):

        """
        Initializes the View.
        """
        # This is file path for given dataset.
        file_path = './Fiscaldatareader_PP4/DataCentreAvailability.csv'
        # An instance of the Model class.
        self.model = Model(file_path)
        # An instance of the Controller class.
        self.controller = Controller(self.model)


    def display_records(self, records):
        """
        Prints a of records to the console when called. 
        Only the first 100 are displayed.
        Additionally, inserts a signature after every 10 records.

        :param records: A list of record objects to be displayed.
        """
        # If there's no records in the file, nothing happens
        if not records:
            return
        
        limited_records = records[:300]

        # Header on the top
        header_info = ", ".join(vars(limited_records[0]).keys())
        print(header_info)
        # Iterates over the records, printing each one.
        for index, record in enumerate(limited_records, start=1):
            record_info = ", ".join(f"{key}: {value}" for key, value in vars(record).items())
            print(record_info)

            """
            Check if index is a multiple of 10.
            If so, prints the following statement.
            """
            if index % 10 == 0:
                print("Programmed by Hanbin Lee")

    def edit_record(self):
        """
        Allows the user to edit a specific record in the dataset by its ID.
        The user is prompted to enter the ID of the record first.
        Then type the exact column name to update it.
        When done, user may end modification by empty space + enter.
        """
        row_id = input("Enter the ID of the record to edit: ")
        record = self.controller.get_row_by_id(row_id)
        if record is None:
            print(f"No record found with ID: {row_id}")
            return
        
        updates = {}
        print("Enter the fields you wish to update:")
        while True:
            column = input("""Choose among: _id, Fiscal Year, Fiscal Period,Month, Information Date, Branch, Service, SSC Client, Metric Name, Value, Metric Type.
Enter the name (Leave empty when your done): """)
            if not column or column not in record.index:
                break
            new_value = input(f"New value for {column}: ")
            updates[column] = new_value
        
        if updates:
            success = self.controller.edit_row_threaded(row_id, updates)
            if success:
                print("Record updated successfully.")
        else:
            print("No updates made.")
        self.display_specific_record(row_id)

    def create_new_record(self):
        """
        Allow the  to create a new record by entering every column information.
        The _id column is auto-generated, which is the maximum integer value. 
        """
        new_row = {}
        print("Enter new record data:")
        # An array that stores column names
        columns = ["_id", "Fiscal Year", "Fiscal Period", "Month", "Information Date",
                   "Branch", "Service", "SSC Client", "Metric Name", "Value", "Metric Type"]
        
        """
        Auto assign the maximum id
        Then prompt user to enter the data for each column
        """
        for column in columns:
            if column == "_id":
                max_id = self.controller.get_max_id()
                new_row['_id'] = max_id + 1  # Auto-increment
                print(f"Auto-assigned _id: {new_row[column]}")
                continue
            value = input(f"{column}: ")
            new_row[column] = value
        
        
        self.controller.add_row_threaded(new_row)
        new_max = int(self.controller.get_max_id())
        self.display_specific_record(new_max)
        print("Record created successfully.")

    def delete_record(self):
        """
        Allow user to delete a record from the CSV file.
        If the ID value input by user is found,
        the data is removed.
        """
        try:
            row_id = input("Enter the ID to delete: ")
            success = self.controller.delete_record(row_id)
            if success:
                print(f"Record with ID {row_id} successfully deleted.")
            else:
                print(f"Record with ID {row_id} successfully deleted.")
        except ValueError:
            print("Invalid input. Please enter an integer for the ID.")
        except Exception as e:
            print(f"Error occurred : {e}")

    def display_specific_record(self, record_id):
        """
        Displays a single row of data, using record_id.
        """
        record = self.controller.get_row_by_id(record_id)
        if record is not None:
            print("Record retrieved successfully:")
            print(record)
        else:
            print(f"No row found with ID: {record_id}")

    def sort_records(self):

        """
        Allows user to sort the result based on the column name and order (asc or desc).
        User may enter single or multiple numbers of column(s).
        Then, enter order type for each columns.
        """
        
        pd.set_option('display.max_rows', 900)
        pd.set_option('display.min_rows', 100)
        
        columns_input = input("Enter the columns to sort by (separated by commas): ").split(', ')
        columns_order = input("Enter the order of sorting (asc or desc, comma-separated): ").split(', ')
        ascending = [order.strip().lower() == 'asc' for order in columns_order]

        sorted_df = self.controller.sort_records(columns_input, ascending)
        
        print("Programmed by Hanbin Lee")
        if sorted_df is not None:
            print(sorted_df)
        else:
            print("""
                    Column name not found.
                    Please enter correct column name.
                  """)
        

    def menu(self):
        """
        This is main menu option that constantly prompts the user to choose the option, until user entered 7 to exit.
        On top of the menu, student information is specified.
        """
        while True:
            
            full_name = 'Hanbin Lee'
            stud_num = '041085722'
            print("\n\n"

  f"Programmed by {full_name}\n"
  f"Student Number: {stud_num}")


            print(
"""
What do you want to do?

(1) Display all the data from existing file
(2) Reload the data (refreshing) from the existing file
(3) Create a new record in the existing file
(4) Select and edit a record from existing file
(5) Select and delete a record from existing file
(6) Select column(s) and order(s)
(7) Exit program
""")
            choice = input("Enter the option: ")

            if choice == "1":
                self.controller.display_threaded()
                self.display_records(self.controller.get_records())
            elif choice == "2":
                confirm = input("Is it ok to reload all the data? Y or N: ").strip().lower()
                print("Reloaded data:")
                self.controller.display_threaded()
                self.display_records(self.controller.get_records())
                if confirm == 'y':
                    self.controller.reloading_threaded()
                else:
                    print("Reloading cancelled.")
            elif choice == "3":
                self.create_new_record()
            elif choice == "4":
                self.edit_record()
            elif choice == "5":
                self.delete_record()
            elif choice == "6":
                self.sort_records()
            elif choice == "7":
                print("Exiting program.")
                break   
            else:
                print("Invalid choice. Please try again.")



if __name__ == "__main__":
    app = View()
    app.menu()


    
   