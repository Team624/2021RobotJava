package frc.robot.Subsytems;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {

    public Solenoid intakeSolenoid = new Solenoid(0);
    public TalonSRX intakeMotor = new TalonSRX(11);
    
    public void intakeLoop(Joystick controller){
        if(controller.getRawButton(1) == true){
            dropIntake();
        }
        if(controller.getRawButton(1) == false){
            raiseIntake();
        }
    }

    public void dropIntake(){
        intakeSolenoid.set(true);
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        intakeMotor.set(ControlMode.PercentOutput,.3);
    }

    public void raiseIntake(){
        intakeSolenoid.set(false);
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void agitateIntake(){
        intakeMotor.set(ControlMode.PercentOutput, .1);
    }

    public TalonSRX getIntakeMotor(){
        return intakeMotor;
    }

}
