package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AuxiliaryMotorConstants;

public class AuxiliaryMotorSubsystem extends SubsystemBase {
    
    private final TalonFX auxiliaryMotor;
    private final TalonFXConfiguration motorConfig;
    private final DutyCycleOut dutyCycleControl;
    private final VelocityVoltage velocityControl;
    
    public AuxiliaryMotorSubsystem() {
        // Initialize the Kraken X44 motor
        auxiliaryMotor = new TalonFX(AuxiliaryMotorConstants.kAuxiliaryMotorPort, "*");
        
        // Configure the motor
        motorConfig = new TalonFXConfiguration();
        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        motorConfig.MotorOutput.Inverted = AuxiliaryMotorConstants.kMotorInverted;
        
        // Apply configuration
        auxiliaryMotor.getConfigurator().apply(motorConfig);
        
        // Initialize control requests
        dutyCycleControl = new DutyCycleOut(0);
        velocityControl = new VelocityVoltage(0);
        
        System.out.println("Auxiliary Kraken X44 Motor Initialized on CAN ID: " + AuxiliaryMotorConstants.kAuxiliaryMotorPort);
    }
    
    /**
     * Set motor speed using percentage output (-1.0 to 1.0)
     */
    public void setSpeed(double speed) {
        auxiliaryMotor.setControl(dutyCycleControl.withOutput(speed));
    }
    
    /**
     * Set motor velocity in rotations per second
     */
    public void setVelocity(double rotationsPerSecond) {
        auxiliaryMotor.setControl(velocityControl.withVelocity(rotationsPerSecond));
    }
    
    /**
     * Stop the motor
     */
    public void stop() {
        auxiliaryMotor.setControl(dutyCycleControl.withOutput(0));
    }
    
    /**
     * Get current motor velocity in rotations per second
     */
    public double getVelocity() {
        return auxiliaryMotor.getVelocity().getValue();
    }
    
    /**
     * Get current motor position in rotations
     */
    public double getPosition() {
        return auxiliaryMotor.getPosition().getValue();
    }
    
    /**
     * Reset motor position to zero
     */
    public void resetPosition() {
        auxiliaryMotor.setPosition(0);
    }
    
    /**
     * Set motor to brake mode
     */
    public void setBrakeMode() {
        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        auxiliaryMotor.getConfigurator().apply(motorConfig);
    }
    
    /**
     * Set motor to coast mode
     */
    public void setCoastMode() {
        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        auxiliaryMotor.getConfigurator().apply(motorConfig);
    }
    
    /**
     * Command to run motor at specified speed
     */
    public Command runAtSpeed(double speed) {
        return Commands.run(() -> setSpeed(speed), this);
    }
    
    /**
     * Command to stop the motor
     */
    public Command stopMotor() {
        return Commands.runOnce(this::stop, this);
    }
    
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // You can add telemetry here if needed
    }
}