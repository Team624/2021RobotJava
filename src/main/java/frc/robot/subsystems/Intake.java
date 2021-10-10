// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Intake extends SubsystemBase {

  private double dashSpeed;

  private ShuffleboardTab intakeTab = Shuffleboard.getTab("Intake");

  private NetworkTableEntry dashIntakeSpeed = intakeTab.add("Intake Speed", 0).withPosition(0, 0).getEntry();

  public TalonSRX intakeMotor = new TalonSRX(Constants.CAN.intakeMotorID);
  private final Solenoid intakeSolenoid = new Solenoid(Constants.Solenoid.intakeID);
  /** Creates a new Intake. */
  public Intake() {}

  @Override
  public void periodic() {
    hopperDash();
    // This method will be called once per scheduler run
  }

  public void hopperDash(){
    dashSpeed = dashIntakeSpeed.getDouble(0);
  }

  public void setIntake(double speed, boolean actuate){
    intakeSolenoid.set(actuate);
    intakeMotor.set(TalonSRXControlMode.PercentOutput, speed);
  }

  public void stopIntake(){
    intakeMotor.set(TalonSRXControlMode.PercentOutput, 0);
  }

  public double getDashSpeed(){
    return dashSpeed;
  }
}
