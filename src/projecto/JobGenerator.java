package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

public class JobGenerator extends SimProcess{

    private Process myModel;
    
    
    public JobGenerator(Model model, String name, boolean showInTrace) {
      super(model, name, showInTrace);
      myModel = (Process)model;
    }
    
    @Override
    public void lifeCycle() {
        while (true) {

         Job job = new Job(myModel, "Job", true);

         job.activateAfter(this);

         TimeSpan time = new TimeSpan(myModel.getJobArrivalTime(), TimeUnit.MINUTES);
         
         hold(time);
        }
    }
    
}
