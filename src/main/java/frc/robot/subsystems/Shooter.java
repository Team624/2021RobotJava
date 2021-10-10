// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.Shooter.IdleShooter;
import frc.robot.commands.Shooter.Prime;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Gains;

public class Shooter extends SubsystemBase {
  private final Solenoid hoodSolenoid = new Solenoid(Constants.Solenoid.hoodID);
  public TalonFX leftFlywheel = new TalonFX(Constants.CAN.leftFlywheelID);
  public TalonFX rightFlywheel = new TalonFX(Constants.CAN.rightFlywheelID);
  
  //Are we shooting manually?
  private boolean manualShoot;
  //Does dash say to put hood up while manual shooting?
  private boolean manualHood;
  //What RPM does the manual dash say?
  private double manualRPM;

  //Should hood be up right now?
  private boolean autoHood;
  //What should rpm be right now?
  private double autoRPM;

  //What is the idle fly speed
  private double idleFly = Constants.ShooterSettings.idleFlywheelSpeed;
  //What is the idle hood set
  private boolean idleHood = Constants.ShooterSettings.idleHoodSet;

  //Should we tune PID's?
  private boolean tunePID;


  //What should the rpm be now?
  private double setRPM;
  //What is rpm now?
  private double currentRPM;
  //Are we priming now?
  private boolean prime;


  private double Pconstant;
  private double Iconstant;
  private double Dconstant;
  private double Fconstant;
  private int I_Zone;
  private double MaxOutput;

  private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");

  private NetworkTableEntry dashTunePid = shooterTab.add("Tune PID", false).withPosition(0, 0).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
  
  private NetworkTableEntry dashShootManual = shooterTab.add("Shoot Manual", false).withPosition(1, 0).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
  private NetworkTableEntry dashHood = shooterTab.add("Dash Hood", false).withPosition(2, 0).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
  private NetworkTableEntry dashRPM = shooterTab.add("Dash RPM", 0).withPosition(3, 0).getEntry();

  private NetworkTableEntry dashSetRPM = shooterTab.add("Set RPM", 0).withPosition(0, 1).getEntry();
  private NetworkTableEntry dashCurrentRPM = shooterTab.add("Current RPM", 0).withPosition(1, 1).getEntry();
  private NetworkTableEntry dashPriming = shooterTab.add("Priming", false).withPosition(2, 1).getEntry();

  private NetworkTableEntry PID_P = shooterTab.addPersistent("PID P", Constants.PID.FlywheelConstants.kGains_Velocit.kP).withPosition(0, 2).getEntry();
  private NetworkTableEntry PID_I = shooterTab.addPersistent("PID I", Constants.PID.FlywheelConstants.kGains_Velocit.kI).withPosition(1, 2).getEntry();
  private NetworkTableEntry PID_D = shooterTab.addPersistent("PID D", Constants.PID.FlywheelConstants.kGains_Velocit.kD).withPosition(2, 2).getEntry();
  private NetworkTableEntry PID_F = shooterTab.addPersistent("PID F", Constants.PID.FlywheelConstants.kGains_Velocit.kF).withPosition(3, 2).getEntry();
  private NetworkTableEntry PID_Izone = shooterTab.addPersistent("PID I Range", Constants.PID.FlywheelConstants.kGains_Velocit.kIzone).withPosition(4, 2).getEntry();
  private NetworkTableEntry PID_MaxOutput = shooterTab.addPersistent("PID Peak Output", Constants.PID.FlywheelConstants.kGains_Velocit.kPeakOutput).withPosition(5, 2).getEntry();

  private NetworkTableEntry dashIdleRPM = shooterTab.addPersistent("Idle Flywheel", Constants.ShooterSettings.idleFlywheelSpeed).withPosition(0, 3).getEntry();
  private NetworkTableEntry dashIdleHood = shooterTab.addPersistent("Idle Hood", Constants.ShooterSettings.idleHoodSet).withPosition(1, 3).getEntry();

