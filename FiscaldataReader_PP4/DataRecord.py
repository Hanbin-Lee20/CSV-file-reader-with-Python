# Student name: Hanbin Lee
# Course: CST8333 - Programming Language Research Project
# Assignment name: Practical project 04
# File name: DataRecord.py

# By Hanbin Lee
import dateutil.parser as parser

# By Hanbin Lee
class DataCentreRecord:
    def __init__(self, record_id, fiscal_year, fiscal_period, month, information_date, branch, 
                 service, ssc_client, metric_name, value, metric_type):
        self.record_id = int(record_id)
        self.fiscal_year = fiscal_year
        self.fiscal_period = fiscal_period
        self.month = month
        self.information_date = self.format_date(information_date)
        self.branch = branch
        self.service = service
        self.ssc_client = ssc_client
        self.metric_name = metric_name
        self.value = value
        self.metric_type = metric_type

    @staticmethod
    def format_date(date_string):

        """
        This method is used to format the date string to the format 'MM/DD/YYYY'.
        It takes a date string as input and returns the formatted date string.
        If the date string is empty or cannot be parsed, it returns None.
        
        By Hanbin Lee
        """
        
        # If the date string is empty, return None
        if not date_string:
            return None
        try:

            # Used API, dateutil, to parse the information date record and format automatically
            # The value will be: 'YYYY-mm-dd 00:00:00'
            # By Hanbin Lee
            parsed_date = parser.parse(date_string)

            # Using date() function, the timestamp is removed
            # The value will be: 'YYYY-mm-dd'
            date_only = parsed_date.date()

            # Returns date without timestamp
            return date_only
        
        except Exception as e:
            return e

    def __str__(self):

        """
        This method is used to specify column names for the data records.
        """

        return (f"ID: {self.record_id}, Year: {self.fiscal_year}, Period: {self.fiscal_period}, Month: {self.month}, "
                f"Information Date: {self.information_date}, Branch: {self.branch}, Service: {self.service}, "
                f"Client: {self.ssc_client}, Metric: {self.metric_name}, Value: {self.value}, Type: {self.metric_type}")
