public class Door {
    private boolean doorOpen;
    private int elevatorId;

    /**
     * initializes elevator door
     * @param elevatorId the elevator's id
     */
    public Door(int elevatorId){
        this.elevatorId = elevatorId;
    }

    /**
     * open's elevator door
     */
    public void openDoor(){
        if(!doorOpen){
            doorOpen = true;
        }
    }

    /**
     * closes elevator door
     */
    public void closeDoor(){
        if(doorOpen){
            doorOpen = false;
        }
    }
}
