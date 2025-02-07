package com.example.catlib_0612.data;

public class CatBody {
    private String posture;
    private String explain;
    public CatBody(String _posture,String _explain){
        this.explain = _explain;
        this.posture = _posture;
    }

    public String getExplain() {
        return explain;
    }

    public String getPosture() {
        return posture;
    }
}
