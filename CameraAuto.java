package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.sql.Struct;

@Config
@Autonomous
public class CameraAuto extends LinearOpMode {
    public thisImageProcessor pipe;

    public OpenCvWebcam webcam1 = null;
    // public String displayMessage = ""; send message is somthing we used to debug
    //  public String currentMulti = "";  things that we used that are unquine
    //  public int currentMultiStep = 0;
    //public baseRobotClass myRobot;

    public void setUpWebCam() {



        try

        {
            pipe = new thisImageProcessor();
            //SendMessage("Pipe line is set up", false);
            pipe.setAreaRectangle(1, 100, 100, 300, 100);
            pipe.setAreaRectangle(2, 500, 75, 300, 100); // sets up the boxs and where they are
            pipe.setAreaRectangle(3, 900, 100, 300, 100);

            // This gets the Camera name object, and the drive station viewer and sets up the webcam.
            WebcamName myCamera = hardwareMap.get(WebcamName.class, "webcam1");
            int cameraID = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            webcam1 = OpenCvCameraFactory.getInstance().createWebcam(myCamera, cameraID);
            webcam1.setPipeline(pipe);
            webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                public void onOpened() {
                    webcam1.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
                }

                public void onError(int errorCode) {

                }
            });
        } catch(Exception e) {
            // Do nothing.....
        }

    }




    public static int colorChannel = 2;// 2 is best for blue, 1 is best for red
    public static int[] box1 = {100,100,300,100};
    public static int[] box2 = {500,75,300,100};
    public static int[] box3 = {900,100,300,100};
    public static int colorMargin = 40;

    public double box1Value;
    public double box2Value;
    public double box3Value;

// box1 100,

    @Override
    public void runOpMode() throws InterruptedException {

        // Webcam should be named:  "webcam1"



        pipe.setColorChanel(colorChannel);

        //waitForStart();
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
            telemetry.addLine("Area 1 is greater than others!");

        }else if (box2Value > box1Value + colorMargin && box2Value > box3Value + colorMargin){
            telemetry.addLine("Area 2 is greater than others!");

        }else if (box3Value > box1Value + colorMargin && box3Value > box2Value + colorMargin){
            telemetry.addLine("Area 3 is greater than others!");

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
    public enum regionDetected{
       LEFT, CENTER, RIGHT
    }
}