package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

public class Machine extends SimProcess {

    private Process myModel;
    private boolean busy;
    private Job job;
    private int ws;
    
    public Machine(Model model, String string, boolean bln, int ws) {
        super(model, string, bln);
        myModel = (Process)model;
        this.busy = false;
        this.ws = ws;
    }

    public boolean isBusy() {
        return this.busy;
    }
    
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    
    @Override
    public void lifeCycle() {
        while(true) {
            if(busy) {
                hold(new TimeSpan(myModel.getServiceTime(job.getJobType(), ws), TimeUnit.MINUTES));
                busy = false;
            } else {
                if(myModel.workstations.get(ws).getJobQueue().isEmpty()) {
                    passivate();
                } else {
                    job = myModel.workstations.get(ws).getJobQueue().first();
                    myModel.workstations.get(ws).getJobQueue().remove(job);
                    busy = true;
                }
            }
        }
    }
    
}
