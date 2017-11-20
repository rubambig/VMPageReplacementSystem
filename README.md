# VMPageReplacementSystem
Implements virtual memory simulation with LRU page replacement.

Physical memory (16KB) is comprised of 16 frames of 1KB each.


Virtual memory (64KB) is comprised of 64 pages of 1KB each.

As page faults occur, pages will be removed from physical memory and replaced by other pages using
the Least-Recently-Used replacement algorithm.

The system implements global frame allocation, LRU page replacement and pure demand paging.

The LRU algorithm is implemented using a queue that keeps track of a frame pair that has been
recently reused.

The page table of a running process and current state of physical memory will be displayed using the
Java Swing package i.e. JFrames, JPanel, and text areas.
