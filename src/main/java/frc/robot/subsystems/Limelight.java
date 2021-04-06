// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");;
  
  NetworkTableEntry m_tv = m_table.getEntry("tv");
  NetworkTableEntry m_tx = m_table.getEntry("tx");
  NetworkTableEntry m_ty = m_table.getEntry("ty");
  NetworkTableEntry m_ledMode = m_table.getEntry("ledMode");
  NetworkTableEntry m_camMode = m_table.getEntry("camMode");

  BooleanSupplier m_isRaised;

  /** Creates a new Limelight. */
  public Limelight(BooleanSupplier isRaised) {
    m_isRaised = isRaised;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Distance Estimate", getDistance());
  }

  public static final int kPipeline = 0, kOff = 1, kBlink = 2, kOn = 3;
  public void setLed(int mode) {
    m_ledMode.setNumber(mode);
  }

  public int getLed() {
    return (int)m_ledMode.getNumber(0);
  }

  public double getOffsetX() {
    return m_tv.getBoolean(false) ? m_tx.getDouble(180) : 180; 
  }

  static final double mountHeightRaised = Units.feetToMeters(29 / 12.0);
  static final double mountHeightLowered = Units.feetToMeters(-1); // TODO
  
  double getMountHeightMeters() {
    return m_isRaised.getAsBoolean() ? mountHeightRaised : mountHeightLowered;
  }

  static final double mountAngleRaised = 60;
  static final double mountAngleLowered = -1; // TODO

  double getMountAngle() {
    return m_isRaised.getAsBoolean() ? mountHeightRaised : mountHeightLowered;
  }

  static final double kOuterPortCenterHeightMeters = Units.feetToMeters(98.25 / 12.0);

  public double getDistance() {
    // https://docs.limelightvision.io/en/latest/cs_estimating_distance.html
    // d = (h2-h1) / tan(a1+a2)

    return Math.abs(
      (kOuterPortCenterHeightMeters - getMountHeightMeters())
      / Math.tan(Units.degreesToRadians(getMountAngle() + m_ty.getDouble(0.0)))
    );
  }
}
