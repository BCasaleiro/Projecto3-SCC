package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

public class JobGenerator extends SimProcess{

    public JobGenerator(Model owner, String name, boolean showInTrace) {
      super(owner, name, showInTrace);
    }
    
    @Override
    public void lifeCycle() {
        // get a reference to the model
      Process model = (Process)getModel();

      // endless loop:
      while (true) {

         // create a new truck
         // Parameters:
         // model       = it's part of this model
         // "Truck"     = name of the object
         // true        = yes please, show the truck in trace file
         Job job = new Job(model, "Job", true);

         // now let the newly created truck roll on the parking-lot
         // which means we will activate it after this truck generator
         job.activateAfter(this);

         // wait until next truck arrival is due
         TimeSpan time = new TimeSpan(model.getJobArrivalTime(), TimeUnit.MINUTES);
         
         System.out.println(time);
         
         hold(time);
         // from inside to outside...
         // we draw a new inter-arrival time
         // we make a TimeSpan object out of it and
         // we wait for exactly this period of time
      }
    }
    
}
