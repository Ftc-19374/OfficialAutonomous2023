package org.firstinspires.ftc.teamcode;

import static java.lang.Math.round;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "park blue (left)")
public class parkblueleft extends LinearOpMode {
    //Declare motors and other variables
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor lift;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("Fl_motor");
        frontRight = hardwareMap.dcMotor.get("Fr_motor");
        backLeft = hardwareMap.dcMotor.get("Bl_motor");
        backRight = hardwareMap.dcMotor.get("Br_motor");
        lift = hardwareMap.dcMotor.get("Lift_1");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        Park();
    }

    void Movement(float fL, float fR, float bL, float bR, float l, int timeoutS, double speed) {
        int ntfl;
        int ntfr;
        int ntbl;
        int ntbr;
        int ntl;


        if(opModeIsActive()){
            ntfl = frontLeft.getCurrentPosition() + round(fL*1000);
            ntfr = frontRight.getCurrentPosition() + round(fR*1000);
            ntbl = backLeft.getCurrentPosition() + round(bL*1000);
            ntbr = backRight.getCurrentPosition() + round(bR*1000);
            ntl = lift.getCurrentPosition() + round(l);
            frontLeft.setTargetPosition(ntfl);
            frontRight.setTargetPosition(ntfr);
            backLeft.setTargetPosition(ntbl);
            backRight.setTargetPosition(ntbr);
            lift.setTargetPosition(ntl);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed);
            lift.setPower(speed);

            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    ((frontLeft.isBusy() || frontRight.isBusy() ||
                            backLeft.isBusy() || backRight.isBusy() || lift.isBusy()))) {

                // Display it for the driver.
                telemetry.addData("Goal",  "Running to %7d :%7d :%7d :%7d :%7d", ntfl,  ntfr, ntbl, ntbr, ntl);
                telemetry.addData("Actual distance moved",  "Running at %7d :%7d :%7d :%7d :%7d",
                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition(), lift.getCurrentPosition());
                telemetry.addData("Speed", speed);
                telemetry.update();
            }


            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
            lift.setPower(0);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    void PlacePixel() {
        //Move to board using Movement()
        //Sweep horizontally a specific distance (half the width of the backdrop) until the correct april tag is detected
        //move corresponding motors to place pixel
    }

    void Park() {
        Movement(0.1f,0.1f,0.1f,0.1f,0,5,0.5);
        Movement(-1.7f,1.7f,1.7f, -1.7f, 0,5, 0.5);
    }
}
