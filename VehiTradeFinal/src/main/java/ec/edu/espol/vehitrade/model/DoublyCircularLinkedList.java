/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.vehitrade.model;

/**
 *
 * @author nicol
 */
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author laboratorio
 */
public class DoublyCircularLinkedList<E> implements List<E>, Serializable{

    private DoublyNodeList<E> last;
    
    public DoublyCircularLinkedList(){
        this.last = null;
    }

    public DoublyNodeList<E> getLast() {
        return last;
    }
    
    
    
    @Override
    public boolean addFirst(E e) {
        if (e != null){
            DoublyNodeList<E> newNode = new DoublyNodeList<>(e);
            if(last!=null){
                newNode.setNext(last.getNext());
                newNode.setPrevious(last);
                last.getNext().setPrevious(newNode);
                last.setNext(newNode);
                return true;
            }
            last = newNode;
            return true;
        }
        return false;
    }

    @Override
    public boolean addLast(E e) {
        if (e != null) {
            DoublyNodeList<E> newNode = new DoublyNodeList<>(e);
            if (last == null) {
                last = newNode;
                last.setNext(last);
                last.setPrevious(last);
            } else {
                DoublyNodeList<E> first = last.getNext();
                newNode.setNext(first);
                newNode.setPrevious(last);
                first.setPrevious(newNode);
                last.setNext(newNode);
                last = newNode;
            }
            return true;
        }
        return false;
    }

    @Override
    public E removeFirst() {
        if(size()==1){
            E content = last.getContent();
            last=null;
            return content;
        } else if(isEmpty()){
            return null;
        }
        DoublyNodeList<E> n = last.getNext();
        E content = n.getContent();
        n.getNext().setPrevious(last);
        last.setNext(n.getNext());
        return content;
    }

    @Override
    public E removeLast() {
        if(size()==1){
            E content = last.getContent();
            last=null;
            return content;
        }else if(size()==0){
            return null;
        }
        DoublyNodeList<E> n = last;
        n.getPrevious().setNext(n.getNext());
        n.getNext().setPrevious(n.getPrevious());
        
        E content = last.getContent();
        this.setLast(n.getPrevious());
        return content;
    }

    @Override
    public int size() {
        if(last==null){
            return 0;
        } else{
            int counter = 0;
            for(DoublyNodeList<E> n = last.getNext();n!=last;n=n.getNext()){
                counter++;
            }
            counter++;
            return counter;
        }
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   @Override
public Iterator<E> iterator() {
    return new Iterator<E>() {
        private DoublyNodeList<E> cursor = last != null ? last.getNext() : null;
        private DoublyNodeList<E> lastReturned = null;
        private boolean start = last != null;

        @Override
        public boolean hasNext() {
            return cursor != null && (cursor != last.getNext() || start);
        }

        @Override
        public E next() {
            lastReturned = cursor;
            E e = cursor.getContent();
            cursor = cursor.getNext();
            start = false;
            return e;
        }

        @Override
        public void remove() {
            if (lastReturned == null) throw new IllegalStateException();

            DoublyNodeList<E> nextNode = lastReturned.getNext();
            DoublyNodeList<E> prevNode = lastReturned.getPrevious();

            prevNode.setNext(nextNode);
            nextNode.setPrevious(prevNode);

            if (lastReturned == last) {
                last = prevNode;
            }

            lastReturned = null;
        }
    };
}
  
    
    public void setLast(DoublyNodeList<E> node){
        this.last= node;
    }

    @Override
    public Integer find(Comparator cmp, E elemento) {
        Iterator<E> it= this.iterator();
        int count = 0;
        while(it.hasNext()){  
            E n=it.next();
            if (cmp.compare(n, elemento) == 0)
                return count;
            count +=1;
        }
        return null;
    }

    @Override
    public List<E> findAll(Comparator cmp, E elemento) {
        DoublyCircularLinkedList<E> nueva = new DoublyCircularLinkedList<>();
        Iterator<E> it= this.iterator();
        while(it.hasNext()){  
            E n=it.next();
            if (cmp.compare(n, elemento) == 0)
                nueva.addLast(n);
        }
        return nueva;
    }
    
    public void sort(Comparator<E> cmp) {
        if (size() > 1) {
            boolean sorted;
            do {
                sorted = true;
                DoublyNodeList<E> current = last.getNext();
                do {
                    DoublyNodeList<E> next = current.getNext();
                    if (cmp.compare(current.getContent(), next.getContent()) > 0) {
                        E temp = current.getContent();
                        current.setContent(next.getContent());
                        next.setContent(temp);
                        sorted = false;
                    }
                    current = next;
                } while (current != last);
            } while (!sorted);
        }
    }
}