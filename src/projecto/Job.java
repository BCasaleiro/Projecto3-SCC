package projecto;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Job extends SimProcess{
    /** a reference to the model this process is a part of
    *  useful shortcut to access the model's static components
    */
    private static int id_geral = 0;
    public int id;
    private Process myModel;
    private int currentStation;
    private int machine;
    private int type; // 1, 2 or 3
    private ArrayList<Integer> route;
    private final Random rand = new Random();
    public double time;
    public double timeAGV;
    public double init;
    public double end;
    public double initAGV;
    public double endAGV;
    
    public Job(Model model, String name, boolean showInTrace) {

        super(model, name, showInTrace);
        // store a reference to the model this truck is associated with
        myModel = (Process)model;
        currentStation = 5;
        if(rand.nextDouble() <= 0.3){
            this.type = 1;
            route = new ArrayList<>(Arrays.asList(2, 0, 1, 4, 5));
        } else if( 0.3 < rand.nextDouble() && rand.nextDouble() <= 0.8) {
            this.type = 2;
            route = new ArrayList<>(Arrays.asList(3, 1, 2, 5));
        } else {
            this.type = 3;
            route = new ArrayList<>(Arrays.asList(1, 4, 0, 3, 2, 5));
        }
        
        this.id = id_geral++;
        
        System.out.println("Job " + id + " criado do tipo " + this.type);
    }
    
    public void setMachine(int machine) {
        this.machine = machine;
    }
    
    public int getMachine() {
        return this.machine;
    }
    
    public int getJobType() {
        return this.type;
    }
   
    public int getNextStation() {
        int x;
        if (this.route.isEmpty()) {
            return 5;
        } else {
            x = this.route.get(0);
            this.route.remove(0);
        }
        return x;
    }
    
    public int getCurrentStation () {
        return this.currentStation;
    }
    
    public void setCurrentStation(int currentStation) {
        this.currentStation = currentStation;
    }
    
    @Override
    public void lifeCycle() {
        
        myModel.getAGV().insertInJobQueue(this);
        
        this.initAGV = this.presentTime().getTimeAsDouble();
        
        myModel.getAGV().activateAfter(this);
        
        sendTraceNote("AGV JobQueue length: "+ myModel.getAGV().getJobQueue().length());

      
        if (!myModel.getAGV().isBusy()) {
            myModel.getAGV().setBusy(true);

            myModel.getAGV().activateAfter(this);
        }
      
        passivate();
        
        if(this.type == 1) {
            myModel.delay1.update(this.time);
            myModel.delayAGV1.update(this.timeAGV);
        } else if(this.type == 2) {
            myModel.delay2.update(time);
            myModel.delayAGV2.update(this.timeAGV);
        } else {
            myModel.delay3.update(time);
            myModel.delayAGV3.update(this.timeAGV);
        }
        
        System.out.println("Job a sair");
        
        sendTraceNote("Job has been serviced and is leaving.");
   }   
}
