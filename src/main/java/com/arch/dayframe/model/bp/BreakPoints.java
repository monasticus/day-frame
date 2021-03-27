package com.arch.dayframe.model.bp;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BreakPoints {

    private final List<BreakPoint> breakPoints;
    private ListIterator<BreakPoint> bpIterator;

    public BreakPoints(String path) throws BreakPointException, IOException {
        this.breakPoints = BreakPointFactory.fromPath(path).stream().filter(BreakPoint::isNotPast).collect(Collectors.toList());
        this.bpIterator = this.breakPoints.listIterator();
    }

    public int getSize(){
        return breakPoints.size();
    }
}
