package frc.robot;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Subsytems.Shooter;

public class Music {
    Orchestra _orchestra;
    TalonFX [] _fxes =  {};
    public void MusicLoop(Joystick controller, Shooter shooter){
        _fxes[0] = shooter.leftFlywheel;
        _fxes[1] = shooter.rightFlywheel;
        if(controller.getRawButton(5)){
            Play();
        }
    }
    private void Play(){
        _orchestra.loadMusic("Pirate.chrp");
    }
}
