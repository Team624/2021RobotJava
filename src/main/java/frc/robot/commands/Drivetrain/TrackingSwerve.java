// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.Robot;

public class TrackingSwerve extends CommandBase {

  private double rot;
  private double measurement;

  private final PIDController m_trackingPIDController =
      new PIDController(Constants.PID.SwervePIDConstants.kPTrackingController, Constants.PID.SwervePIDConstants.kITrackingController, 0);

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("SmartDashboard");

  /** Creates a new TrackingSwerve. */
  public TrackingSwerve() {
    addRequirements(Robot.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(rot);
    measurement = table.getEntry("-turret-feedback").getDouble(0);
    rot = -m_trackingPIDController.calculate(measurement, 0.0);
    
    Robot.drivetrain.drive(Robot.m_robotContainer.GetDriverRawAxis(1), -Robot.m_robotContainer.GetDriverRawAxis(0), rot + Robot.m_robotContainer.GetDriverRawAxis(4), true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
