package lab6.server.Presenter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import lab6.serializedobjects.DataClasses.EntityWithId;
import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.Exceptions.ValidationFailedException;


/**
 * Коллекция данных в виде стека
 */
public class DataStack<E extends EntityWithId> extends Stack<E> implements IDataCollection {

    private LocalDateTime modificationDate;
    private LocalDateTime initDate;

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
        return modificationDate;
    }

    @Override
    public LocalDateTime getInitDate() {
        return initDate;
    }

    @Override
    public String getTypeName() {
        return Stack.class.getCanonicalName();
    }

    @Override
    public int getSize() {
        return size();
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
        return this.stream()
                    .map(e -> e.getId())
                    .collect(Collectors.toList());
    }

    private void updateModificationDate() {
        this.modificationDate = LocalDateTime.now();
    }

    private void setInitDate() {
        this.initDate = LocalDateTime.now();
    }


    @Override
    public E push(E e) {
        updateModificationDate();
        return super.push(e);
    }

    @Override
    public synchronized boolean add(E e) {
        updateModificationDate();
        return super.add(e);
    }

    @Override
    public void add(int index, E element) {
        updateModificationDate();
        super.add(index, element);
    }

    @Override
    public synchronized E pop() {
        updateModificationDate();
        return super.pop();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        updateModificationDate();
        return super.addAll(c);
    }

    @Override
    public synchronized void addElement(E obj) {
        updateModificationDate();
        super.addElement(obj);
    }

    @Override
    public void clear() {
        updateModificationDate();
        super.clear();
    }

    @Override
    public synchronized void insertElementAt(E obj, int index) {
        updateModificationDate();
        super.insertElementAt(obj, index);
    }

    @Override
    public boolean remove(Object o) {
        updateModificationDate();
        return super.remove(o);
    }

    @Override
    public synchronized E remove(int index) {
        updateModificationDate();
        return super.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        updateModificationDate();
        return super.removeAll(c);
    }

    @Override
    public synchronized void removeAllElements() {
        updateModificationDate();
        super.removeAllElements();
    }

    @Override
    public synchronized boolean removeElement(Object obj) {
        updateModificationDate();
        return super.removeElement(obj);
    }

    @Override
    public synchronized void removeElementAt(int index) {
        updateModificationDate();
        super.removeElementAt(index);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        updateModificationDate();
        return super.removeIf(filter);
    }

    @Override
    protected synchronized void removeRange(int fromIndex, int toIndex) {
        updateModificationDate();
        super.removeRange(fromIndex, toIndex);
    }

    @Override
    public synchronized void replaceAll(UnaryOperator<E> operator) {
        updateModificationDate();
        super.replaceAll(operator);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        updateModificationDate();
        return super.retainAll(c);
    }

    @Override
    public synchronized void trimToSize() {
        updateModificationDate();
        super.trimToSize();
    }
}
