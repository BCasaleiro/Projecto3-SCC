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
        myModel = (Process) model;
        this.busy = false;
        this.ws = ws;
        
        System.out.println("Maquina criada na estação " + ws);
    }

    public boolean isBusy() {
        return this.busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public void lifeCycle() {
        
        System.out.println("Máquina activa da estação " + ws);
        
        while (true) {
            if (myModel.getWorkstation(ws).getJobQueue().isEmpty()) {
                System.out.println("Queue da estação está empty");
                passivate();
            } else {
                job = myModel.getWorkstation(ws).getFirstInQueue();
                busy = true;
                
                System.out.println("Máquina da estação " + ws + "a trabalhar no job " + job.id);
                
                sendTraceNote("Job");
                hold(new TimeSpan(myModel.getServiceTime(job.getJobType(), ws), TimeUnit.MINUTES));
                myModel.getAGV().insertInJobQueue(job);
                myModel.getAGV().activateAfter(this);
                System.out.println("Job " + job.id + " inserido na queue do AGV");
                busy = false;
            }
        }
    }

}
