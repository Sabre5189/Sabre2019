/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PWMVictorSPX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Spark frontLeft;
  private Spark frontRight;
  private Spark backLeft;
  private Spark backRight;
  private MecanumDrive mecDrive;
  private OI oi;

  private int LeftXAxis=0;
  private int LeftYAxis=1;
  private int RightXAxis=4;


  private Spark leftGrabber;
  private Spark rightGrabber;
  private PWMVictorSPX liftController;

  //may need to change later
  private int leftGrabberport=7;
  private int rightGrabberport=8;
  private int liftControllerPort=9;

  private double leftForward=1.0;
  private double rightForward=1.0;
  private double leftback=-1.0;
  private double rightback=-1.0;

  private double liftUpSpeed=1.0;
  private double liftDownSpeed=-1.0;




  //private int Intake=1000;
  //private int Outake=2000;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    frontLeft= new Spark(0);
    frontRight=new Spark(2);
    backLeft=new Spark(1);
    backRight=new Spark(3);
    mecDrive=new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    oi=new OI();

    leftGrabber=new Spark(leftGrabberport);
    rightGrabber=new Spark(rightGrabberport);

    liftController=new PWMVictorSPX(liftControllerPort);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    mecDrive.driveCartesian(oi.driver.getRawAxis(LeftXAxis), oi.driver.getRawAxis(LeftYAxis), oi.driver.getRawAxis(RightXAxis));
    
    handleGrab();

    handleLift();
    

  }

  private void handleLift() {
    boolean liftUp=oi.driver.getXButton();
    boolean liftDown=oi.driver.getYButton();

    if (liftUp){
      liftController.setSpeed(liftUpSpeed);
    }

    if (liftDown){
      liftController.setSpeed(liftDownSpeed);
    }
  }

  private void handleGrab() {
    boolean grab=oi.driver.getTriggerAxis(Hand.kLeft)>.5;
    boolean push=oi.driver.getTriggerAxis(Hand.kRight)>.5;

    if (grab && push){
      //don't do anything
  
      }
      if (grab){
        grabTheBall();
      }

      if (push){
        pushTheBall();
      }
  }

  private void pushTheBall() {
    leftGrabber.setSpeed(leftForward);
    rightGrabber.setSpeed(rightForward);
  }

  private void grabTheBall() {
    leftGrabber.setSpeed(leftback);
    rightGrabber.setSpeed(rightback);
  }

  /*
  'spark controlers for graber, vex controler for lift'
   */


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  
  @Override
  public void disabledInit(){
    mecDrive.driveCartesian(0, 0, 0);
  }
}
