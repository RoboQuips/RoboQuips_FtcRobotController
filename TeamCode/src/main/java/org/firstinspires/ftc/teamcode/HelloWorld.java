package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Mecanum TeleOp", group = "TeleOp")
public class HelloWorld extends OpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight;

    @Override
    public void init() {
        // Map motors to hardware configuration
        frontLeft = hardwareMap.get(DcMotor.class, "motor67");
        frontRight = hardwareMap.get(DcMotor.class, "motor69");
        backLeft = hardwareMap.get(DcMotor.class, "motor68");
        backRight = hardwareMap.get(DcMotor.class, "motor70");

        // Reverse directions for correct mecanum movement
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        // Get joystick values
        double y = gamepad1.left_stick_y;     // Forward/Backward
        double x = -gamepad1.left_stick_x;      // Strafe Left/Right
        double rx = -gamepad1.right_stick_x;    // Rotate Left/Right

        // Calculate motor powers using mecanum wheel formulas
        double frontLeftPower = y + x + rx;
        double backLeftPower = y - x + rx;
        double frontRightPower = y - x - rx;
        double backRightPower = y + x - rx;

        // Normalize motor powers to not exceed 1.0
        double maxPower = Math.max(Math.abs(frontLeftPower),
                Math.max(Math.abs(backLeftPower),
                        Math.max(Math.abs(frontRightPower), Math.abs(backRightPower))));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            backLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backRightPower /= maxPower;
        }

        // Set motor powers
        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);

        // Telemetry for testing
        telemetry.addData("Front Left", frontLeftPower);
        telemetry.addData("Back Left", backLeftPower);
        telemetry.addData("Front Right", frontRightPower);
        telemetry.addData("Back Right", backRightPower);
        telemetry.update();
    }
}
