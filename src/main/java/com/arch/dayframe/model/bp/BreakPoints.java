package com.arch.dayframe.model.bp;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class BreakPoints {

    private final List<BreakPoint> breakPoints;
    private ListIterator<BreakPoint> iterator;

    public BreakPoints(String path) throws BreakPointException, IOException {
        breakPoints = BreakPointFactory.fromPath(path).stream().filter(BreakPoint::isNotPast).collect(Collectors.toList());
        iterator = breakPoints.listIterator();
    }

    public int getSize() {
        return breakPoints.size();
    }

    public List<BreakPoint> getBreakPointsList() {
        return breakPoints.stream().map(BreakPoint::clone).sorted().collect(Collectors.toCollection(LinkedList::new));
    }

    public BreakPoint moveForward() {
        return iterator.hasNext() ? iterator.next() : null;
    }

    public BreakPoint moveBackward() {
        return iterator.hasPrevious() ? iterator.previous() : null;
    }

    public BreakPoint getNext() {
        return iterator.hasNext() ? breakPoints.get(iterator.nextIndex()).clone() : null;
    }

    public BreakPoint getPrevious() {
        return iterator.hasPrevious() ? breakPoints.get(iterator.previousIndex()).clone() : null;
    }

    public List<BreakPoint> getFutureWithoutNext() {
        return doesNotHaveNextOrNextIsLast() ? new LinkedList<>() : getBreakPointsList().subList(iterator.nextIndex() + 1, getSize());
    }

    public void postponeRecent(int postponementMinutes) throws BreakPointException {
        BreakPoint breakPoint = getPrevious();
        if (breakPoint != null) {
            BreakPoint originBreakPoint = breakPoints.stream().filter(breakPoint::equals).findFirst().get();
            originBreakPoint.postpone(postponementMinutes);
            removeDuplicateOf(originBreakPoint);

            refresh();
        }
    }

    private boolean doesNotHaveNextOrNextIsLast() {
        return !iterator.hasNext() || iterator.nextIndex() == getSize()-1;
    }

    private void removeDuplicateOf(BreakPoint breakPoint) {
        breakPoints.stream().filter(bp -> bp.hasTheSameTime(breakPoint) && bp != breakPoint)
                .findAny().map(breakPoints::remove);
    }

    private void refresh() {
        Collections.sort(breakPoints);
        iterator = breakPoints.listIterator();
        while (iterator.hasNext()) {
            BreakPoint nextBreakPoint = getNext();
            if (nextBreakPoint.isNotPast())
                break;
            else
                moveForward();
        }
    }
}
