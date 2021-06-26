package frc.robot.Subsytems;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Hopper{

    public TalonSRX leftHopper = new TalonSRX(6);
    public TalonSRX rightHopper = new TalonSRX(9);

    public boolean hopperSpinning;
    
    public void HopperLoop(Joystick controller){
        if(controller.getRawButton(0)){
            ManualHopper();
        }else{
            leftHopper.set(ControlMode.PercentOutput,0);
            rightHopper.set(ControlMode.PercentOutput,0);
            hopperSpinning = false;
        }
    }

    public void ManualHopper(){
        leftHopper.set(ControlMode.PercentOutput,.6);
        rightHopper.set(ControlMode.PercentOutput,-.6);
        hopperSpinning = true;
    }

    public void Shoot(){
        leftHopper.set(ControlMode.PercentOutput,-.6);
        rightHopper.set(ControlMode.PercentOutput,.6);
    }
    
}
