package src.javaproject.classes;

import java.util.ArrayList;

/**
 * This class is used for storing assigned projects
 */
public class ProjectAssignment extends Project {
    private ArrayList<Worker> workers;



    /**
     * takes an Arraylist of Workers for the Project
     * @param w
     */
    public void workerSetter(ArrayList<Worker> w) {
        this.workers = w;
    }

    /**
     * used to get the workers assigned to the project
     * @return an Arraylist of Workers working on the project
     */
    public ArrayList<Worker> workerGetter() {
        return workers;
    }
}
