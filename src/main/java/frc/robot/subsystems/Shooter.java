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
import frc.robot.commands.Shooter.ManualShoot;

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

  private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
  //shows dashboard what the rpm should be
  private NetworkTableEntry dashSetRPM = shooterTab.add("Set RPM", 0).getEntry();
  //shows dashboard what the rpm is
  private NetworkTableEntry dashRPM = shooterTab.add("Current RPM", 0).getEntry();
  //gets from dashboard to manual shoot
  private NetworkTableEntry dashShootManual = shooterTab.add("Shoot Manual", false).getEntry();
  //gets from dashboard whether hood should be up while manual
  private NetworkTableEntry dashHood = shooterTab.add("Dash Hood", false).getEntry();
  //gets from dashboard what rpm should be while manually shooting
  private NetworkTableEntry dashFlywheel = shooterTab.add("Dash RPM", 0).getEntry();
  //tells dashboard if the robot is priming
  private NetworkTableEntry dashPriming = shooterTab.add("Priming", false).getEntry();

  public boolean test = true;

  public static Orchestra orchestra;
  /** Creates a new Shooter. */
  public Shooter() {
    flywheelDefaultDash();

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
  }

  public void getStatus(){
    prime = Robot.m_robotContainer.getManipulatorButton(Constants.OI.yButtonID);
    currentRPM = getRPM();
  }

  public void shooterDash(){
    manualShoot = dashShootManual.getBoolean(false);
    manualHood = dashHood.getBoolean(false);
    manualRPM = dashFlywheel.getDouble(0);
    dashSetRPM.setDouble(setRPM);
    dashRPM.setDouble(currentRPM);
    dashPriming.setBoolean(prime);
  }

  public void flywheelDefaultDash(){
    shooterTab.addPersistent("PID P", Constants.PID.FlywheelConstants.kGains_Velocit.kP);
    shooterTab.addPersistent("PID I", Constants.PID.FlywheelConstants.kGains_Velocit.kI);
    shooterTab.addPersistent("PID D", Constants.PID.FlywheelConstants.kGains_Velocit.kD);
    shooterTab.addPersistent("PID F", Constants.PID.FlywheelConstants.kGains_Velocit.kF);
    shooterTab.addPersistent("PID I Zone", Constants.PID.FlywheelConstants.kGains_Velocit.kIzone);
    shooterTab.addPersistent("Max Output", Constants.PID.FlywheelConstants.kGains_Velocit.kPeakOutput);
  }

  public void stopAll(TalonFXControlMode mode){
    stopFlywheel(mode);
    actuate(false);
  }

  public void stopFlywheel(TalonFXControlMode mode){
    leftFlywheel.set(mode, 0);
    rightFlywheel.set(mode, 0);
  }

  public void setFlywheel(TalonFXControlMode mode, double speed){
    setRPM = speed;
    leftFlywheel.set(mode, speed);
    rightFlywheel.set(mode, speed);
  }

  public void actuate(boolean actuate){
    hoodSolenoid.set(actuate);
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
    boolean val = false;
    if(prime == true && manualShoot == true){
      val = true;
    }
    return val;
  }

  public void play(){
    orchestra.loadMusic("Candy.chrp");
    orchestra.play();
  }

}
