package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {
    private DcMotor ballMotor;

    public void init(HardwareMap hwMap) {

        ballMotor = hwMap.get(DcMotor.class, "ballDestroyerMotor");
        ballMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
        public void setMotorSpeed(double speed) {
        ballMotor.setPower (speed);
    }

}
