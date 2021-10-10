// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

import frc.robot.commands.Feeder.Shoot;
import frc.robot.commands.Feeder.StopFeeder;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Feeder extends SubsystemBase {
  private final CANSparkMax feederMotor = new CANSparkMax(Constants.CAN.LeftHopperID, MotorType.kBrushless);

  private double feederSpeed = Constants.FeederSettings.feederSpeed;
  private double dashFeederSpeed;
  private boolean useDash;

  private ShuffleboardTab feederTab = Shuffleboard.getTab("Feeder");
  private NetworkTableEntry dashSetFeederSpeed = feederTab.addPersistent("Set Feeder Speed", false).withPosition(0, 1).getEntry();
  private NetworkTableEntry dashSpeed = feederTab.addPersistent("Feeder Speed", 0).withPosition(0, 1).getEntry();

  /** Creates a new Feeder. */
  public Feeder() {}

  @Override
  public void periodic() {
    feederDash();
    updateSpeed();
    updateCommand();
  }

  private void updateCommand() {
    if(Robot.m_robotContainer.GetManipulatorRawAxis(Constants.OI.RightTriggerID) > Constants.FeederSettings.shooterControlAxisThreshold){
      new Shoot();
    }else{
      new StopFeeder();
    }
  }

  public void feederDash() {
    dashFeederSpeed = dashSpeed.getDouble(0);
    useDash = dashSetFeederSpeed.getBoolean(false);
  }

  public void updateSpeed(){
    if(useDash){
      feederSpeed = dashFeederSpeed;
    }
  }

  public void feed(){
    feederMotor.set(feederSpeed);
  }

  public void stopFeeder(){
    feederMotor.stopMotor();
  }
}
