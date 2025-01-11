package clase;

public class NewThread extends Thread{
    private String exeName;

    NewThread(String name){
        super(name);
        exeName = name;
        System.out.println("New thread: " + this);
        //TODO: start the thread in main class
    }

    public void run(){
        try{
            for(int i = 5; i > 0; i--){
                System.out.println(exeName + ": " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e){
            System.out.println(exeName + " interrupted.");
        }
        System.out.println(exeName + " exiting.");
    }
}

