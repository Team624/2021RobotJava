package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;



public class DriveTrain extends SubsystemBase {
  
    public CANSparkMax frontLeftDrive = new CANSparkMax(Constants.CAN.FrontLeftDriveID, MotorType.kBrushless);
    public CANSparkMax frontLeftSteer = new CANSparkMax(Constants.CAN.FrontLeftSteerID, MotorType.kBrushless);
    public CANSparkMax frontRightDrive = new CANSparkMax(Constants.CAN.FrontRightDriveID, MotorType.kBrushless);
    public CANSparkMax frontRightSteer = new CANSparkMax(Constants.CAN.FrontRightSteerID, MotorType.kBrushless);
    public CANSparkMax backLeftDrive = new CANSparkMax(Constants.CAN.BackLeftDriveID, MotorType.kBrushless);
    public CANSparkMax backLeftSteer = new CANSparkMax(Constants.CAN.BackLeftSteerID, MotorType.kBrushless);
    public CANSparkMax backRightDrive = new CANSparkMax(Constants.CAN.BackRightDriveID, MotorType.kBrushless);
    public CANSparkMax backRightSteer = new CANSparkMax(Constants.CAN.BackRightSteerID, MotorType.kBrushless);
  
  public DriveTrain() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSteers(double speed){
    frontLeftSteer.set(speed);
    backLeftSteer.set(speed);
    frontRightSteer.set(speed);
    backRightSteer.set(speed);
  }

  public void setDrives(double speed){
    frontLeftDrive.set(speed);
    frontRightDrive.set(speed);
    backLeftDrive.set(speed);
    backRightDrive.set(speed);
  }

  public void stopMotors(){
    frontLeftDrive.set(0);
    frontRightDrive.set(0);
    frontLeftSteer.set(0);
    frontRightSteer.set(0);
    backLeftDrive.set(0);
    backRightDrive.set(0);
    backLeftSteer.set(0);
    backRightSteer.set(0);
  }


}
