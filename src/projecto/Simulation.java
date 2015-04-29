package projecto;


import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private Process ioStation;
    private Experiment exp;
    private AutoGuidedVehicle agv;
    private int runtime;
    private boolean getBackToIO;
    
    public Simulation(int runtime, boolean getBackToIO) {  
        this.runtime = runtime;
        this.getBackToIO = getBackToIO;
    }
    
    public void start() {
        exp = new Experiment("ProcessExampleExperiment", TimeUnit.SECONDS, TimeUnit.MINUTES, null);
        ioStation = new Process(null, "Simple Process-Oriented Van Carrier Model", true, true, getBackToIO);
        ioStation.connectToExperiment(exp);
        
        // set experiment parameters
        exp.setShowProgressBar(true);  // display a progress bar (or not)
        exp.stop(new TimeInstant(runtime, TimeUnit.MINUTES));
        exp.tracePeriod(new TimeInstant(0), new TimeInstant(runtime, TimeUnit.MINUTES));
        // set the period of the trace
        exp.debugPeriod(new TimeInstant(0), new TimeInstant(runtime, TimeUnit.MINUTES));   // and debug output
        // ATTENTION!
        // Don't use too long periods. Otherwise a huge HTML page will
        // be created which crashes Netscape :-)    
        // start the experiment at simulation time 0.0
        exp.start();

        // --> now the simulation is running until it reaches its end criterion
        // ...
        // ...
        // <-- afterwards, the main thread returns here
        // generate the report (and other output files)
        exp.report();

        // stop all threads still alive and close all output files
        exp.finish();
    }
}
