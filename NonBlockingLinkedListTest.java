import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class Node {
    int value;
    AtomicReference<Node> next;

    Node(int value) {
        this.value = value;
        this.next = new AtomicReference<>(null);
    }
}

class NonBlockingSortedLinkedList {
    private AtomicReference<Node> head = new AtomicReference<>(null);

    public void insert(int value) {
        Node newNode = new Node(value);

        while (true) {
            Node prevNode = null;
            Node currentNode = head.get();

            // Traverse the list to find the correct position for insertion
            while (currentNode != null && currentNode.value < value) {
                prevNode = currentNode;
                currentNode = currentNode.next.get();
            }

            newNode.next.set(currentNode);

            if (prevNode == null) {
                // newNode should be the new head
                if (head.compareAndSet(currentNode, newNode)) {
                    return;
                }
            } else {
                // Insert newNode after prevNode
                if (prevNode.next.compareAndSet(currentNode, newNode)) {
                    return;
                }
            }
        }
    }

    public boolean delete(int value) {
        Node prevNode = null;
        Node currentNode = head.get();

        while (currentNode != null && currentNode.value < value) {
            prevNode = currentNode;
            currentNode = currentNode.next.get();
        }

        if (currentNode != null && currentNode.value == value) {
            if (prevNode == null) {
                // currentNode is the head
                if (head.compareAndSet(currentNode, currentNode.next.get())) {
                    return true;
                }
            } else {
                if (prevNode.next.compareAndSet(currentNode, currentNode.next.get())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean read(int value) {
        Node currentNode = head.get();

        while (currentNode != null && currentNode.value < value) {
            currentNode = currentNode.next.get();
        }

        return currentNode != null && currentNode.value == value;
    }
}

public class NonBlockingLinkedListTest {

    public static void main(String[] args) {
        int[] problemSizes = {2000, 20000, 200000};
        int[] threadCounts = {1, 2, 4, 6, 8, 10, 12, 14, 16};
        String[] workloads = {
            "0C-0I-50D",
            "50C-25I-25D",
            "100C-0I-0D"
            // Add other workloads as needed
        };

        for (int problemSize : problemSizes) {
            for (int numThreads : threadCounts) {
                for (String workload : workloads) {
                    runTest(problemSize, numThreads, workload);
                }
            }
        }
    }

    private static int[] generateTestData(int size) {
        int[] data = new int[size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            data[i] = rand.nextInt(1000); // Adjust range as needed
        }

        return data;
    }

    private static Runnable createTestRunnable(String workload, NonBlockingSortedLinkedList list, int[] data) {
        switch (workload) {
            case "0C-0I-50D":
                return () -> workload_0C_0I_50D(list, data);
            case "50C-25I-25D":
                return () -> workload_50C_25I_25D(list, data);
            case "100C-0I-0D":
                return () -> workload_100C_0I_0D(list, data);
            default:
                throw new IllegalArgumentException("Invalid workload: " + workload);
        }
    }

    private static void workload_0C_0I_50D(NonBlockingSortedLinkedList list, int[] data) {
        int deletes = data.length / 2;
        for (int i = 0; i < deletes; i++) {
            int valueToDelete = data[i];
            list.delete(valueToDelete);
        }
    }

    private static void workload_50C_25I_25D(NonBlockingSortedLinkedList list, int[] data) {
        int reads = data.length / 2;
        int inserts = data.length / 4;
        int deletes = data.length / 4;

        for (int i = 0; i < reads; i++) {
            int valueToRead = data[i];
            list.read(valueToRead);
        }

        for (int i = reads; i < reads + inserts; i++) {
            int valueToInsert = data[i];
            list.insert(valueToInsert);
        }

        for (int i = reads + inserts; i < reads + inserts + deletes; i++) {
            int valueToDelete = data[i];
            list.delete(valueToDelete);
        }
    }

    private static void workload_100C_0I_0D(NonBlockingSortedLinkedList list, int[] data) {
        int reads = data.length;
        for (int i = 0; i < reads; i++) {
            int valueToRead = data[i];
            list.read(valueToRead);
        }
    }

    private static void runTest(int problemSize, int numThreads, String workload) {
        NonBlockingSortedLinkedList list = new NonBlockingSortedLinkedList();
        int[] testData = generateTestData(problemSize);
        Runnable testRunnable = createTestRunnable(workload, list, testData);

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        long startTime = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(testRunnable);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        double executionTimeInSeconds = (endTime - startTime) / 1e9;

        double totalOperations = numThreads * (problemSize / 2); // Adjust for workload
        double throughput = totalOperations / executionTimeInSeconds;

        System.out.println("\nConcurrent Linked List Performance Test");
        System.out.println("Problem Size: " + problemSize);
        System.out.println("Threads: " + numThreads);
        System.out.println("Workload: " + workload);
        System.out.println("Execution Time: " + executionTimeInSeconds + " seconds");
        System.out.println("Throughput: " + throughput + " ops/s\n");
    }
}
