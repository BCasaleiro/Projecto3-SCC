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
        
        exp.setShowProgressBar(true);  
        exp.stop(new TimeInstant(runtime, TimeUnit.MINUTES));
        exp.tracePeriod(new TimeInstant(0), new TimeInstant(runtime, TimeUnit.MINUTES));
        
        exp.debugPeriod(new TimeInstant(0), new TimeInstant(runtime, TimeUnit.MINUTES));   // and debug output
        
        exp.start();

        exp.report();

        exp.finish();
    }
}
