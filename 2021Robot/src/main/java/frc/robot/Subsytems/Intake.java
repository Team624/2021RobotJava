package frc.robot.Subsytems;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;

public class Intake {

    public Solenoid intakeSolenoid = new Solenoid(0);
    public PWMTalonSRX intakeMotor = new PWMTalonSRX(11);
    
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
        intakeMotor.set(.3);
    }

    public void RaiseIntake(){
        intakeSolenoid.set(false);
        intakeMotor.stopMotor();
    }

    public void AgitateIntake(){
        intakeMotor.set(.1);
    }

    public PWMTalonSRX getIntakeMotor(){
        return intakeMotor;
    }

}
