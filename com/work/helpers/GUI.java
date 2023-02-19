package com.work.helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GUI extends JFrame {

    ArrayList<RenderObject> RenderObjects = new ArrayList<>();
    public class RenderObject
    {
        public int sizeX;
        public int sizeY;
        public int posX;
        public int posY;
        public boolean visible;

        public BiConsumer<RenderObject, Graphics> renderFunction;

        public RenderObject(int a, int b, int c, int d, BiConsumer<RenderObject, Graphics> e)
        {
            sizeX = a;
            sizeY = b;
            posX = c;
            posY = d;

            visible = true;

            renderFunction = e;

            RenderObjects.add(this);
        }
    }

    ArrayList<ClickableObject> ClickableObjects = new ArrayList<>();
    public class ClickableObject {

        public BiPredicate<MouseEvent, ClickableObject> hitboxFunction;
        public Consumer<ClickableObject> onclickFunction;

        int[] bounds;
        public boolean active;

        public ClickableObject(int[] a, BiPredicate<MouseEvent, ClickableObject> b, Consumer<ClickableObject> c)
        {
            bounds = a;
            hitboxFunction = b;
            onclickFunction = c;

            active = true;

            ClickableObjects.add(this);
        }
    }

    public GUI()
    {
        setVisible(true);
        setSize(600, 400);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (ClickableObject cClickableObject : ClickableObjects)
                {
                    //RenderObject cRenderObject = RenderObjects.get(i);
                    if (cClickableObject.hitboxFunction.test(e, cClickableObject))
                    {
                        cClickableObject.onclickFunction.accept(cClickableObject);
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                //System.out.println(e.getX() + " / " + e.getY());
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
        });
    }

    public void paint(Graphics g)
    {
        for (RenderObject cRenderObject : RenderObjects)
        {
            //RenderObject cRenderObject = RenderObjects.get(i);
            cRenderObject.renderFunction.accept(cRenderObject, g);
        }

    }

    public static void main(String[] args)
    {
        GUI gui = new GUI();

        BiConsumer<RenderObject, Graphics> renderRect = (cRenderObject, graphics) -> graphics.fillRect(cRenderObject.posX, cRenderObject.posY, cRenderObject.sizeX, cRenderObject.sizeY);
        BiPredicate<MouseEvent, ClickableObject> hitboxRect = (mouse, cClickableObject) -> {
            Point mousePos = mouse.getPoint();
            double mouseX = mousePos.getX();
            double mouseY = mousePos.getY();
            return (
                    (mouseX > cClickableObject.bounds[2]) && (mouseX < cClickableObject.bounds[2] + cClickableObject.bounds[0]) &&
                    (mouseY > cClickableObject.bounds[3]) && (mouseX < cClickableObject.bounds[3] + cClickableObject.bounds[0])
            );
        };
        Consumer<ClickableObject> onClick = (cClickableObject) -> System.out.println("dogfish");


        gui.new RenderObject(100, 100, 50, 50, renderRect);
        gui.new ClickableObject(new int[]{100, 100, 50, 50}, hitboxRect, onClick);
    }
}