# HPC_ASSIGNMENT_02
Created a concurrent double-linked list (sorted list) using the following synchronization techniques:

- Coarse-grain Synchronization
- Fine-grain Synchronization
- Optimistic Synchronization
- Lazy Synchronization
- Non-blocking Synchronization

Verified the performance of the concurrent data structure for different problem sizes (2 × 1000, 2 × 10000, and 2 × 100000) by varying the number of threads (1, 2, 4, 6, 8, 10, 12, 14, and 16) and workloads 
(0C-0I-50D, 50C-25I-25D, and 100C-0I-0D).

# Developing a Concurrent Double-Linked List (Sorted List) with Synchronization Techniques and Performance Evaluation

# Concurrent Double-Linked List Implementation:

Create a concurrent double-linked list data structure that maintains a sorted order of elements. Each node in the list should contain the data and references to the previous and next nodes.

# Synchronization Techniques:

Implement the following synchronization techniques in separate versions of the double-linked list:

a. Coarse-grain Synchronization:
Use locks to protect the entire list during insertions, deletions, and lookups.
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/fa86b775-41f8-4f2b-9de0-f7b7d7f0cc71)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/c0b55eb2-c038-48f2-aab8-9d70b5814c4e)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/059e6f1a-8c84-4d90-ad3f-d59468153dd5)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/9cb60be5-7c17-4a67-bbd2-78fe7b524cb0)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/159d533a-17c2-4e13-a72d-68b7dd0c8059)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/9883f237-f010-4be1-9009-e5448302d79e)


b. Fine-grain Synchronization:
Use locks on individual nodes to allow concurrent access to different parts of the list.
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/2796e884-7d0c-44eb-a043-6a1d2ab5689c)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/89e81635-ade0-409a-b8b2-5ea95f98d811)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/79c50671-dc06-4622-8945-77c3d3c1694d)


c. Optimistic Synchronization:
Use optimistic techniques such as lock-free algorithms or software transactional memory to handle concurrent operations.
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/f1b26014-4186-41a1-812d-45c55bec1d99)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/2121e0e2-9924-4384-b6fc-bcfda5cd80ac)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/8b4f8ecd-e2f4-4db8-bc35-9436751064bb)


d. Lazy Synchronization:
Implement a lazy synchronization approach, allowing multiple threads to perform operations concurrently and resolve conflicts when necessary.
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/4934dbc2-38e1-4683-9f5a-bd434e3943d2)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/84d1eaba-6076-447f-8a48-dfd2dd10f362)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/2142e27c-7579-4321-85bf-588519641b8d)


e. Non-blocking Synchronization:
Implement non-blocking synchronization techniques like compare-and-swap (CAS) to enable threads to make progress without blocking.
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/864f3cfe-a799-4b83-8a8d-95a43c315c1e)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/b25e06a0-d0d0-404d-833c-66120bc3969f)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/3edda771-25e4-4374-991d-8c0297b1c675)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/99a4a2db-7880-41b0-8f22-873b7458b852)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/a020e2f9-64b9-48dc-b6cf-1f7f6b94d56e)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/95a2ead3-6394-4346-94e3-ca078dfcd827)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/1d14a374-f5c1-4f36-b752-98163123123c)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/c5db2d54-b588-4ebf-92ba-ebe428a1168d)
![image](https://github.com/sumanthchennamsetty/HPC_ASSGN_02/assets/138759091/9c3e95d5-6fec-4c17-8d39-0c2b4fa68a72)

# Performance Evaluation:

For performance testing, use the following problem sizes and workloads:

Problem Sizes: 2000, 20000, and 200000 elements in the list.

Number of Threads: Test the concurrent data structure with 1, 2, 4, 6, 8, 10, 12, 14, and 16 threads to simulate various levels of concurrency.

# Workloads:

0C-0I-50D: 0% concurrent inserts, 0% concurrent inserts, 50% concurrent deletes.

50C-25I-25D: 50% concurrent inserts, 25% concurrent inserts, 25% concurrent deletes.

100C-0I-0D: 100% concurrent inserts, 0% concurrent inserts, 0% concurrent deletes.

# Performance Metrics:

Measure the following performance metrics for each combination of problem size, number of threads, and workload:

Throughput: The number of operations (insertions, deletions, or lookups) per unit of time.
Latency: The time taken for an individual operation to complete.
Scalability: Analyze how the system's performance scales with an increasing number of threads.
Identify any bottlenecks or contention points in each synchronization technique.
Analysis and Comparison:

Compare the performance of the different synchronization techniques for each problem size, thread count, and workload. Identify the most suitable synchronization technique(s) for different scenarios.

# Optimization and Further Testing:

Based on the results, consider optimizing the chosen synchronization technique(s) or experimenting with other variations to improve performance further.

This modified plan provides a framework for developing and evaluating a concurrent double-linked list with various synchronization techniques while testing its performance under different conditions. The specific implementation details and code are beyond the scope of this response but can be achieved using relevant programming languages and concurrency libraries or frameworks.
