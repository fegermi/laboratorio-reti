public class GregoryLeibniz implements Runnable{
    private double accuracy;
    private double pi;

    //Costruttore
    public GregoryLeibniz(double accuracy){
        this.accuracy = accuracy;
    }

    public void run(){
        this.pi = 0;
        int den = 1;
        int sign = 1;

        while(!Thread.currentThread().isInterrupted() && 
            Math.abs(this.pi - Math.PI) >= this.accuracy){
                this.pi += 4.*sign/den ;
                den += 2 ;
                sign *= -1 ;
        }

        System.out.println("PI calcolato:\t" + this.pi);
		System.out.println("Math.PI:\t" + Math.PI);
		System.out.println("Accuratezza:\t" + Math.abs(Math.PI - this.pi) );

    }
    
}
