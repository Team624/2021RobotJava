package frc.robot.Subsytems;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;


public class Shooter {

    public double targetRPM;
    public double currentDifference;

    public TalonFX leftFlywheel = new WPI_TalonFX(7);
    public TalonFX rightFlywheel = new WPI_TalonFX(8);
    public Solenoid hoodSolenoid = new Solenoid(1);
    public Spark feeder = new Spark(4);

    public void shooterLoop(Joystick controller, Hopper hopper, Intake intake){
        if(controller.getRawButton(3) == true){
            prime();
            if(isPrimed() == true && controller.getRawAxis(0)>.2){
                shoot(hopper, intake);
            }
        }else{
            leftFlywheel.set(ControlMode.Velocity, 0);
            rightFlywheel.set(ControlMode.Velocity, 0);
            hoodSolenoid.set(false);
        }
    }

    public void prime(){
        double targetVelocity_UnitsPer100ms = targetRPM * 2048.0 / 600.0;
        leftFlywheel.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
        rightFlywheel.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
        hoodSolenoid.set(true);
        currentDifference = rightFlywheel.getClosedLoopError();
    }

    public boolean isPrimed(){
        if(currentDifference < 1000){
            return true;
        }else{
            return false;
        }
    }

    public void shoot(Hopper hopper, Intake intake){
        feeder.set(.5);
        hopper.shoot();
        intake.agitateIntake();
    }

}