FILE NAMES:
    - ButtonLamp
    - CSVReader
    - DestinationButton
    - Elevator
    - Floor
    - Main
    - Motor
    - Scheduler
    - TaskData

TO RUN:
    - add a csv file with the name "ElevatorCallSheet - Sheet1.csv" to the src folder with the following format:

    Time,Floor,Floor Button,Car Button
    hh:mm:ss.mmm,n,Up/Down,n

    Followed by the values in rows below it ordered by time
    Example CSV file:

    Time,Floor,Floor Button,Car Button
    hh:mm:ss.mmm,n,Up/Down,n
    14:05:15,2,Up,4
    15:06:05,4,Down,1
    19:00:00,1,Up,2
    22:25:55,3,Down,2

    - Run the Main File