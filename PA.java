import java.io.*;
import java.util.*;

// ============================
// TODO (1) MAIN CLASS (PA2)
// ============================

class PA2 {

    public static void main(String[] args) {
        try {
            // TODO (1.1)
            // Prompt the user to enter an input file name
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter an input file name: ");

            // Read the filename from standard input
            String inputFileName = scanner.nextLine(); // ChatGPT used to learn syntax for prompting strings in java

            // Debugging: Print filename before reading (confirmation message)
            System.out.println();
            System.out.println("=============================================");
            System.out.println(" 📂  Reading input file: " + inputFileName);

            // TODO (1.2)
            // Read the input file and store its contents as a string.
            String fileContents = readFile(inputFileName);

            // If the file is empty or cannot be read, print an error message and terminate execution.
            if (fileContents.isEmpty()) {
                System.err.println("Error: File cannot be read or is empty.");
                return;
            }

            // TODO (1.3)
            // Process input text to extract words into a 1-D array.
            // Trim input, split words by whitespace, and store words in an array.

            String[] words = fileContents.trim().split("\\s+");

            // Debugging: Print words before sorting
            System.out.println("---------------------------------------------");
            System.out.println(" ⊗  Words before sorting: " + Arrays.toString(words));
            System.out.println("---------------------------------------------");

            // TODO (1.4)
            // Call MergeSort to sort words alphabetically.
            MergeSort.mergeSort(words, 0, words.length - 1);

            // Debugging: Print words after sorting
            System.out.println("*********************************************");
            System.out.println(" ✓ Words after sorting: " + Arrays.toString(words));

            // TODO (1.5)
            // Call AnagramGrouper.groupAnagrams() to group words into singly linked lists (anagram groups).
            Map<String, SinglyLinkedList> anagramGroups = AnagramGrouper.groupAnagrams(words); // used ChatGPT to learn what a Map is and how it works

            // Debugging: Print grouped anagrams before final output
            System.out.println("\n =============================================");
            System.out.println(" {} Grouped Anagrams: " + anagramGroups);

            // TODO (1.6)
            // Call printFinalOutput() to print and save the final formatted output (anagram groups).
            printFinalOutput(anagramGroups, inputFileName);

        } catch (Exception e) {  // Catch exceptions and handle errors.
            e.printStackTrace(); // Print stack trace for debugging.
        }
    }

    // ================================
    // FUNCTION TO READ FILE CONTENT
    // ================================

    private static String readFile(String fileName) throws IOException {

        // TODO (1.7)
        // Create a File object for the given file name.
        File file = new File(fileName);

        // TODO (1.8)
        // Check if the file exists before attempting to read.
        if (!file.exists()) { // ** '?' replaced with file.exists() check **

            // TODO (1.9)
            // Print an error message if the file is not found.
            System.err.println("Error: File '" + fileName + "' not found.");

            // TODO (1.10)
            // Return an empty string if the file doesn't exist.
            return "";
        }

        // TODO (1.11)
        // Use StringBuilder to store file contents.
        StringBuilder fileContent = new StringBuilder();

        // TODO (1.12)
        // Use BufferedReader to read the file line by line.
        BufferedReader reader = new BufferedReader(new FileReader(file));

        // TODO (1.13)
        // Declare a variable to store each line read from the file.
        String line;

        // TODO (1.14)
        // Iterate through the file and read it line by line.
        while ((line = reader.readLine()) != null) { // '?' replaced with 'reader.readLine()'

            // TODO (1.15)
            // Append each line to StringBuilder.
            fileContent.append(line).append(" "); // Adds space between words
        }

        // TODO (1.16)
        // Close BufferedReader.
        reader.close();

        // TODO (1.17)
        // Return the final string containing the file content.
        return fileContent.toString().trim(); // Trim extra spaces
    }

    // ==========================================
    // FUNCTION TO PRINT AND SAVE FINAL OUTPUT
    // ==========================================

