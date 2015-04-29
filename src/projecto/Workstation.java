package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.SimProcess;
import java.util.ArrayList;

public class Workstation extends SimProcess{

    private ArrayList<Machine> machines;
    private ProcessQueue<Job> jobQueue;
    
    public Workstation(Model model, String string, boolean bln, boolean bln1, int nMachines, int ws) {
        super(model, string, bln, bln1);
        
        for (int i = 0; i < nMachines; i++) {
            machines.add(new Machine(model, "Machine", true, ws));
        }
        
        jobQueue = new ProcessQueue<Job>(model, "Job Queue", true, true);
    }
    
    public Job getFirstInQueue() {
        Job aux = this.jobQueue.first();
        this.jobQueue.remove(aux);
        
        return aux;
    }
    
    public ProcessQueue<Job> getJobQueue() {
        return this.jobQueue;
    }

    @Override
    public void lifeCycle() {
        // TODO
    }
}
