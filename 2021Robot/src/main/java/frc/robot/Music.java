package frc.robot;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Subsytems.Shooter;

public class Music {
    Orchestra _orchestra;
    TalonFX [] _fxes =  {};

    public void musicLoop(Joystick controller){
        if(controller.getRawButton(5)){
            play();
        }
    }

    public void assignInstruments(Shooter shooter){
        _fxes[0] = shooter.leftFlywheel;
        _fxes[1] = shooter.rightFlywheel;
    }

    private void play(){
        _orchestra.loadMusic("Pirate.chrp");
    }

}