    public static void printFinalOutput(Map<String, SinglyLinkedList> groups, String inputFileName) {

        // TODO (1.18)
        // Print a header for the final grouped anagrams output.
        System.out.println("\n=============================================");
        System.out.println("\n          Final Grouped Anagrams          ");
        System.out.println("\n=============================================");

        // TODO (1.19)
        // Initialize a counter to number the anagram groups in the output.
        int groupCount = 0;

        // TODO (1.20)
        // Create a StringBuilder to store the formatted output before saving to a file.
        StringBuilder groupContent = new StringBuilder();

        // Iterate over the grouped anagrams (values of the map).
        for (SinglyLinkedList group : groups.values()) {

            // TODO (1.21)
            // Ensure the group is not null and contains words before printing.
            if (group != null && !group.isEmpty()) {
            // a mix of ENSF 409 notes and chatGPT thaught me how to use these string functions
                // TODO (1.22)
                // Format the group as a numbered entry and remove any extra spaces.
                String formattedGroupContent = (groupCount + 1) + ": " + group.toString().replaceAll("\\s+", "");


                // TODO (1.23)
                // Print the formatted group to the console.
                System.out.println(formattedGroupContent);

                // TODO (1.24)
                // Append the formatted group to the output content for file saving.
                groupContent.append(formattedGroupContent).append("\n");

                // TODO (1.25)
                // Increment the counter for the next group.
                groupCount++;

            }
        }

        // ==========================
        // FILE OUTPUT HANDLING
        // ==========================

        // TODO (1.26)
        // Save the final grouped anagrams to a text file with a modified filename based on the input file.
        String outputFileName = inputFileName.replace(".txt","_anagrams.txt");

        // TODO (1.27)
        // Use try-with-resources to ensure BufferedWriter is closed automatically after writing.
        // Initialize BufferedWriter for writing to the output file.
        // ChatGpt used here to explain what this means and why its done this way.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) { // ** YOUR CODE WHERE '?' **

            // TODO (1.28)
            // Write the final grouped anagrams output to the specified file.
            writer.write(groupContent.toString());

            // TODO (1.29)
            // Print a confirmation message indicating successful file saving.
            System.out.println("File saved to file \"" + outputFileName +"\" successfully");

            // TODO (1.30)
            // Handle any IOException that may occur during file writing.

        } catch (IOException e) { // ** YOUR CODE WHERE '?' **

            // TODO (1.31)
            // Print an error message to standard error (stderr) if file writing fails.
            System.err.println("An error occurred while writing to file \"" + outputFileName +"\"");
        }
    }
}

// ============================
// TODO (2) MERGE SORT CLASS
// ============================
class MergeSort {

    public static void mergeSort(String[] array, int left, int right) {
        // Referred to CPSC 319 slides and google to learn how to make this algorithm

        // left represents the starting index of the current subarray 'array'.
        // right represents the ending index of the current subarray 'array'.

        // TODO (2.1) If the subarray has at least two elements, then it can still be split further.
        if (left > right)  { // ** YOUR CODE WHERE '?' **

            // TODO (2.2)
            // Calculate the middle index to divide the array into two halves.
            int middle = (left + right) / 2;

            // Debugging: Print subarray being sorted
            // ENSF 409 notes used here
            System.out.println("( ................... )");
            System.out.println("Sorting subarray: " + Arrays.toString(Arrays.copyOfRange(array, left, right + 1)));

            // TODO (2.3)
            // Recursively sort the left half of the array.
            mergeSort(array, left, middle);

            // TODO (2.4)
            // Recursively sort the right half of the array.
            mergeSort(array, middle + 1, right);

            // TODO (2.5)
            // Merge the sorted left and right halves.
            merge(array, left, middle, right);
        }
    }

    // ==================================================
    // MERGE FUNCTION to combine two sorted subarrays.
    // ==================================================

    private static void merge(String[] array, int left, int mid, int right) {
        // Referred to CPSC 319 slides and google to learn how to make this algorithm

        // TODO (2.6)
        // Compute the sizes of the two subarrays to be merged.
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        // TODO (2.7)
        // Create temporary arrays to store elements from the left and right subarrays.
        String[] leftArray = new String[leftSize];
        String[] rightArray = new String[rightSize];

        // TODO (2.8)
        // Copy data from the original array into the left and right subarrays.
        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        // Debugging: Print subarrays before merging
        System.out.println("( ................... )");
        System.out.println(" ∪ Merging: " + Arrays.toString(leftArray) + " and " + Arrays.toString(rightArray));

        // TODO (2.9)
        // Merge the two subarrays by comparing their elements.
        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i].compareTo(rightArray[j]) <= 0) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // TODO (2.10)
        // Copy any remaining elements from `leftArray` to `array`.
        while (i < leftSize) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // TODO (2.11)
        // Copy any remaining elements from `rightArray` to `array`.
        while (j < rightSize) {
            array[k] = rightArray[j];
            j++;
            k++;
        }

        // Debugging: Print merged array
        System.out.println(" ≡ After Merge: " + Arrays.toString(Arrays.copyOfRange(array, left, right + 1)));
    }


    // =============================
    // MANUAL ARRAY COPY FUNCTION
    // =============================

    private static void manualCopy(String[] source, int sourceStart, String[] destination, int destStart, int length) {

        // TODO (2.12)
        // Iterate over the given range and copy elements
        for (int i = 0; i < length; i++) {

            // TODO (2.13)
            // Copy each element from source to destination at the correct index
            destination[destStart + i] = source[sourceStart + i];

        }
    }
}

