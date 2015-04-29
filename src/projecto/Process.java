package projecto;

import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.ArrayList;

public class Process extends Model{
    
    private ContDistExponential jobArrivalTime;
    private AutoGuidedVehicle agv;
    private JobGenerator ioStation;
    private ArrayList<Workstation> workstations;
    private boolean getBackToIO;
    
    public Process(Model model, String string, boolean bln, boolean bln1, boolean getBackToIO) {
        super(model, string, bln, bln1);
        
        workstations.add(new Workstation(model, "Workstation ", true, true, 3, 0));
        workstations.add(new Workstation(model, "Workstation ", true, true, 3, 1));
        workstations.add(new Workstation(model, "Workstation ", true, true, 4, 2));
        workstations.add(new Workstation(model, "Workstation ", true, true, 4, 3));
        workstations.add(new Workstation(model, "Workstation ", true, true, 1, 4));
        this.getBackToIO = getBackToIO;
    }
    
    public double getServiceTime(int jobType, int workstation) {
        ContDistErlang serviceTime;
        switch(jobType) {
            case 1:
                switch(workstation) {
                    case 2:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 30, true, false);
                        return serviceTime.sample();
                    case 0:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 36, true, false);
                        return serviceTime.sample();
                    case 1:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 51, true, false);
                        return serviceTime.sample();
                    case 4:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 30, true, false);
                        return serviceTime.sample();
                }
            case 2:
                switch(workstation) {
                    case 3:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 66, true, false);
                        return serviceTime.sample();
                    case 0:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 48, true, false);
                        return serviceTime.sample();
                    case 2:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 45, true, false);
                        return serviceTime.sample();
                }
            case 3:
                switch(workstation) {
                    case 1:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 72, true, false);
                        return serviceTime.sample();
                    case 4:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 15, true, false);
                        return serviceTime.sample();
                    case 0:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 42, true, false);
                        return serviceTime.sample();
                    case 3:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 54, true, false);
                        return serviceTime.sample();
                    case 2:
                        serviceTime = new ContDistErlang(this, "ServiceTimeStream", 2, 60, true, false);
                        return serviceTime.sample();
                }
                break;
            default:
                
        }
        
        return 0;
    }
    
    public double getJobArrivalTime() {
        return jobArrivalTime.sample();
    }
    
    public AutoGuidedVehicle getAGV() {
        return this.agv;
    }

    public Workstation getWorkstation(int station) {
        return this.workstations.get(station);
    }
    
    @Override
    public String description() {
        return "A job shop consists of one I/O (in/out) station and five workstations (group of machines).";
    }
    
    @Override
    public void doInitialSchedules() {
        agv = new AutoGuidedVehicle(this, "AVG", true);
        agv.activate(new TimeSpan(0));
        // Use TimeSpan to activate a process after a span of time relative to actual simulation time,
        // or use TimeInstant to activate the process at an absolute point in time.

        // create and activate the truck generator process
        JobGenerator generator = new JobGenerator(this,"JobArrival",true);
        generator.activate(new TimeSpan(0));
    }

    @Override
    public void init() {
        jobArrivalTime= new ContDistExponential(this, "JobArrivalTimeStream", 15.0, true, false);
        jobArrivalTime.setNonNegative(true);
    }
    
}
