package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Spark;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
public class DriveTrain extends SubsystemBase {
  
    public Spark frontLeftDrive = new Spark(Constants.CAN.FrontLeftDriveID);
    public Spark frontLeftSteer = new Spark(Constants.CAN.FrontLeftSteerID);
    public Spark frontRightDrive = new Spark(Constants.CAN.FrontRightDriveID);
    public Spark frontRightSteer = new Spark(Constants.CAN.FrontRightSteerID);
    public Spark backLeftDrive = new Spark(Constants.CAN.BackLeftDriveID);
    public Spark backLeftSteer = new Spark(Constants.CAN.BackLeftSteerID);
    public Spark backRightDrive = new Spark(Constants.CAN.BackRightDriveID);
    public Spark backRightSteer = new Spark(Constants.CAN.BackRightSteerID);
  
    public AHRS imu = new AHRS(SPI.Port.kMXP);

  public DriveTrain() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getRotation() {
    double angle = imu.getAngle();
    return angle;
  }

  public void resetImu() {
    imu.reset();
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
