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
    private ArrayList<ArrayList<ContDistErlang>> serviceTimeArrayList;
    
    public Process(Model model, String string, boolean bln, boolean bln1, boolean getBackToIO) {
        super(model, string, bln, bln1);
        
        workstations = new ArrayList<>();
        
        this.getBackToIO = getBackToIO;
        
        this.serviceTimeArrayList = new ArrayList<> ();
        this.serviceTimeArrayList.add(new ArrayList<>());
        this.serviceTimeArrayList.add(new ArrayList<>());
        this.serviceTimeArrayList.add(new ArrayList<>());
        
        System.out.println("Modelo criado");
    }
    
    public double getServiceTime(int jobType, int workstation) {
        ContDistErlang serviceTime;
        switch(jobType) {
            case 1:
                switch(workstation) {
                    case 2:
                        return serviceTimeArrayList.get(0).get(0).sample();
                    case 0:
                        return serviceTimeArrayList.get(0).get(1).sample();
                    case 1:
                        return serviceTimeArrayList.get(0).get(2).sample();
                    case 4:
                        return serviceTimeArrayList.get(0).get(3).sample();
                }
            case 2:
                switch(workstation) {
                    case 3:
                        return serviceTimeArrayList.get(1).get(0).sample();
                    case 0:
                        return serviceTimeArrayList.get(1).get(1).sample();
                    case 2:
                        return serviceTimeArrayList.get(1).get(2).sample();
                }
            case 3:
                switch(workstation) {
                    case 1:
                        return serviceTimeArrayList.get(2).get(0).sample();
                    case 4:
                        return serviceTimeArrayList.get(2).get(1).sample();
                    case 0:
                        return serviceTimeArrayList.get(2).get(2).sample();
                    case 3:
                        return serviceTimeArrayList.get(2).get(3).sample();
                    case 2:
                        return serviceTimeArrayList.get(2).get(4).sample();
                }
                break;
            default:
                
        }
        
        return 0;
    }
    
    public double getJobArrivalTime() {
        return jobArrivalTime.sample();
    }
    
    public JobGenerator getIOStation () {
        return this.ioStation;
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
        for (int i = 0; i < workstations.size(); i++) {
            workstations.get(i).activate(new TimeSpan(0));
        }
        
        agv = new AutoGuidedVehicle(this, "AGV", true, getBackToIO);
        agv.activate(new TimeSpan(0));
        // Use TimeSpan to activate a process after a span of time relative to actual simulation time,
        // or use TimeInstant to activate the process at an absolute point in time.

        // create and activate the truck generator process
        ioStation = new JobGenerator(this,"IO Station",true);
        ioStation.activate(new TimeSpan(0));
    }

    @Override
    public void init() {
        jobArrivalTime= new ContDistExponential(this, "JobArrivalTimeStream", 15, true, true);
        jobArrivalTime.setNonNegative(true);
        
        serviceTimeArrayList.get(0).add(new ContDistErlang(this, "ServiceTimeStream", 2, 30, true, true));
        serviceTimeArrayList.get(0).add(new ContDistErlang(this, "ServiceTimeStream", 2, 36, true, true));
        serviceTimeArrayList.get(0).add(new ContDistErlang(this, "ServiceTimeStream", 2, 51, true, true));
        serviceTimeArrayList.get(0).add(new ContDistErlang(this, "ServiceTimeStream", 2, 30, true, true));
        
        serviceTimeArrayList.get(1).add(new ContDistErlang(this, "ServiceTimeStream", 2, 66, true, true));
        serviceTimeArrayList.get(1).add(new ContDistErlang(this, "ServiceTimeStream", 2, 48, true, true));
        serviceTimeArrayList.get(1).add(new ContDistErlang(this, "ServiceTimeStream", 2, 45, true, true));
        
        serviceTimeArrayList.get(2).add(new ContDistErlang(this, "ServiceTimeStream", 2, 72, true, true));
        serviceTimeArrayList.get(2).add(new ContDistErlang(this, "ServiceTimeStream", 2, 15, true, true));
        serviceTimeArrayList.get(2).add(new ContDistErlang(this, "ServiceTimeStream", 2, 42, true, true));
        serviceTimeArrayList.get(2).add(new ContDistErlang(this, "ServiceTimeStream", 2, 54, true, true));
        serviceTimeArrayList.get(2).add(new ContDistErlang(this, "ServiceTimeStream", 2, 60, true, true));
        
        workstations.add(new Workstation(this, "Workstation ", true, true, 3, 0));
        workstations.add(new Workstation(this, "Workstation ", true, true, 3, 1));
        workstations.add(new Workstation(this, "Workstation ", true, true, 4, 2));
        workstations.add(new Workstation(this, "Workstation ", true, true, 4, 3));
        workstations.add(new Workstation(this, "Workstation ", true, true, 1, 4));
    }
    
}
