package prj5;
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal,
// nor will I accept the actions of those who do.
// Arshia Saeidifar arshias@vt.edu
// Prince princeg@vt.edu
// Sakdipong Rodphong Sakdipong@vt.edu
// Zaybish Tariq Zaybish@vt.edu

import java.util.Comparator;
//

/**
 * This is a basic implementation of a linked list
 *
 * @author Prince
 * @version 4/28/2025
 * @author Sakdipong Rodphong
 * @version 4/28/2025
 * @param <E>
 *            This is the type of object that this class will store
 */

public class SinglyLinkedList<E>
    implements LList<E>
{

    /**
     * This class represents a node in a singly linked list, storing data and a
     * reference to the next node in the sequence.
     * 
     * @param <D>
     */

    public static class Node<D>
    {

        private D data;

        private Node<D> next;

        /**
         * Creates a new node with the given data
         *
         * @param d
         *            the data to put inside the node
         */
        public Node(D d)
        {
            this.data = d;
            this.next = null;
        }


        /**
         * Sets the node after this node
         *
         * @param n
         *            the node after this one
         */
        public void setNext(Node<D> n)
        {
            next = n;
        }


        /**
         * Gets next node
         *
         * @return next node
         */
        public Node<D> next()
        {
            return next;
        }


        /**
         * Gets the data in the node
         *
         * @return data in the node
         */
        public D getData()
        {
            return data;
        }
    }

    private Node<E> head;

    // the size of the linked list
    private int size;

    /**
     * Creates a new LinkedList object
     */
    public SinglyLinkedList()
    {
        head = null;
        size = 0;

    }


    /**
     * Gets the number of elements in the list
     *
     * @return the number of elements in the list
     */
    @Override
    public int size()
    {
        return size;
    }


    /**
     * Adds the object to the position in the list obj cannot be null
     * 
     * @param index
     *            where to add the object
     * @param obj
     *            the object to add
     * @throws IndexOutOfBoundsException
     *             if the index is less than zero or greater than size
     * @throws IllegalArgumentException
     *             if obj is null
     */
    @Override
    public void add(int index, E obj)
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("Object is null");
        }

        if (index < 0)
        {
            throw new IndexOutOfBoundsException("out of bounds");
        }

        if (index > size)
        {
            throw new IndexOutOfBoundsException("out of bounds");
        }

        if (index == 0)
        {
            Node<E> newNode = new Node<>(obj);
            newNode.setNext(head);
            head = newNode;
        }
        else
        {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++)
            {
                current = current.next();
            }

            Node<E> newNode = new Node<>(obj);
            newNode.setNext(current.next());
            current.setNext(newNode);
        }

        size++;
    }


    /**
     * Adds the object to the end of the list. -precondition obj cannot be null
     * 
     * @param obj
     *            the object to add
     * @throws IllegalArgumentException
     *             if obj is null
     */
    @Override
    public void add(E obj)
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("Object is null");
        }

        Node<E> current = head;

        if (isEmpty())
        {
            head = new Node<E>(obj);
        }

        else
        {
            while (current.next != null)
            {
                current = current.next;
            }
            current.setNext(new Node<E>(obj));
        }
        size++;
    }


    /**
     * Checks if the array is empty
     *
     * @return true if the array is empty
     */
    @Override
    public boolean isEmpty()
    {
        return (size == 0);
    }


    /**
     * Removes the first instance of the given object from the list
     *
     * @param obj
     *            the object to remove
     * @return true if successful
     */
    @Override
    public boolean remove(E obj)
    {
        if (head == null)
        {
            return false;
        }

        if (obj.equals(head.getData()))
        {
            head = head.next();
            size--;
            return true;
        }

        Node<E> current = head;
        while (current.next() != null)
        {
            if (obj.equals(current.next().getData()))
            {
                current.setNext(current.next().next());
                size--;
                return true;
            }
            current = current.next();
        }

        return false;
    }


    /**
     * Removes the object at the given position in the parameter
     *
     * @param index
     *            the position of the object
     * @return true if the removal was successful
     * @throws IndexOutOfBoundsException
     *             if there is not an element at the index
     */
    @Override
    public boolean remove(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        if (index == 0)
        {
            head = head.next();
            size--;
            return true;
        }

        Node<E> current = head;
        for (int i = 0; i < index - 1; i++)
        {
            current = current.next();
        }

        current.setNext(current.next().next());
        size--;
        return true;
    }


    /**
     * Gets the object at the given position in the parameter
     *
     * @param index
     *            where the object is located
     * @return The object at the given position
     * @throws IndexOutOfBoundsException
     *             if no node at the given index
     */
    @Override
    public E get(int index)
    {
        Node<E> current = head;
        int currentIndex = 0;
        E data = null;
        while (current != null)
        {
            if (currentIndex == index)
            {
                data = current.data;
            }
            currentIndex++;
            current = current.next;
        }

        if (data == null)
        {
            throw new IndexOutOfBoundsException("Index exceeds the size.");
        }
        return data;
    }


    /**
     * Checks if the list contains the given object in the parameter
     *
     * @param obj
     *            the object to check for
     * @return true if it contains the object
     */
    @Override
    public boolean contains(E obj)
    {
        Node<E> current = head;
        while (current != null)
        {
            if (obj.equals(current.data))
            {
                return true;
            }
            current = current.next;
        }

        return false;
    }


    /**
     * Removes all the elements from the list
     */
    @Override
    public void clear()
    {
        if (head != null)
        {
            head.setNext(null);
            head = null;
        }
        size = 0;

    }


    /**
     * Gets the last time the given object is in the list
     *
     * @param obj
     *            the object to look for
     * @return the last position of it. -1 If it is not in the list
     */
    @Override
    public int lastIndexOf(E obj)
    {
        int lastIndex = -1;
        Node<E> current = head;
        int currentIndex = 0;
        while (current != null)
        {
            if (obj.equals(current.data))
            {
                lastIndex = currentIndex;
            }
            currentIndex++;
            current = current.next;

        }
        return lastIndex;
    }


    /**
     * Returns a string representation of the list If a list contains A, B, and
     * C, the following should be returned "{A, B, C}" (Without the quotations)
     *
     * @return the string representing the list
     */
    @Override
    public String toString()
    {
        String result = "{";

        Node<E> current = head;
        while (current != null)
        {
            result += "" + current.data;
            current = current.next;
            if (current != null)
            {
                result += ", ";
            }
        }
        result += "}";
        return result;
    }


    /**
     * Returns an array representation of the list If a list contains A, B, and
     * C, the following should be returned {A, B, C}, If a list contains A, B,
     * C, and C the following should be returned {A, B, C, C}
     *
     * @return the array representing the list
     */
    public Object[] toArray()
    {

        Object[] array = new Object[this.size()];

        Node<E> current = head;
        int count = 0;
        while (current != null)
        {
            array[count] = current.getData();
            current = current.next;
            count++;
        }

        return array;
    }


    /**
     * Returns true if both lists have the exact same contents in the exact same
     * order
     *
     * @return a boolean of whether two lists have the same contents, item per
     *             item and in the same order
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (this.getClass() == obj.getClass())
        {
            @SuppressWarnings("unchecked")
            SinglyLinkedList<E> other = ((SinglyLinkedList<E>)obj);
            if (other.size() == this.size())
            {
                Node<E> current = head;
                Node<E> otherCurrent = other.head;
                while (current != null)
                {
                    if (!current.getData().equals(otherCurrent.getData()))
                    {
                        return false;
                    }
                    current = current.next();
                    otherCurrent = otherCurrent.next();
                }
                return true;
            }
        }

        return false;
    }


    /**
     * Sorts the list using insertion sort and the comparator.
     *
     * @param comparator
     *            a Comparator that defines element ordering
     */
    @SuppressWarnings("null")
    public void insertionSort(Comparator<E> comparator)
    {

        if (head == null || head.next == null)
        {
            return;
        }

        Node<E> sorted = null;

        while (head != null)
        {

            Node<E> current = head;
            head = head.next();
            current.setNext(null);

            boolean insertAtFront = sorted == null
                || comparator.compare(current.getData(), sorted.getData()) < 0;

            if (insertAtFront)
            {
                current.setNext(sorted);
                sorted = current;
            }
            else
            {
                Node<E> search = sorted;
                while (search.next() != null && comparator
                    .compare(current.getData(), search.next().getData()) >= 0)
                {
                    search = search.next();
                }
                current.setNext(search.next());
                search.setNext(current);
            }
        }

        head = sorted;
    }
}
