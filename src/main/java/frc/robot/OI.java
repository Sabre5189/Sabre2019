package frc.robot;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class OI {
    public XboxController driver=new XboxController(0);
    public JoystickButton aButton=new JoystickButton(driver, 0);
    public JoystickButton xButton=new JoystickButton(driver, 2);
    public JoystickButton bJoystickButton=new JoystickButton(driver, 1);
    public JoystickButton   yButton=new JoystickButton(driver, 3);


    /*private void foo(){
        driver.getTriggerAxis(Hand.kLeft);
        driver.getTriggerAxis(Hand.kRight);
        */
    }
