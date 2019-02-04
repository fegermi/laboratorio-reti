public class Assignment1_1 {
	   
	public static void main(String[] args) throws InterruptedException{
	      
	        //Default values 
	        double accuracy = 0.00000001;  // 1.0E-8
	        int timeMax =  30000 ;         // 30 s

	        //Parsing input  
	        if(args.length >=1){
	            try{
	                double tmpAccuracy = Double.parseDouble(args[0]);
	                if(tmpAccuracy != 0) accuracy = tmpAccuracy ;
	            }
	            catch(NumberFormatException c){
	                System.out.println("NumberFormatException: Accuracy");
	            }

	            if(args.length >= 2){

	                try{
	                    int tmpTimeMax = Integer.parseInt(args[1]);
	                    if(tmpTimeMax > 0) timeMax = tmpTimeMax;
	                }
	                catch(NumberFormatException c){
	                    System.out.println("NumberFormatException: timeMax");
	                }
	            }
	        }
	        
	        System.out.println("Accuratezza Pi:\t"+accuracy);
	        System.out.println("Timeout thread:\t"+ timeMax + " ms");

	        GregoryLeibniz gregoryLeibniz = new GregoryLeibniz(accuracy);
	        Thread thread = new Thread(gregoryLeibniz);
	        thread.start();
	        thread.join(timeMax);
			if(thread.isAlive()) thread.interrupt();
	    }
	}

