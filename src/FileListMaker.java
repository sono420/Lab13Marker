 import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class FileListMaker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        char choice;
        do {
            displayList();
            displayMenu();
            choice = getUserChoice();
            switch (choice) {
                case 'A':
                    addItemToList();
                    break;
                case 'D':
                    deleteItemFromList();
                    break;
                case 'V':
                    viewList();
                    break;
                case 'O':
                    loadList();
                    break;
                case 'S':
                    saveList();
                    break;
                case 'C':
                    clearList();
                    break;
                case 'Q':
                    confirmQuit();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 'Q');
    }

    private static void displayList() {
        System.out.println("\nCurrent List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
    }

    private static char getUserChoice() {
        System.out.print("\nEnter your choice: ");
        return Character.toUpperCase(scanner.next().charAt(0));
    }

    private static void addItemToList() {
        String item = getInputString("Enter the item to add to the list: ");
        list.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItemFromList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. No items to delete.");
            return;
        }

        displayList();
        int indexToDelete = getValidIndex("Enter the number of the item to delete: ");
        if (indexToDelete != -1) {
            list.remove(indexToDelete - 1);
            needsToBeSaved = true;
            System.out.println("Item deleted successfully.");
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            displayList();
        }
    }

    private static void loadList() {
        if (needsToBeSaved) {
            System.out.println("Warning: There are unsaved changes. Save the current list before loading a new one.");
            return;
        }
        System.out.print("Enter the filename to load: ");
        String filename = scanner.next();
        try {
            File file = new File(filename + ".txt");
            Scanner fileScanner = new Scanner(file);
            list.clear();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                list.add(line);
            }
            System.out.println("List loaded successfully.");
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error: File not found.");
        }
    }

    private static void saveList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to save.");
            return;
        }
        if (!needsToBeSaved) {
            System.out.println("There are no changes to save.");
            return;
        }
        System.out.print("Enter the filename to save: ");
        String filename = scanner.next();
        try {
            FileWriter writer = new FileWriter(filename + ".txt");
            for (String item : list) {
                writer.write(item + "\n");
            }
            writer.close();
            System.out.println("List saved successfully.");
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error: Unable to save the list.");
        }
    }

    private static void clearList() {
        list.clear();
        needsToBeSaved = false;
        System.out.println("List cleared.");
    }

    private static void confirmQuit() {
        if (needsToBeSaved) {
            System.out.println("Warning: There are unsaved changes. Save the list before quitting.");
            char choice;
            do {
                System.out.print("Do you want to save the list before quitting? (Y/N): ");
                choice = Character.toUpperCase(scanner.next().charAt(0));
            } while (choice != 'Y' && choice != 'N');
            if (choice == 'Y') {
                saveList();
            }
        }
        System.out.println("Exiting the program. Goodbye!");
    }

    private static String getInputString(String message) {
        System.out.print(message);
        return scanner.next();
    }

    private static int getValidIndex(String message) {
        while (true) {
            System.out.print(message);
            int index = scanner.nextInt();
            if (index >= 1 && index <= list.size()) {
                return index;
            } else {
                System.out.println("Invalid index. Please try again.");
            }
        }
    }
}
