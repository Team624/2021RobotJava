package frc.robot.Subsytems;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Hopper{

    public TalonSRX leftHopper = new TalonSRX(6);
    public TalonSRX rightHopper = new TalonSRX(9);

    public boolean hopperSpinning;
    
    public void hopperLoop(Joystick controller){
        if(controller.getRawButton(0)){
            manualHopper();
        }else{
            leftHopper.set(ControlMode.PercentOutput,0);
            rightHopper.set(ControlMode.PercentOutput,0);
            hopperSpinning = false;
        }
    }

    public void manualHopper(){
        leftHopper.set(ControlMode.PercentOutput,.6);
        rightHopper.set(ControlMode.PercentOutput,-.6);
        hopperSpinning = true;
    }

    public void shoot(){
        leftHopper.set(ControlMode.PercentOutput,-.6);
        rightHopper.set(ControlMode.PercentOutput,.6);
    }
    
}
