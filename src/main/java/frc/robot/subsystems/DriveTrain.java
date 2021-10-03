package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import com.revrobotics.ControlType;


public class Drivetrain extends SubsystemBase {
  
    // Motors
    public CANSparkMax frontLeftDrive = new CANSparkMax(Constants.CAN.FrontLeftDriveID, MotorType.kBrushless);
    public CANSparkMax frontLeftSteer = new CANSparkMax(Constants.CAN.FrontLeftSteerID, MotorType.kBrushless);
    public CANSparkMax frontRightDrive = new CANSparkMax(Constants.CAN.FrontRightDriveID, MotorType.kBrushless);
    public CANSparkMax frontRightSteer = new CANSparkMax(Constants.CAN.FrontRightSteerID, MotorType.kBrushless);
    public CANSparkMax backLeftDrive = new CANSparkMax(Constants.CAN.BackLeftDriveID, MotorType.kBrushless);
    public CANSparkMax backLeftSteer = new CANSparkMax(Constants.CAN.BackLeftSteerID, MotorType.kBrushless);
    public CANSparkMax backRightDrive = new CANSparkMax(Constants.CAN.BackRightDriveID, MotorType.kBrushless);
    public CANSparkMax backRightSteer = new CANSparkMax(Constants.CAN.BackRightSteerID, MotorType.kBrushless);

    // PIDS
    public CANPIDController d_pidFrontLeft = frontLeftDrive.getPIDController();
    public CANPIDController s_pidFrontLeft = frontLeftSteer.getPIDController();
    public CANPIDController d_pidFrontRight = frontRightDrive.getPIDController();
    public CANPIDController s_pidFrontRight = frontRightSteer.getPIDController();
    public CANPIDController d_pidBackLeft = backLeftDrive.getPIDController();
    public CANPIDController s_pidBackLeft = backLeftSteer.getPIDController();
    public CANPIDController d_pidBackRight = backRightDrive.getPIDController();
    public CANPIDController s_pidBackRight = backRightSteer.getPIDController();

    // Encoders
    public CANEncoder d_encoderFrontLeft = frontLeftDrive.getEncoder();
    public CANEncoder s_encoderFrontLeft = frontLeftSteer.getEncoder();
    public CANEncoder d_encoderFrontRight = frontRightDrive.getEncoder();
    public CANEncoder s_encoderFrontRight = frontRightSteer.getEncoder();
    public CANEncoder d_encoderBackLeft = backLeftDrive.getEncoder();
    public CANEncoder s_encoderBackLeft = backLeftSteer.getEncoder();
    public CANEncoder d_encoderBackRight = backRightDrive.getEncoder();
    public CANEncoder s_encoderBackRight = backRightSteer.getEncoder();
  
    // IMU
    public AHRS imu = new AHRS(SPI.Port.kMXP);

    // PIDs for the drive motors
    private double d_kP = Constants.PID.SwerveConstants.d_kP;
    private double d_kI = Constants.PID.SwerveConstants.d_kI;
    private double d_kD = Constants.PID.SwerveConstants.d_kD;
    private double d_kIz = Constants.PID.SwerveConstants.d_kIz;
    private double d_kFF = Constants.PID.SwerveConstants.d_kFF;
    private double d_kMaxOutput = Constants.PID.SwerveConstants.d_kMaxOutput;
    private double d_kMinOutput = Constants.PID.SwerveConstants.d_kMinOutput;
    // private int d_maxRPM = Constants.SwerveConstants.d_maxRPM;
    // private int d_maxVel = Constants.SwerveConstants.d_maxVel;
    // private int d_maxAcc = Constants.SwerveConstants.d_maxAcc;

    // PIDs for the steer motors
    private double s_kP = Constants.PID.SwerveConstants.s_kP;
    private double s_kI = Constants.PID.SwerveConstants.s_kI;
    private double s_kD = Constants.PID.SwerveConstants.s_kD;
    private double s_kIz = Constants.PID.SwerveConstants.s_kIz;
    private double s_kFF = Constants.PID.SwerveConstants.s_kFF;
    private double s_kMaxOutput = Constants.PID.SwerveConstants.s_kMaxOutput;
    private double s_kMinOutput = Constants.PID.SwerveConstants.s_kMinOutput;
    // private int d_maxRPM = Constants.SwerveConstants.s_maxRPM;
    // private int d_maxVel = Constants.SwerveConstants.s_maxVel;
    // private int d_maxAcc = Constants.SwerveConstants.s_maxAcc;


  public Drivetrain() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // Set PID values
  public void setDrivePID(CANPIDController d_pid){
    d_pid.setP(d_kP);
    d_pid.setI(d_kI);
    d_pid.setD(d_kD);

    d_pid.setIZone(d_kIz);
    d_pid.setFF(d_kFF);

    d_pid.setOutputRange(d_kMinOutput, d_kMaxOutput);
  }

  public void setSteerPID(CANPIDController s_pid){
    s_pid.setP(s_kP);
    s_pid.setI(s_kI);
    s_pid.setD(s_kD);

    s_pid.setIZone(s_kIz);
    s_pid.setFF(s_kFF);

    s_pid.setOutputRange(s_kMinOutput, s_kMaxOutput);
  }

  // Control PIDs (RPM)
  public void updateDrivePID(CANPIDController d_pid, double setPoint){
    d_pid.setReference(setPoint, ControlType.kVelocity);
  }

  // Control PID's (Rotations)
  public void updateSteerPID(CANPIDController s_pid, double setPoint){
    s_pid.setReference(setPoint, ControlType.kPosition);
  }

  // Testing
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