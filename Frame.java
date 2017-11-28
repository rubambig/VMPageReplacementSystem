/***************************
* A frame data structure
* @author Gloire Rubambiza
* @since 11/12/2017
**************************/
public class Frame {

  /** The PID of the process associated with the frame. */
  private int processPID;

  /** The page number associated with the frame. */
  private int page;

  /**********************************************
  * Instantiates a frame with given information.
  * To be used as part of frame table.
  *********************************************/
  public Frame () {
    this.processPID = -1;
    this.page = -1;
  }

  /*******************************************
  * Get the process associated with the frame.
  ********************************************/
  public int getPID () {
    return this.processPID;
  }

  /*****************************************
  * Get the page associated with the frame.
  *****************************************/
  public int getPage () {
    return this.page;
  }

  /*******************************************
  * Update the page associated with the frame.
  ********************************************/
  public void updatePage ( int page ) {
    this.page = page;
  }

  /***********************************************
  * Update the process associated with the frame.
  ***********************************************/
  public void updateProcess ( int pid ) {
    this.processPID = pid;
  }
}
