package frc.robot.Subsytems;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {

    public Solenoid intakeSolenoid = new Solenoid(0);
    public TalonSRX intakeMotor = new TalonSRX(11);
    
    public void IntakeLoop(Joystick controller){
        if(controller.getRawButton(1) == true){
            DropIntake();
        }
        if(controller.getRawButton(1) == false){
            RaiseIntake();
        }
    }

    public void DropIntake(){
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

    public void RaiseIntake(){
        intakeSolenoid.set(false);
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void AgitateIntake(){
        intakeMotor.set(ControlMode.PercentOutput, .1);
    }

    public TalonSRX getIntakeMotor(){
        return intakeMotor;
    }

}
