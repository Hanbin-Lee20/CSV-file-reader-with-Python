# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 03
# File name: Test.py

import unittest
from DataController import Controller
from DataModel import Model

class TestMultithreading(unittest.TestCase):
    
    def setUp(self):
        """
        Setup method to initialize before each test method.
        """
        file_path = './Fiscaldatareader_PP3/DataCentreAvailability.csv'
        self.model = Model(file_path)
        self.controller = Controller(self.model)
        self.initial_row_count = len(self.model.datasetData)

    def test_add_row_threaded(self):
        """
        Test adding a new record using multithreading.
        By Hanbin Lee
        """
        initial_max_id = self.controller.get_max_id()
        new_record = {"_id": initial_max_id + 1, 
                      "Fiscal Year": "2020-2021", 
                      "Fiscal Period": 4, 
                      "Month": "July", 
                      "Information Date": "7/31/2020", 
                      "Branch": "Test Branch", 
                      "Service": "Test Service", 
                      "SSC Client": "Test Client", 
                      "Metric Name": "Test Metric", 
                      "Value": 100, 
                      "Metric Type": "Test Type"}
        # Add a new record (data above) using threaded function
        self.controller.add_row_threaded(new_record)

        # Variable for comparing previous maximum id value.
        new_max_id = self.controller.get_max_id()

        # Check if the new max ID is incremented
        # If they are same: test has failed
        # Different: tsst has succeded 
        self.assertEqual(initial_max_id + 1, new_max_id, "The new record was not added correctly.")

if __name__ == '__main__':
    unittest.main()
