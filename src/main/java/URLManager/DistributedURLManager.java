package URLManager;

import Dispatcher.Dispatcher;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DistributedURLManager {
    public static void main(String[] args) {
        try {
            // Create the execution environment
            final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

            // Create the Dispatcher with a fixed number of workers (e.g., 3)
            Dispatcher dispatcher = new Dispatcher(3);

            // Submit job to fetch URLs from the database
            ((Dispatcher) dispatcher).submitJob("urlJob", "roundRobin");

            // Create a DataStream using the URLSource
            DataStream<String> urlStream = env.addSource(new URLSourceBook());


            // Print the retrieved URLs to the console
            urlStream.print();

            // Execute the Flink job
            env.execute("Distributed URL Manager");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}