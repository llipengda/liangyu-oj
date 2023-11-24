package com.zybzyb.liangyuoj.common.enumeration;

public enum EvaluateStatus {
    AC,
    WA,
    TLE,
    MLE,
    RE,
    CE;

    @Override
    public String toString() {
        return this.name();
    }
}
