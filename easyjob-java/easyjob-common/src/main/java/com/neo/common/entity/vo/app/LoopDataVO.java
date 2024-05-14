package com.neo.common.entity.vo.app;

public class LoopDataVO<T> {
    private T pre;
    private T current;
    private T next;

    public T getPre() {
        return pre;
    }

    public void setPre(T pre) {
        this.pre = pre;
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }

    public T getNext() {
        return next;
    }

    public void setNext(T next) {
        this.next = next;
    }
}
