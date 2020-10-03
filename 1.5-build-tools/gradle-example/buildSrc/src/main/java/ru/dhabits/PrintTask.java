package ru.dhabits;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public class PrintTask extends DefaultTask {
    private String to;
    
    @TaskAction
    public void printProjectName() {
        System.out.println("Project module:" + to);
    }

    @Input
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