// ====================================
// TODO (5) SINGLY LINKED LIST CLASS
// ====================================

class SinglyLinkedList {

    // TODO (5.1)
    // Declare a head node representing the start of the linked list
    private Node head;

    // TODO (5.2)
    // Define the node structure for a singly linked list.
    private static class Node {
        // TODO (5.2)
        // Store the Data (word) in this node
        String word;

        // Store the Pointer to the next node in the linked list
        Node next;

        // TODO (5.3)
        // Constructor for Node in SinglyLinkedList. Creates a new linked list node containing a word.
        public Node(String word) {
            this.word = word;
            this.next = null;
        }
    }

    // =====================================================
    // ADD SORTED METHOD to insert a word in sorted order
    // =====================================================
    public void addSorted(String word) {

        // TODO (5.4)
        // Allocate new Node
        Node newNode = new Node(word);

        // TODO (5.5)
        // Handle insertion at the beginning of the list:
        // If the list is empty (i.e., head == NULL) OR If word comes before head.data alphabetically
        if (head == null || word.compareTo(head.word) < 0) {
            // Insert at the beginning.
            newNode.next = head;
            head = newNode;
            // Return immediately after inserting at the head to avoid unnecessary traversal.
            return;
        }

    // TODO (5.6)
    // Position 'current' at the beginning of the singly linked list
    Node current = head;

    // TODO (5.7)
    // Traverse to find correct insertion point
    // Moves forward in the linked list until:
    // (1) The end of the list is reached (current.next == null).
    // (2) The first node with data lexicographically greater than or equal to word is found.

    while(current.next != null && current.next.word.compareTo(word) < 0){
        current = current.next;
    }

    // TODO (5.8)
    // Insert the new node at the correct position
    newNode.next = current.next;
    current.next = newNode;
	}

// TODO (5.9)
// Implement method to check if the list is empty
public boolean isEmpty() {
    return head == null;
}

// ===============================================================
// METHOD TO RETURN A STRING REPRESENTATION OF THE LINKED LIST.
//
// This method produces a human-readable representation of the linked list, where elements are separated by spaces.
// Example:
//          --------------------------------------------------
//          SinglyLinkedList list = new SinglyLinkedList();
//          list.addSorted("banana");
//          list.addSorted("apple");
//          list.addSorted("cherry");
//          --------------------------------------------------
//          Final Output: "apple banana cherry"
// ===============================================================

@Override // Java annotation that indicates a method overrides a method from its superclass.

// Convert the linked list to a formatted string representation
public String toString() {

    // TODO (5.10)
    // Initialize a StringBuilder to store the result
    StringBuilder result = new StringBuilder();

    // TODO (5.11)
    // Start from the head of the linked list
    Node current = head;

    // TODO (5.12)
    // Traverse the entire linked list
    while (current != null) { // ** YOUR CODE WHERE '?' **

        // TODO (5.13)
        // Append the current node's data to the string
        result.append(current.word);

        // TODO (5.14)
        // Add a space if there is another node after this
        if (current.next != null) {
            result.append(" ");
        }

        // TODO (5.15)
        // Move to the next node in the list
        current = current.next;
    }

    // TODO (5.16) Return the final formatted string
    return result.toString();
    }
}


