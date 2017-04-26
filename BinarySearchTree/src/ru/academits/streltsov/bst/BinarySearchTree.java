package ru.academits.streltsov.bst;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable> {
    private Node<T> root;

    public BinarySearchTree() {
    }

    public BinarySearchTree(T data) {
        root = new Node<>(data);
    }

    public Node<T> find(T data) {
        Node<T> p = root;

        while (p != null) {
            //noinspection unchecked
            int sgn = data.compareTo(p.getData());
            if (sgn == 0) {
                return p;
            } else if (sgn < 0) {
                p = p.getLeft();
            } else {
                p = p.getRight();
            }
        }

        return null;
    }

    public void add(T data) {
        Node<T> currentNode = root;
        Node<T> parentNode = null;

        while (currentNode != null) {
            //noinspection unchecked
            int sgn = data.compareTo(currentNode.getData());

            if (sgn == 0) {
                return;
            } else {
                parentNode = currentNode;
                if (sgn < 0) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode = currentNode.getRight();
                }
            }
        }

        Node<T> p = new Node<>(data);
        if (parentNode == null) {
            root = p;
        } else {
            //noinspection unchecked
            if (data.compareTo(parentNode.getData()) < 0) {
                parentNode.setLeft(p);
            } else {
                parentNode.setRight(p);
            }
        }
    }

    private static void printNode(Node root){
        System.out.print(root.getData() +" ");
    }

    public void infixTraverse() {
        infixTraverse(root);
    }

    private void infixTraverse(Node<T> root) {
        if (root != null) {
            infixTraverse(root.getLeft());
            printNode(root);
            infixTraverse(root.getRight());
        }
    }

    public void breadthFirstSearch() {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> p = queue.remove();
            printNode(p);

            if (p.getLeft() != null) {
                queue.add(p.getLeft());
            }

            if (p.getRight() != null) {
                queue.add(p.getRight());
            }
        }
    }

    public void remove(T data) {
        Node<T> currentNote = root;
        Node<T> parentNote = null;

        while (currentNote != null) {
            //noinspection unchecked
            int sgn = data.compareTo(currentNote.getData());

            if (sgn == 0) {
                break;
            } else {
                parentNote = currentNote;
                if (sgn < 0) {
                    currentNote = currentNote.getLeft();
                } else {
                    currentNote = currentNote.getRight();
                }
            }
        }

        if (currentNote == null) {
            return;
        }

        if (currentNote.getRight() == null) {
            if (parentNote == null) {
                root = currentNote.getLeft();
            } else {
                if (currentNote == parentNote.getLeft()) {
                    parentNote.setLeft(currentNote.getLeft());
                } else {
                    parentNote.setRight(currentNote.getLeft());
                }
            }
        } else {
            Node<T> rightSubtreeMin = currentNote.getRight();
            parentNote = null;

            while (rightSubtreeMin.getLeft() != null) {
                parentNote = rightSubtreeMin;
                rightSubtreeMin = rightSubtreeMin.getLeft();
            }

            if (parentNote == null) {
                currentNote.setRight(rightSubtreeMin.getLeft());
            } else {
                parentNote.setLeft(rightSubtreeMin.getLeft());
            }

            currentNote.setData(rightSubtreeMin.getData());
        }
    }
}
