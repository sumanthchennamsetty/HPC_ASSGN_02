import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Node {
    int value;
    Node next;
    Node prev;

    Node(int value) {
        this.value = value;
    }
}

class FineGrainSortedLinkedList {
    private Node head;

    public void insert(int value) {
        Node newNode = new Node(value);
        Node prevNode, nextNode;

        synchronized (this) {
            prevNode = findPrevNode(value);
            nextNode = prevNode.next;
            prevNode.next = newNode;
        }

        synchronized (newNode) {
            newNode.prev = prevNode;
            newNode.next = nextNode;
            if (nextNode != null) {
                nextNode.prev = newNode;
            }
        }
    }

    public boolean delete(int value) {
        Node current, prevNode, nextNode;
        boolean deleted = false;

        synchronized (this) {
            current = head;
            while (current != null && current.value < value) {
                current = current.next;
            }

            if (current != null && current.value == value) {
                prevNode = current.prev;
                nextNode = current.next;

                if (prevNode != null) {
                    prevNode.next = nextNode;
                } else {
                    head = nextNode;
                }

                if (nextNode != null) {
                    nextNode.prev = prevNode;
                }

                deleted = true;
            }
        }

        return deleted;
    }

    public boolean read(int value) {
        Node current;

        synchronized (this) {
            current = head;
            while (current != null && current.value < value) {
                current = current.next;
            }

            return current != null && current.value == value;
        }
    }

    private Node findPrevNode(int value) {
        Node current = head;
        Node prev = null;

        while (current != null && current.value < value) {
            prev = current;
            current = current.next;
        }

        return prev;
    }
}

public class FineGrainLinkedListTest {
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
                    int[] testData = generateTestData(problemSize);
                    FineGrainSortedLinkedList list = new FineGrainSortedLinkedList();
                    Runnable testRunnable = createTestRunnable(workload, list, testData);
                    runTest(problemSize, numThreads, testRunnable, workload);
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

    private static Runnable createTestRunnable(String workload, FineGrainSortedLinkedList list, int[] data) {
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

    private static void workload_0C_0I_50D(FineGrainSortedLinkedList list, int[] data) {
        int deletes = data.length / 2;
        for (int i = 0; i < deletes; i++) {
            int valueToDelete = data[i];
            list.delete(valueToDelete);
        }
    }

    private static void workload_50C_25I_25D(FineGrainSortedLinkedList list, int[] data) {
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

    private static void workload_100C_0I_0D(FineGrainSortedLinkedList list, int[] data) {
        int reads = data.length;
        for (int i = 0; i < reads; i++) {
            int valueToRead = data[i];
            list.read(valueToRead);
        }
    }

    private static void runTest(int problemSize, int numThreads, Runnable testRunnable, String workload) {
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
