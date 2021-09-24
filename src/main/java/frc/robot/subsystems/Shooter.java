// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants;
import frc.robot.Robot;
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
  //Should hood be up right now?
  private boolean setHood;
  //What should rpm be right now?
  private double setRPM;
  //Does dash say to put hood up while manual shooting?
  private boolean manualHood;
  //What RPM does the manual dash say?
  private double manualRPM;
  //What is rpm right now?
  private double currentRPM;
  //Should we be priming right now?
  private boolean prime;

  private boolean tunePID;

  private double Pconstant;
  private double Iconstant;
  private double Dconstant;
  private double Fconstant;
  private int I_Zone;
  private double MaxOutput;

  private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");

  private NetworkTableEntry dashTunePid = shooterTab.add("Tune PID", false).withPosition(0, 0).getEntry();
  
  private NetworkTableEntry dashShootManual = shooterTab.add("Shoot Manual", false).withPosition(1, 0).getEntry();
  private NetworkTableEntry dashHood = shooterTab.add("Dash Hood", false).withPosition(2, 0).getEntry();
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

  public static Orchestra orchestra;
  /** Creates a new Shooter. */
  public Shooter() {
    leftFlywheel.setInverted(true);

    //music code
    ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();
    instruments.add(leftFlywheel);
    instruments.add(rightFlywheel);
    orchestra = new Orchestra(instruments);
  }


  @Override
  public void periodic() {
    getStatus();
    shooterDash();
    updatePID();
  }

  public void getStatus(){
    prime = getPrime();
    currentRPM = getRPM();
  }

  public void shooterDash(){
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

    tunePID = dashTunePid.getBoolean(false);
  }

  public void updatePID(){
    if(tunePID == true){
      //bruh my dumbass can fix this sometime
      Gains newGains = new Gains(getP(), getI(), getD(), getF(), getIzone(), getMaxOutput());

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

  public void stopAll(TalonFXControlMode mode){
    stopFlywheel(mode);
    autoActuate(false);
  }

  public void stopFlywheel(TalonFXControlMode mode){
    leftFlywheel.set(mode, 0);
    rightFlywheel.set(mode, 0);
  }

  public void autoFlywheel(TalonFXControlMode mode, double speed){
    setRPM = speed;
    leftFlywheel.set(mode, speed);
    rightFlywheel.set(mode, speed);
  }

  public void manualFlywheel(TalonFXControlMode mode){
    setRPM = manualRPM;
    leftFlywheel.set(mode, manualRPM);
    rightFlywheel.set(mode, manualRPM);
  }

  public void manualActuate(){
    hoodSolenoid.set(manualHood);
  }

  public void autoActuate(boolean actuate){
    hoodSolenoid.set(actuate);
  }

  public boolean getPrime(){
    return Robot.m_robotContainer.getManipulatorButton(Constants.OI.yButtonID);
  }

  public double getRPM(){
    return leftFlywheel.getSelectedSensorVelocity();
  }

  public double getSetRPM(){
    return setRPM;
  }

  public boolean getSetHood(){
    return setHood;
  }

  public boolean getDashHood(){
    return manualHood;
  }

  public double getDashRPM(){
    return manualRPM;
  }

  public boolean getManualShoot(){
    return manualShoot;
  }

  public double getP(){
    return Pconstant;
  }

  public double getI(){
    return Iconstant;
  }

  public double getD(){
    return Dconstant;
  }

  public double getF(){
    return Fconstant;
  }

  public int getIzone(){
    return I_Zone;
  }
  public double getMaxOutput(){
    return MaxOutput;
  }

  public void play(){
    orchestra.loadMusic("Candy.chrp");
    orchestra.play();
  }

}
