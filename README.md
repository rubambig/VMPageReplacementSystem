# VMPageReplacementSystem(ViMPaReS)

**Note: The aim of the project was to demonstrate an understanding of page replacement, especially LRU replacement and pure demand paging. Specifically, the project is an animation of the Figure 8.6 of the Operating Systems Concepts Essentials, 2nd Edition book by Silberschatz. The system was built as an easy way to explain the idea of page replacement to user with elementary background on Operating Systems concepts i.e. processes, paging, virtual memory, etc. 

## A. Summary
1. Implements virtual memory simulation with LRU page replacement.

2. Physical memory (16KB) is comprised of 16 frames of 1KB each.

3. Virtual memory (64KB) is comprised of 64 pages of 1KB each.

4. The system implements global frame allocation, pure demand paging, LRU page replacement.

**- Global frame allocation**: We map pages to frames as frames are available, this is achieve by simulating pseudorandomness when checking for a free frame. After a few unsuccessful attempts, the program looks for a free frame sequentially. 

**- Pure demand paging**: We only bring pages for a process as they are requested by the process. This simplified the data structure design since we can just add or remove entries to the process' page tables as the pages come in and are assigned frames in the frame table(physical memory).

**- LRU page replacement**: When a page fault occurs and there are no free frames, we "choose a victim" for replacement by choosing the least recently used process/page pair i.e. the frame containing such a pair. This is achieved by popping the head of the LRU queue and updating the page tables of the victim and replacing processes.

5. At the completion of an input run of memory references, the system displays the total number of references made by each process and its total number of page faults i.e. final stats on the current performance of the system. 

## B. Overview of Data Structures
1. A PCB has a PID (integer) and page table associated with it. It is implemented as an object with pointers to the PID and page table. 
  1.a. The page table is implemented as a Hashtable with 64 entries since virtual memory is comprised of 64KB of 1KB pages. 
  1.b. The decision to make it a Hashtable was influenced by the ability to easily change the key value pairs without having to search the entire table for the pair’s location. 

2. A ProcessTable is comprised of 10 PCBs to keep track of each process as it begins making memory references. The processes are named from 1 to 10 with their associated data structures i.e. the PID and a hashtable of integers for page/frame pairs. 

3. A Frame is comprised of its associated page number and the process that owns the page. It is also implemented as an object with pointers to these fields. 
  3.a. There are 16 total frames since physical memory is comprised of 16KB of 1KB pages. This allows for a direct mapping of pages to frames.
  3.b. The PID and page number are initially set to -1 when a frame is created, this signifies the frame is not occupied yet. 
  3.c. Updating a frame only involves changing the contents of the page owner and page number when a new page is brought in regardless of the nature of a page fault.

4. A FrameTable is an array of 16 frames and an associated frame list of the same size. The frame table will be the main data structure sending messages to each individual frame as changes are caused by page faults. Also, the LRU replacement occurs at the frame table level. The frame list is used to keep track of which frames (in the 16 slots) are available for bringing in a new page. 

5. A Tables object is a unified data structure with pointers to a process table and frame table. It allows for a centralized way to communicate with both the Frames and PCBs as new memory references are made.

6. The Controller handles all the main action. It creates a unified Tables object that contains one process table (for handling each process’s data structures) and one frame table (for handling each page/frame pair and the free frame list).  Further, the controller, with help from the SystemGUI, acts as a listener for user actions and relays messages to the GUI and table data structures.

## C. Graphical User Interface(GUI) Functionality

**PhysicalStatePanel**
Displays the current contents of physical memory. It is comprised of labels for each frame where the contents of the given frame are displayed. The contents are obtained from the FrameTable which is passed to the panel by the SystemGUI. 

**PageTablePanel**
Displays the current contents of a given process’ page table. As a new page is referenced, the panel displays the page table of the process that makes the page request ( the text in the labels is reset to empty strings and updated with the contents of the new process). Although there are 64 pages for each process, only the pages associated with a memory location are displayed in the panel. 

**FinalStatsPanel**
Displays the final statistics of a given input run. The controller notifies the tables of a new memory reference. When the input ends, the PCB of each process, which contains the information on total memory references and page faults, is passed to the panel to be displayed. 

**SystemGUI**
The GUI constitutes the middle-man between the panels and the controller. Notifications regarding page faults and physical memory changes are passed from the controller and relayed to the appropriate panel. The controller is charged with listeners for the user actions, and the buttons are passed to the SystemGUI from the commands panel and relayed to the controller.

**CommandsPanel**
Displays the LRU victim, memory reference, and possible actions to the user. In other words, it provides an opportunity for the user to step through memory references and understand the state of the system.

**Next →** Prompts the controller to read the next memory reference. The memory references are stored in a queue of integer arrays. The first index in an array contains the process number whereas the second index contains the page reference made by the process. The input is then processed by controller where page faults are handled according to the type of fault (i.e. a fault with free frames available versus a fault requiring a page replacement. 

**Run to Next Fault →** Instructs the controller to keep reading inputs until a page fault is detected. In case of a fault, the controller notifies the GUI of the victim picked for page replacement and the reading of input is halted. 

**Run to Completion →** Instructs the controller to keep reading inputs until, when polled, the Queue of inputs returns null. At completion, the controller instructs the GUI to display the final statistics for this run of input. Additionally, all the other user command buttons are disabled when the end of file is reached. 

**Exit →** exits the program safely through a `system.exit(0)` call. 