  public static Orchestra orchestra;
  /** Creates a new Shooter. */
  public Shooter() {
    leftFlywheel.setInverted(true);

    leftFlywheel.configFactoryDefault();
    rightFlywheel.configFactoryDefault();

    leftFlywheel.configNeutralDeadband(0.001);
    rightFlywheel.configNeutralDeadband(0.001);

    leftFlywheel.configNominalOutputForward(0, Constants.PID.FlywheelConstants.kTimeoutMs);
		leftFlywheel.configNominalOutputReverse(0, Constants.PID.FlywheelConstants.kTimeoutMs);
		leftFlywheel.configPeakOutputForward(1, Constants.PID.FlywheelConstants.kTimeoutMs);
    leftFlywheel.configPeakOutputReverse(-1, Constants.PID.FlywheelConstants.kTimeoutMs);
    rightFlywheel.configNominalOutputForward(0, Constants.PID.FlywheelConstants.kTimeoutMs);
		rightFlywheel.configNominalOutputReverse(0, Constants.PID.FlywheelConstants.kTimeoutMs);
		rightFlywheel.configPeakOutputForward(1, Constants.PID.FlywheelConstants.kTimeoutMs);
    rightFlywheel.configPeakOutputReverse(-1, Constants.PID.FlywheelConstants.kTimeoutMs);
    
    leftFlywheel.config_kF(Constants.PID.FlywheelConstants.kPIDLoopIdx, Constants.PID.FlywheelConstants.kGains_Velocit.kF, Constants.PID.FlywheelConstants.kTimeoutMs);
		leftFlywheel.config_kP(Constants.PID.FlywheelConstants.kPIDLoopIdx, Constants.PID.FlywheelConstants.kGains_Velocit.kP, Constants.PID.FlywheelConstants.kTimeoutMs);
		leftFlywheel.config_kI(Constants.PID.FlywheelConstants.kPIDLoopIdx, Constants.PID.FlywheelConstants.kGains_Velocit.kI, Constants.PID.FlywheelConstants.kTimeoutMs);
		leftFlywheel.config_kD(Constants.PID.FlywheelConstants.kPIDLoopIdx, Constants.PID.FlywheelConstants.kGains_Velocit.kD, Constants.PID.FlywheelConstants.kTimeoutMs);
   
    rightFlywheel.config_kF(Constants.PID.FlywheelConstants.kPIDLoopIdx, Constants.PID.FlywheelConstants.kGains_Velocit.kF, Constants.PID.FlywheelConstants.kTimeoutMs);
		rightFlywheel.config_kP(Constants.PID.FlywheelConstants.kPIDLoopIdx, Constants.PID.FlywheelConstants.kGains_Velocit.kP, Constants.PID.FlywheelConstants.kTimeoutMs);
		rightFlywheel.config_kI(Constants.PID.FlywheelConstants.kPIDLoopIdx, Constants.PID.FlywheelConstants.kGains_Velocit.kI, Constants.PID.FlywheelConstants.kTimeoutMs);
    rightFlywheel.config_kD(Constants.PID.FlywheelConstants.kPIDLoopIdx, Constants.PID.FlywheelConstants.kGains_Velocit.kD, Constants.PID.FlywheelConstants.kTimeoutMs);
    
    //music code
    ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();
    instruments.add(leftFlywheel);
    instruments.add(rightFlywheel);
    orchestra = new Orchestra(instruments);
  }

  @Override
  public void periodic() {
    shooterDash();
    updatePID();
    setCommand();
  }

  public void shooterDash(){
    prime = getPrime();
    currentRPM = getRPM();

    Pconstant = PID_P.getDouble(0);
    Iconstant = PID_I.getDouble(0);
    Dconstant = PID_D.getDouble(0);
    Fconstant = PID_F.getDouble(0);
    I_Zone = (int)PID_Izone.getDouble(0);
    MaxOutput = PID_MaxOutput.getDouble(0);

    manualShoot = dashShootManual.getBoolean(false);
    manualHood = dashHood.getBoolean(false);
    manualRPM = dashRPM.getDouble(0);

    dashSetRPM.setDouble(setRPM);
    dashCurrentRPM.setDouble(currentRPM);
    dashPriming.setBoolean(prime);

    tunePID = dashTunePid.getBoolean(Constants.ShooterSettings.tunePID);

    idleFly = dashIdleRPM.getDouble(Constants.ShooterSettings.idleFlywheelSpeed);
    idleHood = dashIdleHood.getBoolean(Constants.ShooterSettings.idleHoodSet);
  }

