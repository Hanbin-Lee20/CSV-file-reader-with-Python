# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 02
# File name: Test.py

import unittest
from DataModel import Model
import pandas as pd
import os

student_name = 'Hanbin Lee'
print('Programmed by ', student_name)

class TestModel(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        # Set up a path for a test CSV file
        cls.test_file_path = './DataCentreAvailability.csv'
        # Create a test CSV file with initial data
        cls.initial_data = {'_id': [1, 2], 'Fiscal Year': ['2017-2018', '2017-2018'], 
                            'Fiscal Period': [1, 1], 'Month': ['April', 'April'], 
                            'Information Date': ['4/30/2017', '4/30/2017'], 
                            'Branch': ['Data Centre Services', 'Data Centre Services'], 
                            'Service': ['Data Centre Facilities - Enterprise', 'Data Centre Facilities - Enterprise'], 
                            'SSC Client': ['EDC Barrie', 'EDC Barrie'], 
                            'Metric Name': ['% of time SSC Enterprise Data Centres Facilities are available', 'Contracted Power Capacity (kVA)'], 
                            'Value': [100, 1000], 'Metric Type': ['Non Cumulative', 'Non Cumulative']}
        pd.DataFrame(cls.initial_data).to_csv(cls.test_file_path, index=False)

    def test_add_row(self):
        model = Model(self.test_file_path)
        new_row = {'_id': 3, 'Fiscal Year': '2017-2018', 
                   'Fiscal Period': 1, 'Month': 'April', 'Information Date': '4/30/2017', 
                   'Branch': 'Data Centre Services', 'Service': 'Data Centre Facilities - Enterprise', 
                   'SSC Client': 'EDC Barrie', 'Metric Name': 'Power Consumption Rate (kVA)', 
                   'Value': 139, 'Metric Type': 'Non Cumulative'}
        model.add_row(new_row)
        
        # Reload data to ensure it's reading the updated CSV
        model.load_data()
        added_row = model.get_row_by_id(3)
        
        # Check if the new row matches the one we added
        self.assertIsNotNone(added_row, "Failed to add the new row.")
        self.assertEqual(added_row['_id'], new_row['_id'], "The new row's ID does not match the expected value.")

if __name__ == '__main__':
    unittest.main()