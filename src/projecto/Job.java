package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import java.util.Random;

public class Job extends SimProcess{
    /** a reference to the model this process is a part of
    *  useful shortcut to access the model's static components
    */
    private Process myModel;
    private int type; // 1, 2 or 3
    private final Random rand = new Random();
    
    public Job(Model owner, String name, boolean showInTrace) {

        super(owner, name, showInTrace);
        // store a reference to the model this truck is associated with
        myModel = (Process)owner;
        if(rand.nextDouble() <= 0.3){
            this.type = 1;
        } else if( 0.3 < rand.nextDouble() && rand.nextDouble() <= 0.8) {
            this.type = 2;
        } else {
            this.type = 3;
        }
    }
    
    public int getJobType() {
        return this.type;
    }
   
    @Override
    public void lifeCycle() {
     
      // enter parking-lot
      myModel.jobQueue.insert(this);
      sendTraceNote("JobQueue length: "+ myModel.jobQueue.length());
            // ... lifeCycle() continued

      // check if a VC is available
      if (!myModel.agv.isBusy()) {
         // get a reference to the first  VC from the idle VC queue
         myModel.agv.setBusy(true);

         // place the VC on the eventlist right after me,
         // to ensure that I will be the next customer to get serviced
         myModel.agv.activateAfter(this);
      }
      
      passivate();

      // Ok, I am back online again, which means I was serviced
      // by the VC. I can leave the systems now.
      // Luckily I don't have to do anything more than sending
      // a message to the trace file, because the
      // Java VM garbage collector will get the job done.
      // Bye!
      sendTraceNote("Truck was serviced and leaves system.");
   }   
}
