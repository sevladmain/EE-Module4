package com.goit.ee.module1;

import java.util.List;

/**
 * Created by SeVlad on 14.08.2016.
 */
public class ListRunner implements Runner{
    List<Integer> list;
    Integer volume;

    public ListRunner() {
        list = null;
        volume = 0;
    }

    public ListRunner(List<Integer> list, Integer volume) {

        this.list = list;
        this.volume = volume;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
    private void runPopulate(){
        for (int i = 0; i < volume; i++) {
            list.add(i);
        }

    }
    private void runAdd(){
        list.add((int) (Math.random() * list.size()), volume);

    }
    private void runGet(){
        list.get((int) (Math.random() * list.size()));

    }
    private void runRemove(){
        list.remove((int) (Math.random() * list.size()));

    }
    private void runContains(){
        list.contains((int) (Math.random() * list.size()));

    }
    private void runIteratorAdd(){
        list.listIterator().add((int) (Math.random() * list.size()));

    }
    private void runIteratorContains(){
        list.listIterator().remove();
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
            case "get" : runGet();
                break;
            case "iterator.add" : runIteratorAdd();
                break;
            case "iterator.contains" : runIteratorContains();
                break;
        }

    }
}
