public class ButtonLamp {
    boolean on;

    /**
     * constructor for button lamp
     * @author Saad Sheikh
     */
    public ButtonLamp() {
        on = false;
    }

    /**
     * function to turn on lamp
     * @author Saad Sheikh
     */
    public void turnOn(){
        if(!on){
            on = true;
        }
    }

    /**
     * function to turn off lamp
     * @author Saad Sheikh
     */
    public void turnOff(){
        if(on){
            on = false;
        }
    }
}
