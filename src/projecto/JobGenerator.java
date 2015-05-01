package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

public class JobGenerator extends SimProcess{

    private Process myModel;
    private ProcessQueue<Job> jobQueue;
    
    public JobGenerator(Model model, String name, boolean showInTrace) {
        super(model, name, showInTrace);
        myModel = (Process)model;
      
        jobQueue = new ProcessQueue<>(model, "IO Job Queue", true, true);
        
        System.out.println("I/O station criada");
    }
    
    public void insertInJobQueue(Job job) {
        this.jobQueue.insert(job);
    }
    
    public ProcessQueue<Job> getJobQueue() {
        return this.jobQueue;
    }
    
    @Override
    public void lifeCycle() {
        
        System.out.println("I/O station activa");
        
        while (true) {

         Job job = new Job(myModel, "Job", true);

         job.activateAfter(this);

         TimeSpan time = new TimeSpan(myModel.getJobArrivalTime(), TimeUnit.MINUTES);
         
         hold(time);
        }
    }
    
}
