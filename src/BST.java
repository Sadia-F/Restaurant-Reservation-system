package RestaurantReservationSystem;

public class BST {
    private class Node {
        String phoneNumber;
        Node left, right;

        Node(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            left = right = null;
        }
    }

    private Node root;

    // Insert a phone number
    public void insert(String phoneNumber) {
        root = insertRec(root, phoneNumber);
        System.out.println("Phone number " + phoneNumber + " added to rewards database.");
    }

    private Node insertRec(Node root, String phoneNumber) {
        if (root == null) {
            return new Node(phoneNumber);
        }

        int comparison = phoneNumber.compareTo(root.phoneNumber);
        if (comparison < 0) {
            root.left = insertRec(root.left, phoneNumber);
        } else if (comparison > 0) {
            root.right = insertRec(root.right, phoneNumber);
        }
        return root;
    }

    // Search for a phone number
    public boolean search(String phoneNumber) {
        return searchRec(root, phoneNumber);
    }

    private boolean searchRec(Node root, String phoneNumber) {
        if (root == null) {
            return false;
        }

        int comparison = phoneNumber.compareTo(root.phoneNumber);
        if (comparison == 0) {
            return true;
        }
        return comparison < 0 ? searchRec(root.left, phoneNumber) : searchRec(root.right, phoneNumber);
    }

    // Delete a phone number
    public void delete(String phoneNumber) {
        if (search(phoneNumber)) {
            root = deleteRec(root, phoneNumber);
            System.out.println("Phone number " + phoneNumber + " removed from rewards database.");
        } else {
            System.out.println("Phone number not found in rewards database.");
        }
    }

    private Node deleteRec(Node root, String phoneNumber) {
        if (root == null) {
            return null;
        }

        int comparison = phoneNumber.compareTo(root.phoneNumber);
        if (comparison < 0) {
            root.left = deleteRec(root.left, phoneNumber);
        } else if (comparison > 0) {
            root.right = deleteRec(root.right, phoneNumber);
        } else {
            // Node found - handle deletion
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children: get inorder successor
            root.phoneNumber = findMin(root.right);
            root.right = deleteRec(root.right, root.phoneNumber);
        }
        return root;
    }

    private String findMin(Node root) {
        String min = root.phoneNumber;
        while (root.left != null) {
            min = root.left.phoneNumber;
            root = root.left;
        }
        return min;
    }

    // Display all phone numbers (in-order traversal)
    public void displayAllPhoneNumbers() {
        System.out.print("Rewards Members: ");
        inOrderTraversal(root);
        System.out.println();
    }

    private void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.phoneNumber + " ");
            inOrderTraversal(root.right);
        }
    }
}