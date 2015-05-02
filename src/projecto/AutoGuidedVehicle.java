package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

public class AutoGuidedVehicle extends SimProcess{
    
    private Process myModel;
    private boolean getBackToIO;
    private int currentStation;
    private float speed;
    private boolean busy;
    private ProcessQueue<Job> jobQueue;
    private int routes[][];
    
    public AutoGuidedVehicle(Model model, String name, boolean showInTrace, boolean getBackToIO) {
        super(model, name, showInTrace);
        // store a reference to the model this VC is associated with
        myModel = (Process)model;
        speed = (float)2.5*60;
        jobQueue = new ProcessQueue<Job>(model, "AGV Job Queue", true, true);
        currentStation = 5;
      
         this.getBackToIO = getBackToIO;
      
        routes = new int[][]{{0, 45, 50, 90, 100, 135},
                            {45, 0, 50, 100, 90, 135},
                            {50, 50, 0, 50, 50, 90}, 
                            {90, 100, 50, 0, 45, 50}, 
                            {100, 90, 50, 45, 0, 50}, 
                            {100, 90, 50, 45, 0, 50}, 
                            {135, 135, 90, 50, 50, 0}        
                            };
        
        System.out.println("AGV criado");
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
        
        aux.endAGV = presentTime().getTimeAsDouble();
        
        aux.timeAGV += (aux.endAGV - aux.initAGV);
        
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
        int nextStation;
        float travelTime;
        // the server is always on duty and will never stop working
        System.out.println("AGV activo, a entrar no loop infinito");
        while (true) {
             // check if there is someone waiting
            if (jobQueue.isEmpty()) {
                System.out.println("Job Queue do AGV vazia");
                if(getBackToIO) {
                    travelTime = (float)routes[currentStation][5]/speed;
                    myModel.getDistance().update(routes[currentStation][5]);
                    hold(new TimeSpan(travelTime, TimeUnit.MINUTES));
                    this.currentStation = 5;
                }
                
                passivate();
            } else {
                nextJob = getFirstInQueue();

                nextStation = nextJob.getNextStation();
                System.out.println("A receber job " + nextJob.id + " da estação " + nextJob.getCurrentStation());
                if(currentStation != nextJob.getCurrentStation()) {
                    System.out.println("A mover o AGV até à estação " + nextJob.getCurrentStation());
                    travelTime = (float)routes[nextJob.getCurrentStation()][currentStation]/speed;
                    myModel.getDistance().update(routes[nextJob.getCurrentStation()][currentStation]);
                    hold(new TimeSpan(travelTime, TimeUnit.MINUTES));
                }
                
                if(nextJob.getCurrentStation() == 5) {
                    myModel.getIOStation().getJobQueue().remove(nextJob);
                } else {
                    myModel.getWorkstation(nextJob.getCurrentStation()).getMachines().get(nextJob.getMachine()).activateAfter(this);
                }
                
                System.out.println("A mover o Job " + nextJob.id + " de " + nextJob.getCurrentStation() + " até à estação " + nextStation);
                travelTime = (float)routes[nextJob.getCurrentStation()][nextStation]/speed;
                myModel.getDistance().update(routes[nextJob.getCurrentStation()][nextStation]);
                hold(new TimeSpan(travelTime, TimeUnit.MINUTES));
                
                nextJob.setCurrentStation(nextStation);
                this.currentStation = nextStation;
                
                if(nextStation != 5) {
                    System.out.println("Job " + nextJob.id + " inserido na Job Queue de " + nextStation);
                    myModel.getWorkstation(nextStation).insertInJobQueue(nextJob);
                    nextJob.init = presentTime().getTimeAsDouble();
                    myModel.getWorkstation(nextStation).activateAfter(this);
                } else {
                    System.out.println("Job entregue à I/O");
                    nextJob.activateAfter(this);
                }
            }
        }
    }
    
}
