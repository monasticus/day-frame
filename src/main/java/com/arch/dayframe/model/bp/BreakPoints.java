package com.arch.dayframe.model.bp;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class BreakPoints {

    private final List<BreakPoint> breakPoints;
    private ListIterator<BreakPoint> bpIterator;

    public BreakPoints(String path) throws BreakPointException, IOException {
        this.breakPoints = BreakPointFactory.fromPath(path).stream().filter(BreakPoint::isNotPast).collect(Collectors.toList());
        this.bpIterator = this.breakPoints.listIterator();
    }

    public int getSize() {
        return breakPoints.size();
    }

    public List<BreakPoint> getBreakPointsList() {
        return new LinkedList<>(this.breakPoints);
    }

    public BreakPoint moveForward() {
        return bpIterator.hasNext() ? bpIterator.next() : null;
    }

    public BreakPoint moveBack() {
        return bpIterator.hasPrevious() ? bpIterator.previous() : null;
    }

    public BreakPoint getNext() {
        return bpIterator.hasNext() ? (BreakPoint) breakPoints.get(bpIterator.nextIndex()).clone() : null;
    }

    public BreakPoint getPrevious() {
        return bpIterator.hasPrevious() ? (BreakPoint) breakPoints.get(bpIterator.previousIndex()).clone() : null;
    }

    public List<BreakPoint> getFutureWithoutNext() {
        return doesNotHaveNextOrNextIsLast() ? new LinkedList<>() : getBreakPointsList().subList(bpIterator.nextIndex()+1, getSize());
    }

    private boolean doesNotHaveNextOrNextIsLast() {
        return !bpIterator.hasNext() || bpIterator.nextIndex() == getSize()-1;
    }
}
