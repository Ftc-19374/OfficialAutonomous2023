package org.firstinspires.ftc.teamcode;

import static java.lang.Math.round;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvWebcam;

public class BlueRightAutoRevised extends LinearOpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor lift;
    DcMotor intake;

    DcMotor droneLauncher;
    Servo pixelDropper;

    thisImageProcessor pipe;

    int colorChannel;

    int[] box1;
    int[] box2;
    int[] box3;

    double box1Value;
    double box2Value;
    double box3Value;

    int colorMargin;
    OpenCvWebcam webcam1;

    CameraAuto.regionDetected regionDetected = null;
    private ElapsedTime runtime = new ElapsedTime();

    private CameraAuto cameraAuto = new CameraAuto();

    @Override
    public void runOpMode() throws InterruptedException {
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
        droneLauncher = hardwareMap.dcMotor.get("Drone_Motor");
        pixelDropper = hardwareMap.servo.get("Box_Servo");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        droneLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        droneLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        cameraAuto.setUpWebCam();
        pipe = cameraAuto.pipe;
        colorChannel = CameraAuto.colorChannel;
        box1 = CameraAuto.box1;
        box2 = CameraAuto.box2;
        box3 = CameraAuto.box3;
        colorMargin = CameraAuto.colorMargin;


        pipe.setColorChanel(colorChannel);

        waitForStart();
        while (!isStarted() && !isStopRequested()) {
            pipe.setColorChanel(colorChannel);

            pipe.setAreaRectangle(1, box1[0], box1[1], box1[2], box1[3]);
            pipe.setAreaRectangle(2, box2[0], box2[1], box2[2], box2[3]);
            pipe.setAreaRectangle(3, box3[0], box3[1], box3[2], box3[3]);

            telemetry.addLine("Area1 Average:" + pipe.getAreaAverage(1));
            telemetry.addLine("Area2 Average:" + pipe.getAreaAverage(2));
            telemetry.addLine("Area3 Average:" + pipe.getAreaAverage(3));
            telemetry.addLine("Err Message:" + pipe.ErrMessage);
            telemetry.update();






            sleep(20);



        }

        box1Value = pipe.getAreaAverage(1);
        box2Value = pipe.getAreaAverage(2);
        box3Value = pipe.getAreaAverage(3);



        if (box1Value > box2Value + colorMargin && box1Value > box3Value + colorMargin){
            regionDetected = CameraAuto.regionDetected.LEFT;

        }else if (box2Value > box1Value + colorMargin && box2Value > box3Value + colorMargin){
            regionDetected = CameraAuto.regionDetected.CENTER;

        }else if (box3Value > box1Value + colorMargin && box3Value > box2Value + colorMargin){
            regionDetected = CameraAuto.regionDetected.RIGHT;

        }






        try{
            webcam1.closeCameraDevice(); // to stop the webcam to impove perforce in Tele Op
            webcam1.stopStreaming();
        } catch(Exception e){

        }
        if (isStopRequested()) return;

        long lastCycleMS = 0;
        int countCount = 0;



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
}
