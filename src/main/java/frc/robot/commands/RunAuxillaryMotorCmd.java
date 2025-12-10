package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AuxiliaryMotorSubsystem;

public class RunAuxiliaryMotorCmd extends Command {
    
    private final AuxiliaryMotorSubsystem motorSubsystem;
    private final DoubleSupplier speedSupplier;
    private final double deadband = 0.05;
    
    /**
     * Command to run auxiliary motor with joystick control
     * 
     * @param motorSubsystem The auxiliary motor subsystem
     * @param speedSupplier Supplier for speed input (typically from joystick axis)
     */
    public RunAuxiliaryMotorCmd(AuxiliaryMotorSubsystem motorSubsystem, DoubleSupplier speedSupplier) {
        this.motorSubsystem = motorSubsystem;
        this.speedSupplier = speedSupplier;
        addRequirements(motorSubsystem);
    }
    
    @Override
    public void initialize() {
        // Optional: Reset position or do initial setup
    }
    
    @Override
    public void execute() {
        double speed = speedSupplier.getAsDouble();
        
        // Apply deadband
        if (Math.abs(speed) < deadband) {
            speed = 0.0;
        }
        
        motorSubsystem.setSpeed(speed);
    }
    
    @Override
    public void end(boolean interrupted) {
        motorSubsystem.stop();
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }
}