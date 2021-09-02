// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class SwerveDrive extends CommandBase {
  /** Creates a new SwerveDrive. */
  public SwerveDrive() {
    addRequirements(Robot.driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.driveTrain.setDrivePID(Robot.driveTrain.d_pidBackLeft);
    Robot.driveTrain.setDrivePID(Robot.driveTrain.d_pidBackRight);
    Robot.driveTrain.setDrivePID(Robot.driveTrain.d_pidFrontLeft);
    Robot.driveTrain.setDrivePID(Robot.driveTrain.d_pidFrontRight);

    Robot.driveTrain.setSteerPID(Robot.driveTrain.s_pidBackLeft);
    Robot.driveTrain.setSteerPID(Robot.driveTrain.s_pidBackRight);
    Robot.driveTrain.setSteerPID(Robot.driveTrain.s_pidFrontLeft);
    Robot.driveTrain.setSteerPID(Robot.driveTrain.s_pidFrontRight);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftStickY = Robot.m_robotContainer.getDriverRawAxis(Constants.OI.LeftStickYID);
    Robot.driveTrain.setSteers(leftStickY);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
