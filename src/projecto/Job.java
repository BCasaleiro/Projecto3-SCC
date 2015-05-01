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
    private int type; // 1, 2 or 3
    private ArrayList<Integer> route;
    private final Random rand = new Random();
    
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
        sendTraceNote("AGV JobQueue length: "+ myModel.getAGV().getJobQueue().length());

      
      if (!myModel.getAGV().isBusy()) {
         myModel.getAGV().setBusy(true);

         myModel.getAGV().activateAfter(this);
      }
      
        passivate();
      
        System.out.println("Job a sair");
      
        sendTraceNote("Job has been serviced and is leaving.");
   }   
}
