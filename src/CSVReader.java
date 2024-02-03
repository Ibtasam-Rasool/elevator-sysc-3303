import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CSVReader {

    /**
     * Reads the ElevatorCallSheet csv file and parses it, returning a taskList
     * @return taskList, the tasklist formed from the csv file
     * @author Quinton Tracey
     */
    public ArrayList<TaskData> parser(){

        String file = "src\\ElevatorCallSheet - Sheet1.csv";
        BufferedReader reader = null;
        String line = "";

        ArrayList<TaskData> taskList = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(file));

            //skipping over the colomn names
            reader.readLine();
            //skipping over the example
            reader.readLine();

            // go over each line
            while((line = reader.readLine()) != null){

                ArrayList temp = new ArrayList();
                String[] row = line.split(",");

                // go over each cell/csv
                for(String cell : row){
                    // if the cell is a int parse it as an int
                    if((cell.contains("0") || cell.contains("1") || cell.contains("2") || cell.contains("3") || cell.contains("4") || cell.contains("5") || cell.contains("6") || cell.contains("7") || cell.contains("8") || cell.contains("9")) && (cell.indexOf(":") == -1)){
                        temp.add(Integer.parseInt(cell));
                    } else {
                        // leave it as a string
                        temp.add(cell);
                    }
                }
                // adding new tasks to task list
                taskList.add(new TaskData((String) temp.get(0), (Integer) temp.get(1), (String) temp.get(2), (Integer) temp.get(3)));
            }
        } catch (Exception e){

        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return taskList;
    }


}
