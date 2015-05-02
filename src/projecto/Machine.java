package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.statistic.Count;
import java.util.concurrent.TimeUnit;

public class Machine extends SimProcess {

    private Process myModel;
    private boolean busy;
    private Job job;
    private int ws;
    private Count machineWorking;
    private Count machineBlocked;
    private int nMachine;
    private double init;
    private double end;

    public Machine(Model model, String string, boolean bln, int ws, int nMachine) {
        super(model, string, bln);
        myModel = (Process) model;
        this.busy = false;
        this.ws = ws;
        
        this.nMachine = nMachine;
        
        this.machineWorking = new Count(model, "Working time machine of " + ws, true, true);
        this.machineBlocked = new Count(model, "Blocked time machine of " + ws, true, true);
        
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
        TimeSpan time;
        double nextErlang;
        System.out.println("Máquina activa da estação " + ws);
        
        while (true) {
            if (myModel.getWorkstation(ws).getJobQueue().isEmpty()) {
                System.out.println("Queue da estação está empty");
                passivate();
            } else {
                job = myModel.getWorkstation(ws).getFirstInQueue();
                job.setMachine(this.nMachine);
                
                busy = true;
                
                nextErlang = myModel.getServiceTime(job.getJobType(), ws);
                
                time = new TimeSpan(nextErlang, TimeUnit.MINUTES);
                
                System.out.println("Máquina da estação " + ws + "a trabalhar no job " + job.id + " por " + nextErlang + " minutos");
                
                this.machineWorking.update((long)nextErlang);
                
                hold(time);
                myModel.getAGV().insertInJobQueue(job);
                myModel.getAGV().activateAfter(this);
                System.out.println("Job " + job.id + " inserido na queue do AGV");
                
                init = presentTime().getTimeAsDouble();
                
                passivate();
                
                end = presentTime().getTimeAsDouble();
                
                this.machineWorking.update((long)(end - init));
                
                busy = false;
            }
        }
    }

}
