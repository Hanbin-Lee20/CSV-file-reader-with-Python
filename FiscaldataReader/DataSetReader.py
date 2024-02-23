# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 01
# File name: DataSetReader.py

import csv
from DataRecord import DataCentreRecord

# This is student's full name
STUDENT_NAME = "Hanbin Lee"

# This is the name of the CSV file that contains the data
file_name = "FiscaldataReader\DataCentreAvailability.csv"

# This method reads the data from the CSV file and creates DataCentreRecord objects
def read_data_from_csv(file_name):
    
    # Empty array that stores column name
    # By Hanbin Lee
    data_records = []

    """
    This method reads the csv file, 
                creates DataCentreRecord objects and 
                stores them in the data_records array.
    If an exception occurs, print an error message.
    """

    try:
        # By Hanbin Lee
        with open(file_name, mode='r', encoding='utf-8-sig') as file:
            csv_reader = csv.DictReader(file)
            for row in csv_reader:
                record = DataCentreRecord(
                    record_id=row['_id'], 
                    fiscal_year=row['Fiscal Year'], 
                    fiscal_period=row['Fiscal Period'], 
                    month=row['Month'], 
                    information_date=row['Information Date'], 
                    branch=row['Branch'], 
                    service=row['Service'], 
                    ssc_client=row['SSC Client'], 
                    metric_name=row['Metric Name'], 
                    value=row['Value'], 
                    metric_type=row['Metric Type'])
                data_records.append(record)
    # By Hanbin Lee
    except FileNotFoundError:
        print(f"ERROR! Can't find the file '{file_name}'.")
    except Exception as e:
        print(f"Unknown ERROR occurred! {e}")
    return data_records

# Main function that calls the read_data_from_csv method and prints the first 5 records. 
def main():

    print(f'Practical Project 1 by {STUDENT_NAME}\n')
    
    data_records = read_data_from_csv(file_name)

    # By Hanbin Lee
    for record in data_records[0:5]:
        print(record)

# Runs the main function when the file is executed as a script.
if __name__ == "__main__":
    main()
