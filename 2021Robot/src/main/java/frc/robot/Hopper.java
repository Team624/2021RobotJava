package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;

public class Hopper{

    public PWMTalonSRX leftHopper = new PWMTalonSRX(6);
    public PWMTalonSRX rightHopper = new PWMTalonSRX(9);

    public boolean hopperSpinning;
    
    public void HopperLoop(Joystick controller){
        if(controller.getRawButton(0)){
            leftHopper.set(.6);
            rightHopper.set(.6);
            hopperSpinning = true;
        }else{
            hopperSpinning = false;
        }
    }
    
}
