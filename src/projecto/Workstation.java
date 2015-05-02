package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.ArrayList;

public class Workstation extends SimProcess{

    private ArrayList<Machine> machines;
    private ProcessQueue<Job> jobQueue;
    
    public Workstation(Model model, String string, boolean bln, boolean bln1, int nMachines, int ws) {
        super(model, string, bln, bln1);
        
        machines = new ArrayList<>();
        
        System.out.println("Estação " + ws + " criada");
        
        for (int i = 0; i < nMachines; i++) {
            machines.add(new Machine(model, "Machine", true, ws, i));
        }
        
        jobQueue = new ProcessQueue<>(model, "Job Queue", true, true);
    }
    
    public ArrayList<Machine> getMachines() {
        return this.machines;
    }
    
    public Job getFirstInQueue() {
        Job aux = this.jobQueue.first();
        this.jobQueue.remove(aux);
        
        aux.end = presentTime().getTimeAsDouble();
        aux.time += (aux.end - aux.init);
        
        return aux;
    }
    
    public void insertInJobQueue(Job job) {
        this.jobQueue.insert(job);
    }
    
    public ProcessQueue<Job> getJobQueue() {
        return this.jobQueue;
    }

    @Override
    public void lifeCycle() {
        System.out.println("Estação activa");
        
        while(true) {
            for (int i = 0; i < machines.size(); i++) {
                if(!machines.get(i).isBusy()) {    
                    machines.get(i).activate(new TimeSpan(0.0));
                }
            }
        
            passivate();
            System.out.println("Estação activa novamente, job queue size: " + this.jobQueue.size());
        }
    }
}
