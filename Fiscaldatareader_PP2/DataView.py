# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 02
# File name: DataView.py

from DataController import Controller
from DataModel import Model
from DataRecord import DataCentreRecord

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
        file_path = './Fiscaldatareader_PP2/DataCentreAvailability.csv'
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

        # Only include upto 100 records
        limited_records = records[:100]
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

    def display_stud_info(self, full_name, stud_num):
        """
        Simply prints out student's information when called
        """
        print(f"Student Name: {full_name}", f"Student Number: {stud_num}")

    def get_single_row(self):
        """
        Provides single record, searched by its ID. 
        """
        enter_id = input("Enter the ID of the row you want to view: ")
        row = self.controller.get_row_by_id(enter_id)
        
        if row is not None:
            print("Row retrieved successfully:")
            print(row)
        else:
            print(f"No row found with ID: {enter_id}")

    
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
            success = self.controller.edit_row(row_id, updates)
            if success:
                print("Record updated successfully.")
            else:
                print("Failed to update the record.")
        else:
            print("No updates made.")

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
        
        self.controller.add_row(new_row)
        print("New record added successfully.")

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
                print(f"No such ID in the file: {row_id}.")
        except ValueError:
            print("Invalid input. Please enter an integer for the ID.")
        except Exception as e:
            print(f"Error occurred : {e}")


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

(1) Reload data from existing file
(2) Write to a new file
(3) Display single or multiple rows of data from existing file
(4) Create a new record in the existing file
(5) Select and edit a record from existing file
(6) Select and delete a record from existing file
(7) Exit program
""")
            
            choice = input("Enter the option: ")

            if choice == "1":
                self.controller.reload_data()
                self.display_records(self.controller.get_records())
            elif choice == "2":
                output_file = input("Enter file name: ")
                self.controller.write_new_file(output_file)
            elif choice == "3":
                record_choice = input("Do you want to view (a) a single record or (b) all records? Enter 'a' or 'b': ").lower()
                if record_choice == 'a':
                    self.get_single_row()
                elif record_choice == 'b':
                    self.controller.reload_data()
                    self.display_records(self.controller.get_records())
                else:
                    print("Invalid choice. Please enter 'a' or 'b'.")
            elif choice == "4":
                self.create_new_record()
            elif choice == "5":
                self.edit_record()
            elif choice == "6":
                self.delete_record()
            elif choice == "7":
                print("Exiting program.")
                break
            else:
                print("Invalid choice. Please try again.")

if __name__ == "__main__":
    app = View()
    app.menu()
    app.display_stud_info('Hanbin Lee', '041085722')
    
   