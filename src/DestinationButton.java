public class DestinationButton {
    private boolean pressed;
    private int floorNum;
    private ButtonLamp buttonLamp;

    /**
     * Constructor for destination button
     * @param floorNum the destination floor that this button is associated with
     * @author Saad Sheikh
     */
    public DestinationButton(int floorNum){
        this.floorNum = floorNum;
        pressed = false;
        buttonLamp = new ButtonLamp();
    }

    /**
     * Function to simulate button being pressed
     * @author Saad Sheikh
     */
    public void pressButton(){
        if (!pressed){
            pressed = true;
            buttonLamp.turnOn();
        }
    }

    /**
     * @return the status of the current button
     * @author Saad Sheikh
     */
    public boolean isPressed(){
        return pressed;
    }
}