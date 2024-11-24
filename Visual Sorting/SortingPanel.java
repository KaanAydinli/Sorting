import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import javax.swing.JPanel;

public class SortingPanel extends JPanel implements Runnable{

    int cursorI;
    int cursorJ;
    Thread thread;
    int[] array;
    int randomTemp;
    int temp;
    Random gen = new Random();
    boolean isSorted = false;
    int state = 2; // 0 Bubble Sort -- 1 Insertion Sort -- 2 Selection Sort -- 3 Merge Sort -- 4 Quick Sort
    int key;
    int currentIndex = 1; // Current index being sorted
    int compareIndex = 0;
    int minIndex = 0;
    int[] tempArray; // Temporary array for merging
    int segmentSize = 1; // Current size of the segments being merged
    int leftStart = 0;
    int speed = 10;
    int arraySize = 100;
    private static int low = 0, high = 0; // Current subarray being partitioned
    private static int pivot = 0, i = 0, j = 0; // Partitioning state variables
    private static boolean partitioning = false;

    private static int maxDigits = 0; // Maximum number of digits in the numbers
    private static int currentDigit = 0; // Current digit being processed
    private static int[] output; // Array to store the intermediate sorted result
    private static int[] count = new int[10]; // Counting array for digits
    private static boolean countingPhase = true; // Whether we are in the counting phase
    private static int index = 0;
    Stack<int[]> stack = new Stack<>();
    long start,finish;
    public SortingPanel(){

        
        setPreferredSize(new Dimension(1100,600));
        setBackground(Color.black);
        thread = new Thread(this);
        thread.start();
        array = new int[arraySize];
        tempArray = new int[array.length];
        maxDigits = getMaxDigits(array);
        output = new int[array.length];

        stack.push(new int[]{0, array.length - 1});
        shuffle();

        key = array[cursorI];
    }
    public void shuffle(){
        array = new int[arraySize];
        tempArray = new int[array.length];
        output = new int[array.length];
        
        for(int i = 0; i < arraySize; i++){
            array[i] = i + 1;
        }

        for(int i = 0; i < arraySize; i++){
            randomTemp = gen.nextInt(arraySize);
            temp = array[randomTemp];
            array[randomTemp] = array[i];
            array[i] = temp;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.green);
        for(int i = 0; i < array.length - 1; i++){
            g2.fillRect(i * 1100 / arraySize, 590 - array[i] * 300 / arraySize, 1000 / arraySize, array[i] * 300 / arraySize);
        }
        if(isSorted){
            g2.setColor(Color.white);
            g2.setFont(new Font("Calibri",1,40));
            g2.drawString("Sorting Complete", 350, 300);
        }
    }
    public void sort(){

        //Bubble Sort
        if(state == 0){
            if(!isSorted){
                if(array[cursorJ] > array[cursorJ + 1]){
                    temp = array[cursorJ];
                    array[cursorJ] = array[cursorJ + 1];
                    array[cursorJ + 1] = temp;
                }
                cursorJ++;
                if(cursorJ == arraySize - 1 - cursorI){
                    cursorJ = 0;
                    cursorI++;
                }
                if(cursorI == arraySize - 1){
                    isSorted = true;
                }
            }
        }
        //Insertion Sort
        else if(state == 1){
            
            if(!isSorted){

                if (compareIndex < 0 || array[compareIndex] <= array[compareIndex + 1]) {

                    compareIndex = currentIndex;
                    currentIndex++;
                } else {

                    int temp = array[compareIndex];
                    array[compareIndex] = array[compareIndex + 1];
                    array[compareIndex + 1] = temp;
                    compareIndex--;
                }
                if(compareIndex == arraySize - 1){
                    isSorted = true;
                }
            }

        }
        //Selection Sort
        else if(state == 2){
            if(!isSorted){
                if (compareIndex == 0) {

                    minIndex = cursorI;
                    compareIndex = cursorI + 1;
                }
        
                if (compareIndex < array.length) {
    
                    if (array[compareIndex] < array[minIndex]) {
                        minIndex = compareIndex;
                    }
                    compareIndex++;
                } else {
                    
                    temp = array[cursorI];
                    array[cursorI] = array[minIndex];
                    array[minIndex] = temp;
        
                    cursorI++;
                    compareIndex = 0; 
                }

                if(cursorI == arraySize - 1){
                    isSorted = true;
                }
            }

        }
        //Merge Sort
        else if(state == 3){
            if(!isSorted){
                int rightStart = Math.min(leftStart + segmentSize, array.length);
                int rightEnd = Math.min(leftStart + 2 * segmentSize, array.length);

                if (rightStart < rightEnd) {
                    int i = leftStart, j = rightStart, k = leftStart;

                    // Merge the two segments into tempArray
                    while (i < rightStart && j < rightEnd) {
                        if (array[i] <= array[j]) {
                            tempArray[k++] = array[i++];
                        } else {
                            tempArray[k++] = array[j++];
                        }
                    }

                    // Copy any remaining elements from the left segment
                    while (i < rightStart) {
                        tempArray[k++] = array[i++];
                    }

                    // Copy any remaining elements from the right segment
                    while (j < rightEnd) {
                        tempArray[k++] = array[j++];
                    }

                    // Copy the merged segment back into the original array
                    for (int index = leftStart; index < rightEnd; index++) {
                        array[index] = tempArray[index];
                    }
                }

                // Move to the next pair of segments
                leftStart += 2 * segmentSize;

                // If all pairs for the current segment size are merged, increase segment size
                if (leftStart >= array.length) {
                    leftStart = 0;
                    segmentSize *= 2;
                }
                if(segmentSize >= array.length){
                    isSorted = true;
                  
                }
            }
            
        }
        else if(state == 4){
            if(!isSorted){
                if (!partitioning) {
                    if (stack.isEmpty()) return; // No more subarrays to process
        
                    // Pop a new subarray to partition
                    int[] range = stack.pop();
                    low = range[0];
                    high = range[1];
                    if (low >= high) return; // Single-element subarray, no sorting needed
        
                    // Initialize partitioning state
                    pivot = array[high]; // Choose pivot as the last element
                    i = low - 1;
                    j = low;
                    partitioning = true; // Mark partitioning in progress
                }
        
                // Perform one step of the partitioning process
                if (j <= high - 1) {
                    if (array[j] <= pivot) {
                        i++;
                        // Swap array[i] and array[j]
                        int temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                    j++;
                } else {
                    // Finalize the partition by placing the pivot
                    int temp = array[i + 1];
                    array[i + 1] = array[high];
                    array[high] = temp;
        
                    // Push left and right subarrays onto the stack
                    stack.push(new int[]{low, i});
                    stack.push(new int[]{i + 2, high});
        
                    partitioning = false; // Partitioning for this subarray is done
                }
                if (stack.isEmpty()) {
                    isSorted = true;
                    finish = System.nanoTime();
                    System.out.println(finish - start);
                }
            
            }
        }
        else if( state == 5){
             int divisor = (int) Math.pow(10, currentDigit);

        if (countingPhase) {
            // Initialize count array
            if (index == 0) {
                Arrays.fill(count, 0);
            }

            // Count occurrences of digits
            if (index < array.length) {
                int digit = (array[index] / divisor) % 10;
                count[digit]++;
                index++;
            } else {
                // Move to the accumulating phase
                countingPhase = false;
                index = 0;
            }
        } else {
            // Accumulate the counts
            if (index > 0 && index < 10) {
                count[index] += count[index - 1];
                index++;
            } else if (index == 0) {
                index = 1; // Start accumulation at index 1
            } else {
                // Place elements into the output array
                if (index < array.length) {
                    int digit = (array[array.length - 1 - index] / divisor) % 10;
                    output[--count[digit]] = array[array.length - 1 - index];
                    index++;
                } else {
                    // Move sorted output to array
                    System.arraycopy(output, 0, array, 0, array.length);

                    // Prepare for the next digit
                    currentDigit++;
                    countingPhase = true;
                    index = 0;
                }
            }
        }
        }

        
    }
    private int getMaxDigits(int[] array) {
        int max = Arrays.stream(array).max().getAsInt();
        return Integer.toString(max).length();
    }

    @Override
    public void run() {
        
        while(thread != null){
            try {
                sort();

                repaint();
                
                Thread.sleep(100 / speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void resetEverything(){
        cursorI = 0;
        cursorJ = 0;
        randomTemp = 0; 
        temp = 0;
        key = 0;
        compareIndex= 0;
        currentIndex = 1;
        minIndex = 0;
        segmentSize = 1;
        leftStart = 0;
        stack.push(new int[]{0, array.length - 1});
    }
}