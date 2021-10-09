package main.java.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.ArrayList;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Climber
{
    private boolean done=false;
    private CANSparkMax mainMotor=new CANSparkMax(Constants.CAN.LeftHopperID, MotorType.kBrushless);
    private double speed;
    private ShuffleboardTab hopperTab = Shuffleboard.getTab("Hopper");
    private NetworkTableEntry dashSetClimbSpeed = hopperTab.addPersistent("Set Speed", 0).getEntry();
    private ArrayList<Double> speedHistory=new ArrayList<Double>();
    public Climber(double speed)
    {
        this.speed=speed;
    }
    public void periodic() //idk what this does
    {
        climbDash();
    }
    public boolean processCompleted()
    {
        return done;
    }
    public void stop()
    {
        setSpeed(0);
    }
    public boolean setSpeedToZero(){
        //this method is a basicallt stop() method, but it ensures that the process is over
        if(processCompleted())
        {
            stop();
            return true;
        }
        else
        {
            return false;
        }
    }
    public void setSpeed(int newSpeed)
    {
        mainMotor.set(newSpeed);
    }
    public double getSpeed()
    {
        return speed;
    }
    public void progressChange()
    {
        done=!done;
    }
    public void changeDirection(boolean forward)
    {
        if(forward)
        {
            mainMotor.set(speed);
        }
        else
        {
            mainMotor.set(-speed);
        }
    }
    public void climbDash()//idk what this does
    {
        climbSpeed = dashSetClimbSpeed.getDouble(0);
    }
    public void setSpeed(double newSpeed)
    {   
        speedHistory.add(speed);
        speed=newSpeed;
    }
    public double getLastSpeed()
    {   
        if(speedHistory.size()>0)
        {
        return speedHistory.get(speedHistory.size()-1);
        }
        else
        {
        return -1;
        }
    }
}