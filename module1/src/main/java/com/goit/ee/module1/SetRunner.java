package com.goit.ee.module1;

import java.util.Set;

/**
 * Created by SeVlad on 14.08.2016.
 */
public class SetRunner implements Runner {
    Set<Integer> set;
    Integer volume;

    public SetRunner() {
        set = null;
        volume = 0;
    }

    public SetRunner(Set<Integer> set, Integer volume) {
        this.set = set;
        this.volume = volume;
    }

    public Set<Integer> getSet() {
        return set;
    }

    public void setSet(Set<Integer> set) {
        this.set = set;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
    private void runPopulate(){
        for (Integer i = 0; i < volume; i++) {
            set.add(i);
        }
    }
    private void runAdd(){
        set.add((int) (Math.random() * volume));

    }
    private void runContains(){
        set.contains((int) (Math.random() * set.size()));

    }
    private void runRemove(){
        set.remove((int) (Math.random() * set.size()));

    }
    @Override
    public void run(String method) {
        switch(method.toLowerCase()){
            case "populate" : runPopulate();
                break;
            case "add" : runAdd();
                break;
            case "contains" : runContains();
                break;
            case "remove" : runRemove();
                break;
        }
    }
}
