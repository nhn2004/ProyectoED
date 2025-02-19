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
 * Profesora: Adriana Collaguazo
 * Lista doblemente enlazada
 * @param <E>
 */
public class DoublyLinkedList<E> implements List<E>, Serializable {
    
    private DoublyNodeList<E> header;
    private DoublyNodeList<E> last;
    
    public DoublyLinkedList(){
        this.header = null;
        this.last = null;
    }

    public boolean isEmpty(){
        return header == null && last == null;
    }

    public int size() {
        int count = 0;
        DoublyNodeList<E> current = header;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public DoublyNodeList<E> getHeader() {
        return header;
    }

    public void setHeader(DoublyNodeList<E> header) {
        this.header = header;
    }

    public DoublyNodeList<E> getLast() {
        return last;
    }

    public void setLast(DoublyNodeList<E> last) {
        this.last = last;
    }
    
    private void recorrerHaciaAtras(){
        DoublyNodeList<E> n;
        for (n = last ; n != header; n = n.getPrevious()){
        }
    }
    
    public boolean addFirst(E e)
    {
        if (e != null) {
            DoublyNodeList<E> newNode = new DoublyNodeList<>(e);
            if (isEmpty()) {
                header = last = newNode;
            } else {
                newNode.setNext(header);
                header.setPrevious(newNode);
                this.setHeader(newNode);
            }
            return true;
        }
        return false;
    }
    
    public boolean addLast(E e)
    {
        if (e != null) {
            DoublyNodeList<E> newNode = new DoublyNodeList<>(e);
            if (isEmpty()) {
                header = last = newNode;
            } else {
                newNode.setPrevious(last);
                last.setNext(newNode);
                this.setLast(newNode);
            }
            return true;
        }
        return false;
    }
    
    public boolean addAt(E e, int pos)
    {
        if (e != null && pos >= 0 && pos < this.size()) {
            DoublyNodeList<E> newNode = new DoublyNodeList<>(e);
            DoublyNodeList<E> p = header;
            for(int i = 0; i < pos; i++){
                p = p.getNext();
            }
            newNode.setNext(p.getNext());
            p.setNext(newNode);
            newNode.setPrevious(p);
            newNode.getNext().setPrevious(newNode);
            return true;
        }
        return false;
    }
    
    public E removeElement(int pos){
        if (pos < 0 || pos >= this.size()) {
            return null;
        }
        DoublyNodeList<E> p = header;
        for(int i = 0; i < pos; i++){
            p = p.getNext();
        }
        if (p == header) {
            return this.removeFirst();
        } else if (p == last) {
            return this.removeLast();
        } else {
            p.getPrevious().setNext(p.getNext());
            p.getNext().setPrevious(p.getPrevious());
            p.setNext(null);
            p.setPrevious(null);
            return p.getContent();
        }
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        DoublyNodeList<E> n = header;
        if (header == last) {
            header = last = null;
        } else {
            header = header.getNext();
            header.setPrevious(null);
        }
        return n.getContent();
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        DoublyNodeList<E> n = last;
        if (header == last) {
            header = last = null;
        } else {
            last = last.getPrevious();
            last.setNext(null);
        }
        return n.getContent();
    }

    @Override
    public void clear() {
        header = last = null;
    }

    @Override
    public boolean add(int index, E element) {
        return addAt(element, index);
    }

    @Override
    public E remove(int index) {
        return removeElement(index);
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= this.size()) {
            return null;
        }
        DoublyNodeList<E> p = header;
        for (int i = 0; i < index; i++) {
            p = p.getNext();
        }
        return p.getContent();
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= this.size()) {
            return null;
        }
        DoublyNodeList<E> p = header;
        for (int i = 0; i < index; i++) {
            p = p.getNext();
        }
        E oldContent = p.getContent();
        p.setContent(element);
        return oldContent;
    }

    @Override
    public Iterator<E> iterator(){
        return new Iterator<E>() {
            DoublyNodeList<E> cursor = header;
            @Override
            public boolean hasNext() {
                return cursor != null;
            }
            @Override
            public E next() {
                E e = cursor.getContent();
                cursor = cursor.getNext();
                return e;
            }
        };
    } 
    
    @Override
    public String toString(){
        StringBuilder fin = new StringBuilder();
        DoublyNodeList<E> n = header;
        while (n != null) {
            fin.append(n.getContent()).append(" ");
            n = n.getNext();
        }
        return fin.toString();
    }

    @Override
    public Integer find(Comparator cmp, E elemento) {
        Iterator<E> it = this.iterator();
        int count = 0;
        while (it.hasNext()) {  
            E n = it.next();
            if (cmp.compare(n, elemento) == 0) {
                return count;
            }
            count += 1;
        }
        return null;
    }

    @Override
    public List<E> findAll(Comparator cmp, E elemento) {
        DoublyLinkedList<E> nueva = new DoublyLinkedList<>();
        Iterator<E> it = this.iterator();
        while (it.hasNext()) {  
            E n = it.next();
            if (cmp.compare(n, elemento) == 0) {
                nueva.addLast(n);
            }
        }
        return nueva;
    }
}

