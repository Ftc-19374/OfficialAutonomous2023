package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

// @Autonomous
/*
hi other teams


this is a basic image prossing code that we used
For the 23-24 FTC season by team 4234
the code has stripped of all our unquine things
and so you can take this logic to your Auto
This logic uses OpenCV and it fuctions to draw box and look
for the avagre colors in these boxs,
We use this by steing the boxes up for our camera placement
then these box can look for both blue and red

the vaule that our out put, with nothing is around 120
and with the prop is 150+ (depending how much the color fill the box)

we have learned to use a lot of Try cath blocks, which if there is a error will handle it
in our past testing there was a lot off errors ,
but this version shoulnt crash

*/

class thisImageProcessor extends OpenCvPipeline {
    Mat YCbCr = new Mat();
    Mat area1;
    Mat area2;
    Mat area3;
    Mat output = new Mat();
    int colorChanel = 2; // this is where you say if its look for red or blue
    // 2 is best for blue, 1 is best for red
    Scalar rectColor1 = new Scalar(0,0,255);
    Scalar rectColor2 = new Scalar(255,0,0);
    Scalar rectColor3 = new Scalar(0,255,0);
    double area1avg;
    double area2avg;
    double area3avg;
    public String ErrMessage = "";

    Rect area1Rect = new Rect(1,1,200,360);
    Rect area2Rect = new Rect(200,1,200,360);
    Rect area3Rect = new Rect(400,1,200,360);

    public void setColorChanel(int inCh){
        colorChanel = inCh;
    }

    public void setAreaRectangle(int areaNumber, int x, int y, int width, int height){
        if(areaNumber == 1){
            area1Rect = new Rect(x,y,width, height);
        } else if(areaNumber == 2){
            area2Rect = new Rect(x,y,width, height);
        } else{
            area3Rect = new Rect(x,y,width, height);
        }
    }

    public double getAreaAverage(int areaNumber){
        if(areaNumber == 1){
            return area1avg;
        } else if(areaNumber == 2){
            return area2avg;
        } else{
            return area3avg;
        }
    }

    public Mat processFrame(Mat input){
        try{
            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
            //SendMessage("pipline running");

            // Show to display screen
            input.copyTo(output);
            Imgproc.rectangle(output, area1Rect, rectColor1,  4);
            Imgproc.rectangle(output, area2Rect, rectColor2, 4);
            Imgproc.rectangle(output, area3Rect, rectColor3, 4);

            area1 = YCbCr.submat(area1Rect);
            area2 = YCbCr.submat(area2Rect);
            area3 = YCbCr.submat(area3Rect);

            // Extract REd Color Channel, change with third parameter
            // Blue = 1,  Red = 2
            Core.extractChannel(area1, area1, colorChanel);
            Core.extractChannel(area2, area2, colorChanel);
            Core.extractChannel(area3, area3, colorChanel);

            area1avg = Core.mean(area1).val[0];
            area2avg = Core.mean(area2).val[0];
            area3avg = Core.mean(area3).val[0];
        } catch (Exception err){
            ErrMessage = "ERror:";
        }

        return  output;
    }
}