  public void updatePID(){
    if(tunePID == true){
      Gains newGains = new Gains(Pconstant, Iconstant, Dconstant, Fconstant, I_Zone, MaxOutput);

      Robot.shooter.leftFlywheel.config_kF(Constants.PID.FlywheelConstants.kPIDLoopIdx, newGains.kF, Constants.PID.FlywheelConstants.kTimeoutMs);
      Robot.shooter.leftFlywheel.config_kP(Constants.PID.FlywheelConstants.kPIDLoopIdx, newGains.kP, Constants.PID.FlywheelConstants.kTimeoutMs);
      Robot.shooter.leftFlywheel.config_kI(Constants.PID.FlywheelConstants.kPIDLoopIdx, newGains.kI, Constants.PID.FlywheelConstants.kTimeoutMs);
      Robot.shooter.leftFlywheel.config_kD(Constants.PID.FlywheelConstants.kPIDLoopIdx, newGains.kD, Constants.PID.FlywheelConstants.kTimeoutMs);
    
      Robot.shooter.rightFlywheel.config_kF(Constants.PID.FlywheelConstants.kPIDLoopIdx, newGains.kF, Constants.PID.FlywheelConstants.kTimeoutMs);
      Robot.shooter.rightFlywheel.config_kP(Constants.PID.FlywheelConstants.kPIDLoopIdx, newGains.kP, Constants.PID.FlywheelConstants.kTimeoutMs);
      Robot.shooter.rightFlywheel.config_kI(Constants.PID.FlywheelConstants.kPIDLoopIdx, newGains.kI, Constants.PID.FlywheelConstants.kTimeoutMs);
      Robot.shooter.rightFlywheel.config_kD(Constants.PID.FlywheelConstants.kPIDLoopIdx, newGains.kD, Constants.PID.FlywheelConstants.kTimeoutMs);
    }
  }

  public void setCommand(){
    if(manualShoot || prime)
    {
      new Prime();
    }
    else
    {
      new IdleShooter();
    }
  }

  public void stopAll(){
    setRPM = 0;
    prime = false;
    leftFlywheel.set(TalonFXControlMode.Velocity, 0);
    rightFlywheel.set(TalonFXControlMode.Velocity, 0);
    hoodSolenoid.set(false);
  }

  public void idleShoot(){
    setRPM = idleFly;
    prime = false;
    leftFlywheel.set(TalonFXControlMode.Velocity, idleFly);
    rightFlywheel.set(TalonFXControlMode.Velocity, idleFly);
    hoodSolenoid.set(idleHood);
  }

  public void autoShoot(){
    setRPM = autoRPM;
    prime = true;
    leftFlywheel.set(TalonFXControlMode.Velocity, autoRPM);
    rightFlywheel.set(TalonFXControlMode.Velocity, autoRPM);
    hoodSolenoid.set(autoHood);
    
  }

  public void manualShoot(){
    setRPM = manualRPM;
    prime = true;
    leftFlywheel.set(TalonFXControlMode.Velocity, manualRPM);
    rightFlywheel.set(TalonFXControlMode.Velocity, manualRPM);
    hoodSolenoid.set(manualHood);
  }

  public double getRPM(){
    return leftFlywheel.getSelectedSensorVelocity();
  }

  public boolean getPrime(){
    return Robot.m_robotContainer.getManipulatorButton(Constants.OI.yButtonID);
  }

  public boolean getManual(){
    return manualShoot;
  }

  public void play(){
    orchestra.loadMusic("Candy.chrp");
    orchestra.play();
  }

}
