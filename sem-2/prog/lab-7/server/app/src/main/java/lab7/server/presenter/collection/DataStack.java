package lab7.server.presenter.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.LocalDateTime;

import lab7.serializedobjects.dataclasses.EntityWithId;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.exceptions.ValidationFailedException;


/**
 * Коллекция данных в виде стека
 */
public class DataStack<E extends EntityWithId> extends Stack<E> implements DataCollection {

    private LocalDateTime modificationDate;
    private LocalDateTime initDate;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();

    public DataStack() {
        setInitDate();
        updateModificationDate();
    }
    
    /**
     * 
     * @param data Сырой массив данных
     */
    public DataStack(E[] data) {
        this();
        if (data != null)
            addAll(Arrays.asList(data));
    }

    @Override
    public LocalDateTime getModificationDate() {
        readLock.lock();
        try {
            return modificationDate;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public LocalDateTime getInitDate() {
        readLock.lock();
        try {
            return initDate;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String getTypeName() {
        readLock.lock();
        try {
            return Stack.class.getCanonicalName();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getSize() {
        readLock.lock();
        try {
            return size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int generateUniqueId() {
        for (int id = 1; id < Integer.MAX_VALUE; id++) {
            try {
                SpaceMarine.checkId(id);
            } catch (ValidationFailedException e) {
                continue;
            }
            if (!getAllIds().contains(id))
                return id;
        }
        throw new IndexOutOfBoundsException("Не удалось сгенерировать id в диапазонах типа int");
    }

    @Override
    public List<Integer> getAllIds() {
        readLock.lock();
        try {
            return this.stream()
                        .map(e -> e.getId())
                        .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    private void updateModificationDate() {
        this.modificationDate = LocalDateTime.now();
    }

    private void setInitDate() {
        writeLock.lock();
        try {
            this.initDate = LocalDateTime.now();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public E push(E e) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.push(e);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized boolean add(E e) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.add(e);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void add(int index, E element) {
        writeLock.lock();
        try {
            updateModificationDate();
            super.add(index, element);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized E pop() {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.pop();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.addAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized void addElement(E obj) {
        writeLock.lock();
        try {
            updateModificationDate();
            super.addElement(obj);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            updateModificationDate();
            super.clear();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized void insertElementAt(E obj, int index) {
        writeLock.lock();
        try {
            updateModificationDate();
            super.insertElementAt(obj, index);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.remove(o);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized E remove(int index) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.remove(index);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.removeAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized void removeAllElements() {
        writeLock.lock();
        try {
            updateModificationDate();
            super.removeAllElements();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized boolean removeElement(Object obj) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.removeElement(obj);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized void removeElementAt(int index) {
        writeLock.lock();
        try {
            updateModificationDate();
            super.removeElementAt(index);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.removeIf(filter);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    protected synchronized void removeRange(int fromIndex, int toIndex) {
        writeLock.lock();
        try {
            updateModificationDate();
            super.removeRange(fromIndex, toIndex);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized void replaceAll(UnaryOperator<E> operator) {
        writeLock.lock();
        try {
            updateModificationDate();
            super.replaceAll(operator);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        writeLock.lock();
        try {
            updateModificationDate();
            return super.retainAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized void trimToSize() {
        writeLock.lock();
        try {
            updateModificationDate();
            super.trimToSize();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public synchronized E peek() {
        readLock.lock();
        try {
            return super.peek();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized int search(Object o) {
        readLock.lock();
        try {
            return super.search(o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized E elementAt(int index) {
        readLock.lock();
        try {
            return super.elementAt(index);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized E firstElement() {
        readLock.lock();
        try {
            return super.firstElement();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized E get(int index) {
        readLock.lock();
        try {
            return super.get(index);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int indexOf(Object o) {
        readLock.lock();
        try {
            return super.indexOf(o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized int indexOf(Object arg0, int arg1) {
        readLock.lock();
        try {
            return super.indexOf(arg0, arg1);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized boolean isEmpty() {
        readLock.lock();
        try {
            return super.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized E lastElement() {
        readLock.lock();
        try {
            return super.lastElement();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized int lastIndexOf(Object arg0, int arg1) {
        readLock.lock();
        try {
            return super.lastIndexOf(arg0, arg1);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public synchronized int lastIndexOf(Object o) {
        readLock.lock();
        try {
            return super.lastIndexOf(o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<E> stream() {
        readLock.lock();
        try {
            return super.stream();
        } finally {
            readLock.unlock();
        }
    }

}
