package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import java.util.ArrayList;

public class Workstation extends Process{

    private ArrayList<Machine> machines;
    private ProcessQueue<Job> jobQueue;
    
    public Workstation(Model model, String string, boolean bln, boolean bln1, int nMachines, int ws) {
        super(model, string, bln, bln1);
        
        for (int i = 0; i < nMachines; i++) {
            machines.add(new Machine(model, "Machine", true, ws));
        }
    }
    
    public ProcessQueue<Job> getJobQueue() {
        return this.jobQueue;
    }
    
    @Override
    public void init() {
        jobQueue = new ProcessQueue<Job>(this, "Job Queue", true, true);
    }
}
