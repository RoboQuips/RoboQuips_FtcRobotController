package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Mecanum TeleOp", group = "TeleOp")
public class HelloWorldMain extends OpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight, ballMotor, sweepMotor;
    @Override
    public void init() {
        //balllauncher motor
        ballMotor = hardwareMap.get(DcMotor.class, "ballDestroyerMotor");
        ballMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //motormaster21 sweeps up ball
        sweepMotor = hardwareMap.get(DcMotor.class, "motormaster21");
       sweepMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

        // Make motors stop completely when joystick is released
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        //BALL LAUNCHER
        // ? = simple if/and statement
        double ballmotorspeed = gamepad1.a ? 1.0 : 0.0 ;
        ballMotor.setPower(ballmotorspeed * 1);

        //BALL sweeper
        // ? = simple if/and statement
        double sweepmotorspeed = gamepad1.b ? 1.0 : 0.0 ;
        sweepMotor.setPower(sweepmotorspeed * 1);

        //MECANUM DRIVETRAIN
        // Get joystick values
        double y = gamepad1.left_stick_y;     // Forward/Backward
        double x = -gamepad1.left_stick_x;    // Strafe Left/Right
        double rx = -gamepad1.right_stick_x;  // Rotate Left/Right

        // Apply a small deadzone to ignore tiny joystick movements
        double deadzone = 0.05;
        if (Math.abs(y) < deadzone) y = 0;
        if (Math.abs(x) < deadzone) x = 0;
        if (Math.abs(rx) < deadzone) rx = 0;

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
