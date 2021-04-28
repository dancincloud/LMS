package ui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Stack;

/**
 * use to control navigation and manage views
 *
 * @author Joseph Yuanhao Li
 * @date 4/28/21 13:17
 */
public class Router {
    private static Router instance = null;

    public synchronized static Router getInstance(JFrame frame){
        if (instance == null){
            instance = new Router(frame);
        }

        return instance;
    }

    private Stack<Container> stack;
    private JFrame frame;

    private Router(JFrame frame){
        stack = new Stack<>();
        this.frame = frame;
    }

    public void go(Container container){
        stack.push(frame.getContentPane());
        frame.setContentPane(container);
        frame.revalidate();
    }

    // index: -1 return the first page;
    public void back(int index){
        Container container = stack.pop();
        if(index == -1){
            // back to the first page(Bottom in the stack)
            while (!stack.isEmpty()){
                container = stack.pop();
            }
        }else if(index > 1){
            for (int i = 0; i < index  && !stack.isEmpty(); i++){
                container = stack.pop();
            }

        }

        frame.setContentPane(container);
    }
}

