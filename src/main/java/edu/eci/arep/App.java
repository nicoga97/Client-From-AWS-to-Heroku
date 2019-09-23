package edu.eci.arep;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class App 
{

    public static void main( String[] args ) throws MalformedURLException, InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(args[1]));
        ArrayList<Callable<Integer>> callables=new ArrayList<>();
        int numberOfRequests=Integer.parseInt(args[2]);
        int requestsPerThread=numberOfRequests/Integer.parseInt(args[1]);
        for(int i=0;i<Integer.parseInt(args[1]);i++){
            numberOfRequests=numberOfRequests-requestsPerThread;
            if(numberOfRequests>=requestsPerThread){
                callables.add(new Request(args[0],requestsPerThread));
            }else{
                callables.add(new Request(args[0],numberOfRequests));
            }
        }
        List<Future<Integer>> futures=threadPool.invokeAll(callables);
        awaitTerminationAfterShutdown(threadPool);
        int res=0;
        for(Future future:futures){
            res= res+(Integer) future.get();
        }
        System.out.println(res+" of "+args[2]);

    }

    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.MINUTES)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
