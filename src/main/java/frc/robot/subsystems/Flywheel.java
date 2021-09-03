// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.CANPIDController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Flywheel extends SubsystemBase {
  public TalonFX leftFlywheel = new TalonFX(Constants.CAN.leftFlywheelID);
  public TalonFX rightFlywheel = new TalonFX(Constants.CAN.rightFlywheelID);
  //private final CANPIDController frontController = new CANPIDController(this.front_climber);
  //private final CANPIDController backController = new CANPIDController(this.back_climber);
  /** Creates a new Flywheel. */
  public Flywheel() {
    leftFlywheel.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void stopFlywheel(){
    leftFlywheel.set(ControlMode.PercentOutput, 0);
    rightFlywheel.set(ControlMode.PercentOutput, 0);
  }

  public void setFlywheel(double speed){
    leftFlywheel.set(ControlMode.PercentOutput, speed);
    rightFlywheel.set(ControlMode.PercentOutput, speed);
  }
}
