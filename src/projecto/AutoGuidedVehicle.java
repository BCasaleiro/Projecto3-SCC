package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

public class AutoGuidedVehicle extends SimProcess{
    
    private Model myModel;
    private int currentStation;
    private float speed;
    private boolean busy;
    private ProcessQueue<Job> jobQueue;
    
    public AutoGuidedVehicle(Model model, String name, boolean showInTrace) {
      super(model, name, showInTrace);
      // store a reference to the model this VC is associated with
      myModel = model;
      speed = (float)2.5/60;
      jobQueue = new ProcessQueue<Job>(model, "AVG Job Queue", true, true);
      currentStation = -1;
    }

    public boolean isBusy() {
        return this.busy;
    }
    
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    
    public Job getFirstInQueue() {
        Job aux = this.jobQueue.first();
        this.jobQueue.remove(aux);
        
        return aux;
    }
    
    public void insertInJobQueue (Job job) {
        this.jobQueue.insert(job);
    }
    
    public ProcessQueue<Job> getJobQueue() {
        return this.jobQueue;
    }
    
    @Override
    public void lifeCycle() {
        Job nextJob;
        // the server is always on duty and will never stop working
      while (true) {
         // check if there is someone waiting
         if (jobQueue.isEmpty()) {
            passivate();
         } else {

            // YES, there is a job waiting

            nextJob = getFirstInQueue();

            //TODO mover o job para a workstation seguintes
            
            //hold(new TimeSpan(myModel.getServiceTime(), TimeUnit.MINUTES));

            nextJob.activate(new TimeSpan(0.0));
         }
      }
    }
    
}