// =======================================================
// TODO (6) ANAGRAM GROUPER CLASS (with Insertion Sort)
//
// Groups words into anagram groups using a LinkedHashMap
// This method groups words that are anagrams of each other by mapping each word to a canonical form (a sorted version of its characters).
// Words that share the same canonical form belong to the same group and are stored in a singly linked list.
// Key Idea: Two words are anagrams if they have the same characters in the same frequency.
//
// Approach:
// ---------------
// (1) Sort the characters of each word to get a canonical form.
// (2) Use a LinkedHashMap<String, SinglyLinkedList>:
//     (2.1) The key is the canonical form (e.g., "acr" for "arc" and "car").
//     (2.2) The value is a singly linked list containing all words that share this form.
// (3) Words are inserted into their respective groups in sorted order.
//
// =======================================================
class AnagramGrouper {

    public static Map<String, SinglyLinkedList> groupAnagrams(String[] words) {

        // TODO (6.1)
        // Store anagram groups while preserving insertion order
        Map<String, SinglyLinkedList> map = new LinkedHashMap<>();

        // TODO (6.2)
        // Iterate through each word in the words array and processing them to group anagrams together.
        for (int i = 0; i < words.length; i++) {

            // TODO (6.3)
            // Extract the current word from the words array
            String word = words[i];


            // TODO (6.4)
            // Call computeCanonicalForm() to get sorted-character form of the word (anagram key)
            String canonicalForm = computeCanonicalForm(word);

            // Debugging: Print computed canonical form
            System.out.println("*********************************************");
            System.out.println(" ∴ Canonical form of '" + word + "' is '" + canonicalForm + "'");

            // Create a new linked list if this key does not exist
            map.putIfAbsent(canonicalForm, new SinglyLinkedList());

            // Add the word into the appropriate linked list (maintaining order)
            map.get(canonicalForm).addSorted(word);

            // Debugging: Print updated anagram group
            System.out.println(" + Adding '" + word + "' to group: " + map.get(canonicalForm));
        }

        // Return the map of anagram groups
        return map;
    }

    // ===================================================================================================
    // METHOD computeCanonicalForm()
    //
    // This method computes the canonical form of a word by sorting its characters using insertion sort.
    // It helps group anagrams by ensuring words with the same letters get the same representation.
    // Example:
    //      (1) Processing ----------------------------> "stop"
    //      (2) Convert "stop" into a character array -> ['s', 't', 'o', 'p']
    //      (3) Sort using insertion sort -------------> ['o', 'p', 's', 't']
    //      (4) Convert back to string ----------------> "opst"
    // ===================================================================================================

    private static String computeCanonicalForm(String word) {

        // TODO (6.5)
        // Convert word into a character array
        char[] charArray = word.toCharArray();

        // TODO (6.6)
        // Sort characters in-place calling insertion sort
        insertionSort(charArray);

        // TODO (6.7)
        // Return the converted sorted character array back to a string
        return new String(charArray);
    }

    // =================================
    // METHOD insertionSort()
    // Implement insertion sort for sorting characters in a word
    // =================================

    private static void insertionSort(char[] arr) {

        // Debugging: Initial array state
        System.out.println();
        System.out.println("=============================================");
        System.out.println(" ► Starting Insertion Sort on: " + Arrays.toString(arr));

        // TODO (6.8)
        // Iterate over the array starting from index 1.
        for (int i = 1; i < arr.length; i++) {

            // TODO (6.9)
            // Store the current element (`key`) to be inserted into the sorted section.
            char key = arr[i];

            // TODO (6.10)
            // Initialize `j` to track the last element in the sorted portion of the array.
            int j = i - 1;

            // Debugging: Show current key being inserted
            System.out.println("-----------------------------");
            System.out.println(" ↳  Inserting '" + key + "' into sorted portion: " + Arrays.toString(Arrays.copyOfRange(arr, 0, i)));

            // TODO (6.11)
            // Iterate backwards through the sorted portion of the array:
            // Compare `key` with each element in the sorted section,
            // Shift elements that are greater than `key` to the right.
            while (j >= 0 && arr[j] > key) {

                // TODO (6.12)
                // Shift `arr[j]` one position to the right to create space for `key`.
                arr[j + 1] = arr[j];

                // TODO (6.13)
                // Move `j` one step left to continue shifting process.
                j--;

                // Debugging: Show shifting process
                System.out.println(" ⟲  Shifting " + Arrays.toString(arr));
            }

            // TODO (6.14) Place `key` at its correct position after all shifts.

            arr[j + 1] = key;

            // Debugging: Show array state after inserting key
            System.out.println(" ✔  After inserting '" + key + "': " + Arrays.toString(arr));
        }
    }

}
