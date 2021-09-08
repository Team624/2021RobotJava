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
  
  private boolean manualShoot;
  private boolean setHood;
  private double setRPM;
  private double currentRPM;
  private boolean prime;

  private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");

  private NetworkTableEntry shootManual = shooterTab.add("Shoot Manual", false).getEntry();
  private NetworkTableEntry manualHood = shooterTab.add("Set Hood", false).getEntry();
  private NetworkTableEntry manualFlywheel = shooterTab.add("Set RPM", 0).getEntry();

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
    currentRPM = getRPM();
    prime = Robot.m_robotContainer.getManipulatorButton(Constants.OI.yButtonID);
    flywheelDash();
    while(manualShoot == true && prime == true){
      new ManualShoot();
    }
    // This method will be called once per scheduler run
  }

  public void flywheelDash(){
    manualShoot = shootManual.getBoolean(false);
    setHood = manualHood.getBoolean(false);
    setRPM = manualFlywheel.getDouble(0);
    shooterTab.add("Flywheel RPM", currentRPM);
    shooterTab.add("Flywheel Set RPM", setRPM);
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

  public void play(){
    orchestra.loadMusic("Candy.chrp");
    orchestra.play();
  }

}
