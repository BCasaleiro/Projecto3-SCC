package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

public class AutoGuidedVehicle extends SimProcess{
    
    private Process myModel;
    private float speed;
    private boolean busy;
    
    public AutoGuidedVehicle(Model owner, String name, boolean showInTrace) {
      super(owner, name, showInTrace);
      // store a reference to the model this VC is associated with
      myModel = (Process)owner;
      speed = (float)2.5/60;
    }

    public boolean isBusy() {
        return this.busy;
    }
    
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
        
    @Override
    public void lifeCycle() {
        // the server is always on duty and will never stop working
      while (true) {
         // check if there is someone waiting
         if (myModel.jobQueue.isEmpty()) {
            passivate();
         } else {

            // YES, there is a job waiting

            // get a reference to the first truck from the truck queue
            Job nextJob = myModel.jobQueue.first();
            // remove the truck from the queue
            myModel.jobQueue.remove(nextJob);

            //hold(new TimeSpan(myModel.getServiceTime(), TimeUnit.MINUTES));

            nextJob.activate(new TimeSpan(0.0));
         }
      }
    }
    
}
