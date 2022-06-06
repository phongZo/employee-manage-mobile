package com.example.source.state;

import java.util.List;

public class OrderDisplayContext {
    private State state;
    public OrderDisplayContext(){
        state = null;
    }
    public void setState(State state){
        this.state = state;
    }

    public List applyState(){
        return this.state.update();
    }
}
