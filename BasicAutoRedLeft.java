/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import static java.lang.Math.round;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection, using
 * the easiest way.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "Blue Left OFFICIAL")
public class BasicAutoRedLeft extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    //Declare motors and other variables
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor lift;
    DcMotor intake;
    Servo pixelDropper;
    private ElapsedTime runtime = new ElapsedTime();
    // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;
    boolean objectDetected = false;
    int loopLength = 100;

    /**
     * The variable to store our instance of the vision portal.
     */

    @Override
    public void runOpMode() throws InterruptedException {

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        frontLeft = hardwareMap.dcMotor.get("Fl_motor");
        frontRight = hardwareMap.dcMotor.get("Fr_motor");
        backLeft = hardwareMap.dcMotor.get("Bl_motor");
        backRight = hardwareMap.dcMotor.get("Br_motor");
        lift = hardwareMap.dcMotor.get("Lift_1");
        intake = hardwareMap.dcMotor.get("Intake_Motor_1");
        pixelDropper = hardwareMap.servo.get("Box_Servo");

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

        if (opModeIsActive()) {
            /*while (opModeIsActive()) {

                telemetryTfod();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }


                // Share the CPU.
                sleep(20);
            }
         */ Movement(1,1,1,1,0,5,0.5);
            while (opModeIsActive() && loopLength > 0) {
                telemetryTfod();
                // Push telemetry to the Driver Station.
                telemetry.update();

                loopLength -= 1;
                // Share the CPU.
                sleep(20);
            }
            if(objectDetected){
                Movement(1.5f,1.5f,1.5f,1.5f,0,5,0.5);
                intake.setPower(-0.35);
                sleep(2000);
                intake.setPower(0);
                Movement(1,1,1,1,0,5,0.5);
                Movement(-1.515f, 1.515f, -1.515f, 1.515f, 0,5, 0.5);
                Movement(1.75f,1.75f,1.75f,1.75f,0,5,0.5);
                Movement(2.6f,-2.6f,-2.6f,2.6f,0,5,0.5);
                Movement(0.1f,0.1f,0.1f,0.1f,0,5,0.5);
                //insert placement here
                Movement(0,0,0,0,-1800,5,1);
                pixelDropper.setPosition(0.5);
                sleep(1000);
                pixelDropper.setPosition(1);
                sleep(200);
                Movement(-0.1f,-0.1f,-0.1f,-0.1f,0,5,0.5);
                Movement(0,0,0,0,1800,5,1);
                Movement(1,-1,-1,1,0,5,0.5);

            } else{
                Movement(-0.85f,0.85f,0.85f,-0.85f,0,5,0.5);
                Movement(0.3f,0.3f,0.3f,0.3f,0,5,0.5);
                loopLength = 100;
                while (opModeIsActive() && loopLength > 0) {
                    telemetryTfod();
                    // Push telemetry to the Driver Station.
                    telemetry.update();

                    loopLength -= 1;
                    // Share the CPU.
                    sleep(20);
                }
                if(objectDetected){
                    Movement(1.9f,1.9f,1.9f,1.9f,0,5,0.5);
                    intake.setPower(-0.35);
                    sleep(2000);
                    intake.setPower(0);
                    Movement(0.5f,0.5f,0.5f,0.5f,0,5,0.5);
                    Movement(-1.52f, 1.52f, -1.52f, 1.52f, 0,5, 0.5);
                    Movement(2.5f,2.5f,2.5f,2.5f,0,5,0.5);
                    Movement(2.5f,-2.5f,-2.5f,2.5f,0,5,0.5);
                    Movement(0.15f,0.15f,0.15f,0.15f,0,5,0.5);
                    //insert placement here
                    Movement(0,0,0,0,-1800,5,1);
                    pixelDropper.setPosition(0.5);
                    sleep(1000);
                    pixelDropper.setPosition(1);
                    sleep(200);
                    Movement(-0.15f,-0.15f,-0.15f,-0.15f,0,5,0.5);
                    Movement(0,0,0,0,1800,5,1);
                    Movement(1,-1,-1,1,0,5,0.5);
                } else {
                    Movement(-1.52f, 1.52f, -1.52f, 1.52f, 0,5, 0.5);
                    Movement(-0.8f,0.8f,0.8f,-0.8f,0,5,0.5);
                    Movement(0.2f,0.2f,0.2f,0.2f,0,5,0.5);
                    intake.setPower(-0.35);
                    sleep(2000);
                    intake.setPower(0);
                    Movement(2f,2f,2f,2f,0,5,0.5);
                    Movement(0.3f, -0.3f, 0.3f, -0.3f, 0,5, 0.5);
                    Movement(0.1f,0.1f,0.1f,0.1f,0,5,0.5);
                    Movement(0,0,0,0,-1800,5,1);
                    pixelDropper.setPosition(0.5);
                    sleep(1000);
                    pixelDropper.setPosition(1);
                    sleep(200);
                    Movement(-0.15f,-0.15f,-0.15f,-0.15f,0,5,0.5);
                    Movement(0,0,0,0,1800,5,1);
                    Movement(-1.5f,1.5f,1.5f,-1.5f,0,5,0.5);
                }
            }

        }


        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
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
    private void initTfod() {

        // Create the TensorFlow processor the easy way.
        tfod = TfodProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), tfod);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, tfod);
        }

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            if(recognition.getLabel() == "Pixel"){
                objectDetected = true;
            }
        }   // end for() loop

    }   // end method telemetryTfod()

}   // end class
