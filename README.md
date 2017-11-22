# sample-report

The application accepts the following arguments:
  * **--countries**: allows to import the data for the selected countries and to export them in the selected format; example:
~~~
--countries=Austria,Congo,Italy
~~~
  * **--country/--date**: allows to retrieve the data for the specified country in the time range [date - 10min; date + 10min]; example:
~~~
--country=Italy --date="2017-11-22 15:00"
~~~
  * **--format**: allows to specify the export format; available formats are CSV and XLS; default is CSV; example:
~~~
--format=XLS
~~~
If the application is launched without arguments, the data for all the countries are imported and exported in the selected format.