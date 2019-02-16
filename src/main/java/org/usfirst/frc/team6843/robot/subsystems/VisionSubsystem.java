/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.subsystems;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.opencv.core.CvType;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Never require this subsystem. Just come get the latest angle.
 */
public class VisionSubsystem extends Subsystem {
  public static final int IMAGE_WIDTH = 320;
  public static final int IMAGE_HEIGHT = 240;
  public static final double CAMERA_FOV = 23.35;
  public static final double CAMERA_OFFSET = 0.0;
  /** TODO Change this for 2019 robot! */
  public static final double BUMPER_FRONT_TO_CAMERA_INCHES = 12.0;

  private VisionThread visionThread;
  private Object targetDataLock = new Object();
  private double targetAngle = 0.0;
  private int targetPixelHeight = 0;
  private double targetInchesDistance = 0.0;
  private boolean targetDataGood = false;

  private int threadMiscount = 0;

  /**
   * Updates the dashboard with drive subsystem critical data.
   */
  public void updateDashboard() {
    SmartDashboard.putBoolean("Target data good", this.targetDataGood);
    SmartDashboard.putNumber("Target angle", this.targetAngle);
    SmartDashboard.putNumber("Target pixel height", this.targetPixelHeight);
    SmartDashboard.putNumber("Target inches distance", this.targetInchesDistance);
  }

  /**
   * Configures the camera and starts the vision pipeline processing thread.
   */
  public VisionSubsystem() {
    final UsbCamera visionCamera = CameraServer.getInstance().startAutomaticCapture(0);

    visionCamera.setFPS(20);
    visionCamera.setResolution(IMAGE_WIDTH, IMAGE_HEIGHT);
    visionCamera.setExposureManual(25);
    visionCamera.setBrightness(25);
    visionCamera.setWhiteBalanceManual(4500);

    visionThread = new VisionThread(visionCamera, new GripPipeline(), pipeline -> {
      try {
        double calcTurnAngle = 0.0;
        boolean foundAngle = false;
        int maxHeight = 0;
        // List<MatOfPoint> contours = pipeline.convexHullsOutput();
        List<MatOfPoint> contours = pipeline.filterContoursOutput();
        if (contours.size() >= 2) {
          SortedSet<LtoRSortableContour> sortedContours = new TreeSet<>();
          for (MatOfPoint mop : contours) {
            MatOfPoint2f contour_2f = new MatOfPoint2f();
            mop.convertTo(contour_2f, CvType.CV_32FC2);
            RotatedRect rrect = Imgproc.minAreaRect(contour_2f);
            Rect rect = rrect.boundingRect();
            sortedContours.add(new LtoRSortableContour(rrect, rect));
          }
          // Slight chance same x value contours eliminated a target.
          if (sortedContours.size() >= 2) {
            // Taking first good pair for now. Could optimized to most centered.
            LtoRSortableContour left = null;
            LtoRSortableContour right = null;
            for (LtoRSortableContour sortedContour : sortedContours) {
              if (left == null) {
                if (((sortedContour.rrect.angle > 10.0) && (sortedContour.rrect.angle < 30.0))
                    || ((sortedContour.rrect.angle > -80.0) && (sortedContour.rrect.angle < -60.0))) {
                  left = sortedContour;
                }
              } else {
                if (((sortedContour.rrect.angle > -30.0) && (sortedContour.rrect.angle < -10.0))
                    || ((sortedContour.rrect.angle > 60.0) && (sortedContour.rrect.angle < 80.0))) {
                  right = sortedContour;
                }
                // Either we have both or we have bad data.
                break;
              }
            }

            if (right != null) {
              foundAngle = true;
              final Rect r1 = left.rect;
              final Rect r2 = right.rect;
              final double centerX = (((r1.x + (r1.width / 2)) + ((r2.x + r2.width) - (r2.width / 2))) / 2);
              final double turnRatio = (centerX / (IMAGE_WIDTH / 2)) - 1;
              calcTurnAngle = (turnRatio * CAMERA_FOV) + CAMERA_OFFSET;
              maxHeight = Math.max(r1.height, r2.height);
            }
          }
        }

        if (foundAngle) {
          this.threadMiscount = 0;
          setTargetData(true, calcTurnAngle, maxHeight);
        } else {
          this.threadMiscount++;
        }

        if (this.threadMiscount > 5) {
          setTargetData(false, 0.0, 0);
        }
        SmartDashboard.putNumber("Target Miss Count", this.threadMiscount);
        VisionThread.sleep(100);
      } catch (Throwable e) {
        System.out.println("Vision thread exception");
        e.printStackTrace();
      }
    });
    visionThread.start();
  }

  /**
   * Returns true of the vision data from the thread represents good (seen and
   * processed) vision data.
   * 
   * @return true if we have good data.
   */
  public boolean isTargetDataGood() {
    synchronized (targetDataLock) {
      return this.targetDataGood;
    }
  }

  /**
   * If {@link VisionSubsystem#isTargetDataGood()} returns true, this method
   * returns the good target angle. Otherwise, it returns 0.0.
   * 
   * @return the good target angle or 0.0.
   */
  public double getTargetAngle() {
    synchronized (targetDataLock) {
      return this.targetAngle;
    }
  }

  /**
   * If {@link VisionSubsystem#isTargetDataGood()} returns true, this method
   * returns the good pixel height of the taller of the two tapes. Otherwise, it
   * returns 0.
   * 
   * @return the good pixel height or 0.
   */
  public int getTargetPixelHeight() {
    synchronized (targetDataLock) {
      return this.targetPixelHeight;
    }
  }

  /**
   * If {@link VisionSubsystem#isTargetDataGood()} returns true, this method
   * returns a rough approximation of the target distance in inches. Otherwise, it
   * returns 0.0.
   * 
   * @return the "good" target distance or 0.0.
   */
  public double getTargetInchesDistance() {
    synchronized (targetDataLock) {
      return this.targetInchesDistance;
    }
  }

  private void setTargetData(boolean targetDataGood, double targetAngle, int targetPixelHeight) {
    synchronized (targetDataLock) {
      this.targetDataGood = targetDataGood;
      if (targetDataGood) {
        this.targetAngle = targetAngle;
        this.targetPixelHeight = targetPixelHeight;
        this.targetInchesDistance = (-0.978 * (double) targetPixelHeight) + 100.7 - BUMPER_FRONT_TO_CAMERA_INCHES;
      } else {
        this.targetAngle = 0.0;
        this.targetPixelHeight = 0;
        this.targetInchesDistance = 0.0;
      }
    }
  }

  @Override
  public void initDefaultCommand() {
    // No default command.
  }

  /**
   * Instances of this class are created for each contour that comes out of the
   * pipeline. This types ordering is by the x value of the bounding rectangle.
   * The result is a sort from left to right for further processing to find the
   * right two contours. That processing is based on the {@link RotatedRect} that
   * is contained in here.
   */
  private static class LtoRSortableContour implements Comparable<LtoRSortableContour> {
    public final RotatedRect rrect;
    public final Rect rect;

    public LtoRSortableContour(final RotatedRect rrect, final Rect rect) {
      this.rrect = rrect;
      this.rect = rect;
    }

    public int compareTo(LtoRSortableContour other) {
      return this.rect.x - other.rect.x;
    }
  }
}
