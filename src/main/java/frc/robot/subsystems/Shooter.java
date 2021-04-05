/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  
  WPI_TalonFX m_master = new WPI_TalonFX(Constants.kShooterTalonLeft);
  WPI_TalonFX m_slave = new WPI_TalonFX(Constants.kShooterTalonRight);
  DoubleSolenoid m_piston = new DoubleSolenoid(Constants.kPistonModule, Constants.kShooterPistonForward, Constants.kShooterPistonReverse);

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    m_master.setInverted(InvertType.InvertMotorOutput);

    m_slave.follow(m_master);
    m_slave.setInverted(InvertType.OpposeMaster);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter RPM", getRPM());
    SmartDashboard.putNumber("Shooter Error RPM", getClosedLoopErrorRPM());
  }


  /**
   * Raising / lowering with piston
   */
  public void raise() {
    m_piston.set(Value.kForward);
  }

  public void lower() {
    m_piston.set(Value.kReverse);
  }

  public void toggleHeight() {
    if(isRaised())
      lower();
    else
      raise();
  }

  public void pistonOff() {
    m_piston.set(Value.kOff);
  }

  public boolean isRaised() {
    return m_piston.get() == Value.kForward;
  }

  static final double kDistanceToRaise = 5;

  public boolean shouldBeRaised(double distance) {
    return distance > kDistanceToRaise;
  }

  /**
   * Shooting
   */
  public double getRPM() {
    return getRPMFromVelocity(m_master.getSelectedSensorVelocity());
  }

  public void setRPM(double rpm) {
    m_master.set(ControlMode.Velocity, getVelocityFromRPM(rpm));
  }

  public void zero() {
    m_master.set(ControlMode.PercentOutput, 0);
  }

  public double getClosedLoopErrorRPM() {
    return getRPMFromVelocity(m_master.getClosedLoopError());
  }

  // Converts RPM to talon units
  double getVelocityFromRPM(double rpm) {
    return (rpm / 600) * 4096;
  }

  // Converts talon units to RPM
  double getRPMFromVelocity(double velocity) {
    return (velocity * 600) / 4096;
  }

  // The XURU
  public double getXuru(double distance) {
    double x = distance;

    // if(isRaised()) // Raised
      double estimate = getRPMFromVelocity(23.4667*Math.pow(x, 3) - 1181.7143*Math.pow(x, 2) + 19253.3333*x - 81888.5714);
    // else // Lowered
      // return getRPMFromVelocity(-38.5526*Math.pow(x, 3) + 748.4528*Math.pow(x, 2) - 4459.5178*x + 18815.6851);

    estimate = 2750;
      
    SmartDashboard.putNumber("Target RPM Estimate", estimate);
    return estimate;
  }
}
