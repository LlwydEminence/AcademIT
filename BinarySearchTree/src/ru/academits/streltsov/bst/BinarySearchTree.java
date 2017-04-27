package ru.academits.streltsov.bst;

import java.util.Deque;
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
            int sgn = calculateSgn(data, p.getData());
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

    private int calculateSgn(T data, T nodeData) {
        if (data != null) {
            if (nodeData != null) {
                //noinspection unchecked
                return data.compareTo(nodeData);
            } else {
                return 1;
            }
        } else {
            if (nodeData != null) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public void add(T data) {
        Node<T> currentNode = root;
        Node<T> parentNode = null;

        while (currentNode != null) {
            int sgn = calculateSgn(data, currentNode.getData());
            parentNode = currentNode;

            if (sgn <= 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }

        }

        Node<T> p = new Node<>(data);
        if (parentNode == null) {
            root = p;
        } else {
            int sgn = calculateSgn(data, parentNode.getData());
            if (sgn <= 0) {
                parentNode.setLeft(p);
            } else {
                parentNode.setRight(p);
            }
        }
    }

    private static void printNode(Node root) {
        System.out.print(root.getData() + " ");
    }

    public void recursivePrefixTraverse() {
        recursivePrefixTraverse(root);
    }

    public void prefixTraverse() {
        if (root == null) {
            return;
        }

        Node<T> currentNode = root;
        Deque<Node<T>> stack = new LinkedList<>();

        while (!stack.isEmpty() || currentNode != null) {
            if (currentNode != null) {
                printNode(currentNode);

                Node<T> rightChild = currentNode.getRight();
                if (rightChild != null) {
                    stack.addLast(rightChild);
                }
                currentNode = currentNode.getLeft();
            } else {
                currentNode = stack.removeLast();
            }
        }
    }


    private void recursivePrefixTraverse(Node<T> root) {
        if (root != null) {
            printNode(root);
            recursivePrefixTraverse(root.getLeft());
            recursivePrefixTraverse(root.getRight());
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
        Node<T> currentNode = root;
        Node<T> parentNode = null;

        while (currentNode != null) {
            //noinspection unchecked
            int sgn = data.compareTo(currentNode.getData());

            if (sgn == 0) {
                break;
            } else {
                parentNode = currentNode;
                if (sgn < 0) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode = currentNode.getRight();
                }
            }
        }

        if (currentNode == null) {
            return;
        }

        if (currentNode.getRight() == null) {
            if (parentNode == null) {
                root = currentNode.getLeft();
            } else {
                if (currentNode == parentNode.getLeft()) {
                    parentNode.setLeft(currentNode.getLeft());
                } else {
                    parentNode.setRight(currentNode.getLeft());
                }
            }
        } else {
            Node<T> rightSubtreeMin = currentNode.getRight();
            parentNode = null;

            while (rightSubtreeMin.getLeft() != null) {
                parentNode = rightSubtreeMin;
                rightSubtreeMin = rightSubtreeMin.getLeft();
            }

            if (parentNode == null) {
                currentNode.setRight(rightSubtreeMin.getLeft());
            } else {
                parentNode.setLeft(rightSubtreeMin.getLeft());
            }

            currentNode.setData(rightSubtreeMin.getData());
        }
    }
}
