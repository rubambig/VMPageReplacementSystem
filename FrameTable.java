import java.util.*;
/************************************************************
 * A frame table data structure
 * Keeps track of process and page is associated with frames.
 * Physical memory is specified to be 16 KB with 1KB frames.
 * There are 16 total frames in physical memory.
 * The frame table also keeps track of its free frames.
 * @author Gloire Rubambiza
 * @since 11/12/2017
 ************************************************************/
 public class FrameTable {

   /** The array of frames in physical memory. */
   private Frame [] frameTable;

   /** The max amount of frames to be created. */
   private int max = 16;

   /** The list of free frames in the system. */
   private boolean [] freeFrameList;

   /*************************************
    * Instantiates an array of 16 frames
    *************************************/
    public FrameTable () {
      this.frameTable = new Frame [max];
      int i;

      // Creates each new PCB
      for ( i = 0; i < max; i++ ) {
        frameTable[i] = new Frame ();
        freeFrameList[i] = true;
      }
    }

    /**
     * TO-DO
     * - Keep track of which frame/page pair was least recently used
     * - Use LRU replacement and reupdate the page table of a process kicked out
     * - Check if any frames are free before bringing a new page in
     * - Update the free frame list after bringing a new page in
     * - Update the frame table(new PID, new page) when a new page is brought in
     */

 }
