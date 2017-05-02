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
        System.out.println(root.getData());
    }

    public void recursivePrefixTraverse() {
        recursivePrefixTraverse(root);
    }

    public void prefixTraverse() {
        if (root == null) {
            return;
        }

        Deque<Node<T>> stack = new LinkedList<>();
        stack.addLast(root);

        while (!stack.isEmpty()) {
            Node<T> p = stack.removeLast();
            printNode(p);
            Node<T> right = p.getRight();
            Node<T> left = p.getLeft();

            if (right != null) {
                stack.addLast(right);
            }

            if (left != null) {
                stack.addLast(left);
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
        Node<T> p = root;
        Node<T> parent = null;

        while (p != null) {
            //noinspection unchecked
            int sgn = calculateSgn(data, p.getData());

            if (sgn == 0) {
                break;
            } else {
                parent = p;
                if (sgn < 0) {
                    p = p.getLeft();
                } else {
                    p = p.getRight();
                }
            }
        }

        if (p == null) {
            return;
        }

        Node<T> right = p.getRight();
        Node<T> left = p.getLeft();

        if (right == null && left == null) {
            if (parent == null) {
                root = null;
            } else if (p == parent.getLeft()) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if (right == null) {
            if (parent == null) {
                root = left;
            } else if (p == parent.getLeft()) {
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
        } else if (left == null) {
            if (parent == null) {
                root = right;
            } else if (p == parent.getLeft()) {
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
        } else {
            if (right.getLeft() == null) {
                p.setData(right.getData());
                p.setRight(right.getRight());
            } else {
                Node<T> rightLeftMin = right.getLeft();
                Node<T> parentRightLeftMin = right;

                while (true) {
                    if (rightLeftMin.getLeft() == null) {
                        p.setData(rightLeftMin.getData());
                        parentRightLeftMin.setLeft(null);
                        break;
                    } else {
                        parentRightLeftMin = rightLeftMin;
                        rightLeftMin = rightLeftMin.getLeft();
                    }
                }
            }
        }
    }
}
