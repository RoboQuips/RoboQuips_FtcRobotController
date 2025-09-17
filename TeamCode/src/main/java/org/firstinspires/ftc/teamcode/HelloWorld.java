package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@TeleOp
public class HelloWorld extends OpMode {

    @Override
    public void init() {
        int teamNumber = 30457;

        telemetry.addData("Team Number", teamNumber);
    }

    @Override
    public void loop() {
        double speedForward = -gamepad1.left_stick_y;

        telemetry.addData("x", gamepad1.left_stick_x);
        telemetry.addData("y", speedForward);
        telemetry.addData("a button", gamepad1.a);

    }
}
