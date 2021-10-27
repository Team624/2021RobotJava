// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Prime extends CommandBase {

  private double camDistance;
  /** Creates a new Prime. */
  public Prime() {
    addRequirements(Robot.m_robotContainer.shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    getCamVal();
    calcAuto();
    shootLogic();
  }

  private void getCamVal(){
    camDistance = Robot.m_robotContainer.shooter.getCamVal();
  }

  public void shootLogic(){
    if(Robot.m_robotContainer.shooter.getManual())
    {
      Robot.m_robotContainer.shooter.manualShoot();
    }
    else
    {
      Robot.m_robotContainer.shooter.autoShoot();
    }
  }

  public void calcAuto(){
    
    if(camDistance <= 5.7){
      Robot.m_robotContainer.shooter.setAutoStates(6475, false);
    }else if(camDistance <= 5.75 && camDistance > 5.7){
      Robot.m_robotContainer.shooter.setAutoStates(6500, false);
    }else if(camDistance <= 5.91 && camDistance > 5.75){
      Robot.m_robotContainer.shooter.setAutoStates(6550, false);
    }else if(camDistance <= 6.14 && camDistance > 5.91){
      Robot.m_robotContainer.shooter.setAutoStates(6650, false);
    }else if(camDistance <= 6.39 && camDistance > 6.14){
      Robot.m_robotContainer.shooter.setAutoStates(6825, false);
    }else if(camDistance <= 6.65 && camDistance > 6.39){
      Robot.m_robotContainer.shooter.setAutoStates(7000, false);
    }else if(camDistance <= 6.94 && camDistance > 6.65){
      Robot.m_robotContainer.shooter.setAutoStates(8500, true);
    }else if(camDistance <= 7.24 && camDistance > 6.94){
      Robot.m_robotContainer.shooter.setAutoStates(8250, true);
    }else if(camDistance <= 7.57 && camDistance > 7.24){
      Robot.m_robotContainer.shooter.setAutoStates(8000, true);
    }else if(camDistance <= 7.85 && camDistance > 7.57){
      Robot.m_robotContainer.shooter.setAutoStates(8000, true);
    }else if(camDistance <= 8.2 && camDistance > 7.85){
      Robot.m_robotContainer.shooter.setAutoStates(8100, true);
    }else if(camDistance <= 8.565 && camDistance > 8.2){
      Robot.m_robotContainer.shooter.setAutoStates(8175, true);
    }else if(camDistance <= 8.87 && camDistance > 8.565){
      Robot.m_robotContainer.shooter.setAutoStates(8200, true);
    }else if(camDistance <= 9.26 && camDistance > 8.87){
      Robot.m_robotContainer.shooter.setAutoStates(8250, true);
    }else if(camDistance <= 9.66 && camDistance > 8.87){
      Robot.m_robotContainer.shooter.setAutoStates(8300, true);
    }else if(camDistance <= 10.05 && camDistance > 9.66){
      Robot.m_robotContainer.shooter.setAutoStates(8350, true);
    }else if(camDistance <= 10.401 && camDistance > 10.05){
      Robot.m_robotContainer.shooter.setAutoStates(8400, true);
    }else if(camDistance <= 10.71 && camDistance > 10.401){
      Robot.m_robotContainer.shooter.setAutoStates(8450, true);
    }else if(camDistance <= 11.04 && camDistance > 10.05){
      Robot.m_robotContainer.shooter.setAutoStates(8500, true);
    }else if(camDistance <= 11.337 && camDistance > 11.04){
      Robot.m_robotContainer.shooter.setAutoStates(8550, true);
    }else if(camDistance <= 11.59 && camDistance > 11.337){
      Robot.m_robotContainer.shooter.setAutoStates(8600, true);
    }else if(camDistance <= 11.86 && camDistance > 11.59){
      Robot.m_robotContainer.shooter.setAutoStates(8650, true);
    }else if(camDistance <= 12.2 && camDistance > 11.86){
      Robot.m_robotContainer.shooter.setAutoStates(8700, true);
    }else if(camDistance <= 12.55 && camDistance > 12.2){
      Robot.m_robotContainer.shooter.setAutoStates(8750, true);
    }else if(camDistance <= 12.9221 && camDistance > 12.55){
      Robot.m_robotContainer.shooter.setAutoStates(8850, true);
    }else if(camDistance <= 13.24 && camDistance > 12.9221){
      Robot.m_robotContainer.shooter.setAutoStates(8900, true);
    }else if(camDistance <= 13.62 && camDistance > 13.24){
      Robot.m_robotContainer.shooter.setAutoStates(9150, true);
    }else if(camDistance <= 13.92 && camDistance > 13.62){
      Robot.m_robotContainer.shooter.setAutoStates(9200, true);
    }else if(camDistance <= 14.13 && camDistance > 13.92){
      Robot.m_robotContainer.shooter.setAutoStates(9300, true);
    }else if(camDistance <= 14.32 && camDistance > 14.13){
      Robot.m_robotContainer.shooter.setAutoStates(9450, true);
    }else if(camDistance <= 14.57 && camDistance > 14.32){
      Robot.m_robotContainer.shooter.setAutoStates(9600, true);
    }else{
      Robot.m_robotContainer.shooter.setAutoStates(9700, true);

    }
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
