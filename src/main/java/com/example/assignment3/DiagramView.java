package com.example.assignment3;

import javafx.beans.Observable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

public class DiagramView extends StackPane implements ModelListener,IModelListener {

    GraphicsContext gc;
    Canvas myCanvas;

    SMModel model;

    double viewWidth,viewHeight;

    double dx,dy;

    InteractionModel imodel;







    public DiagramView( double vWidth, double vHeight){
        viewWidth = vWidth;
        viewHeight = vHeight;
        VBox outer = new VBox(10);
        myCanvas = new Canvas(vWidth,vHeight);
        dx = 0;
        dy = 0;

        outer.getChildren().add(myCanvas);
        outer.setStyle("-fx-background-color: transparent");
        gc = myCanvas.getGraphicsContext2D();
        outer.setStyle("-fx-background-color: #18c7e7");

        this.setMaxHeight(1600);
        this.setMinHeight(800);
        this.setMaxWidth(1600);
        this.setMinWidth(800);
        this.widthProperty().addListener(this::setCanvasWidth);

        this.heightProperty().addListener(this::setCanvasHeight);



        myCanvas.setStyle("-fx-border-color: red");

        this.getChildren().add(outer);
    }

    private void setCanvasWidth(Observable observable, Number oldVal, Number newVal) {

        viewWidth = newVal.doubleValue();
        double change = newVal.doubleValue() - oldVal.doubleValue();


        myCanvas.setWidth(viewWidth);
        imodel.setViewWidth(viewWidth);

        if(oldVal.doubleValue() != 0){
            imodel.Viewport.Width += change*1.5;
            dx += change/3.50;




            imodel.Viewport.x = 0;
            imodel.Viewport.y = 0;
        }

        draw();
    }

    private void setCanvasHeight(Observable observable, Number oldVal, Number newVal) {

        viewHeight = newVal.doubleValue();
        myCanvas.setHeight(viewHeight);
        imodel.setViewHeight(viewHeight);

        double change = newVal.doubleValue() - oldVal.doubleValue();

        if(oldVal.doubleValue() != 0){
            imodel.Viewport.Height += change*1.5;
            dy += change/3.50;

            imodel.Viewport.x = 0;
            imodel.Viewport.y = 0;
        }

        draw();
    }

    private void draw() {

        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());

        DrawViewPortNodes();


        model.getNodes().forEach(n -> {




            gc.setFill(Color.YELLOW);
            gc.fillRect(n.x + imodel.regionX,n.y + imodel.regionY,100,50);
            gc.setFont(new Font(Font.getDefault().getStyle(), Font.getDefault().getSize()));



            gc.setFill(Color.BLACK);
            gc.setFont(new Font(Font.getDefault().getStyle(), Font.getDefault().getSize()));
            gc.fillText(n.text,n.x+ 25 + imodel.regionX ,n.y+30 + imodel.regionY);
            if(imodel.selectedNode == n){
                gc.setStroke(Color.RED);
                gc.strokeRect(n.x+ imodel.regionX,n.y + imodel.regionY,n.Width,n.Height);
            }
            else{
                gc.setStroke(Color.BLACK);
                gc.strokeRect(n.x+ imodel.regionX,n.y+ imodel.regionY,n.Width,n.Height);

            }



        });



        model.getLinks().forEach( l -> { //Draw transition Link



            gc.setStroke(Color.BLACK);

            if(l.self){


                gc.setStroke(Color.BLACK);
                gc.save();
                drawArrow(l.ArrowSX + imodel.regionX,l.ArrowSY + imodel.regionY ,l.ArrowEX + imodel.regionX,l.ArrowEY + imodel.regionY);
                drawArrow(l.ArrowEX + imodel.regionX ,l.ArrowEY + imodel.regionY ,l.ArrowSX + imodel.regionX ,l.ArrowSY + imodel.regionY);

                gc.restore();
                gc.strokeOval(l.circleX + imodel.regionX,l.circleY + imodel.regionY,l.circleDiametre,l.circleDiametre);

            }
            if(!l.self){
            gc.save();
            drawArrow(l.linkStartX + imodel.regionX,l.linkStartY + imodel.regionY,l.linkStartX1 + imodel.regionX,l.linkStartY1 + imodel.regionY);
            drawArrow(l.linkEndX + imodel.regionX,l.linkEndY + imodel.regionY,l.linkEndX1 + imodel.regionX,l.linkEndY1 + imodel.regionY);
            gc.restore();}



            gc.setFill(Color.BEIGE);
            gc.fillRect(l.TextBox.x + imodel.regionX, l.TextBox.y + imodel.regionY, l.TextBox.Width,l.TextBox.Height);

            gc.setFill(Color.BLACK);
            gc.setFont(new Font(Font.getDefault().getStyle(), Font.getDefault().getSize()));
            gc.fillText("-> Event",l.TextBox.x+ 5 + imodel.regionX ,l.TextBox.y+15 + imodel.regionY);
            gc.fillText(l.TextBox.Info.get("Event"),l.TextBox.x+ 20 + imodel.regionX,l.TextBox.y+30 + imodel.regionY);

            gc.fillText("-> Context",l.TextBox.x+ 5 + imodel.regionX,l.TextBox.y+50 + imodel.regionY);
            gc.fillText(l.TextBox.Info.get("Context"),l.TextBox.x+ 20 + imodel.regionX,l.TextBox.y+ 65 + imodel.regionY);

            gc.fillText("-> Side-effect",l.TextBox.x+ 5 + imodel.regionX,l.TextBox.y+85 + imodel.regionY);
            gc.fillText(l.TextBox.Info.get("Side-effect"),l.TextBox.x+ 5+ imodel.regionX,l.TextBox.y+105 + imodel.regionY);


            if(imodel.selectedNode == l.TextBox){
                gc.setStroke(Color.RED);
                gc.strokeRect(l.TextBox.x + imodel.regionX,l.TextBox.y + imodel.regionY,l.TextBox.Width,l.TextBox.Height);

            }
            else{
                gc.setStroke(Color.BLACK);
                gc.strokeRect(l.TextBox.x + imodel.regionX,l.TextBox.y + imodel.regionY,l.TextBox.Width,l.TextBox.Height);}


        });


        Color grey = new Color(0.50,0.50,0.50,0.50);
        gc.setFill(grey);

        gc.fillRect(imodel.Viewport.x ,imodel.Viewport.y,imodel.Viewport.Width, imodel.Viewport.Height);

    }




    private void DrawViewPortNodes(){


        Color yellow = new Color(1.0,1.0,0.0,0.4);
        model.getNodes().forEach(n -> {
            gc.setFill(yellow);

            gc.fillRect((n.x/2) + dx,(n.y/2) + dy ,100.0/2,50.0/2);






            gc.setFill(Color.BLACK);
            gc.setFont(new Font(Font.getDefault().getStyle(), Font.getDefault().getSize()/2));
            gc.fillText(n.text,((n.x+ 25)/(2) + dx) ,((n.y+30)/(2) + dy) );
            if(imodel.selectedNode == n){
                gc.setStroke(Color.RED);
                gc.strokeRect((n.x/2)+dx,(n.y/2)+dy,n.Width/2,n.Height/2);
            }
            else{
                gc.setStroke(Color.BLACK);
                gc.strokeRect((n.x/2)+dx,(n.y/2)+dy,n.Width/2,n.Height/2);

            }


        });

        Color linkColor = new Color(0.96,0.96,0.80,0.4);

        model.getLinks().forEach( l -> { //Draw transition Link



            gc.setStroke(Color.BLACK);
            if(l.self){


                gc.setStroke(Color.BLACK);
                gc.save();
                drawArrow(l.ArrowSX/2 + dx ,l.ArrowSY/2 +dy ,l.ArrowEX/2 + dx,l.ArrowEY/2 + dy);
                drawArrow(l.ArrowEX/2 + dx ,l.ArrowEY/2 +dy ,l.ArrowSX/2 +dx ,l.ArrowSY/2 +dy);

                gc.restore();
                gc.strokeOval(l.circleX/2 + dx,l.circleY/2 + dy,l.circleDiametre/2,l.circleDiametre/2);

            }

            if(!l.self){
                gc.save();
                drawArrow(l.linkStartX/2 +dx ,l.linkStartY/2 + dy ,l.linkStartX1/2 +dx ,l.linkStartY1/2 + dy);
                drawArrow(l.linkEndX/2 + dx,l.linkEndY/2 +dy,l.linkEndX1/2 +dx ,l.linkEndY1/2 + dy);
                gc.restore();}

            gc.setFill(linkColor);
            gc.fillRect(l.TextBox.x/2 + dx, l.TextBox.y/2 + dy, l.TextBox.Width/2 ,l.TextBox.Height/2);

            gc.setFill(Color.BLACK);
            gc.fillText("-> Event",(l.TextBox.x+ 5)/2 +dx,(l.TextBox.y+15)/2 +dy);
            gc.fillText(l.TextBox.Info.get("Event"),(l.TextBox.x+ 20)/2 +dx,(l.TextBox.y+30)/2 +dy);

            gc.fillText("-> Context",(l.TextBox.x+ 5)/2 +dx,(l.TextBox.y+50)/2 +dy);
            gc.fillText(l.TextBox.Info.get("Context"),(l.TextBox.x+ 20)/2 +dx,(l.TextBox.y+65)/2 +dy);

            gc.fillText("-> Side-effect",(l.TextBox.x+ 5)/2 +dx,(l.TextBox.y+85)/2 +dy);
            gc.fillText(l.TextBox.Info.get("Side-effect"),(l.TextBox.x+ 5)/2 + dx,(l.TextBox.y+105)/2 + dy);


            if(imodel.selectedNode == l.TextBox){
                gc.setStroke(Color.RED);
                gc.strokeRect(l.TextBox.x/2 +dx,l.TextBox.y/2 +dy,l.TextBox.Width/2,l.TextBox.Height/2);

            }
            else{
                gc.setStroke(Color.BLACK);
                gc.strokeRect(l.TextBox.x/2 + dx,l.TextBox.y/2 +dy,l.TextBox.Width/2,l.TextBox.Height/2);}


        });



    }
    private void drawline(){
        if(model.tempLink != null){
            gc.setStroke(Color.BLACK);
           line l = model.tempLink;
            gc.strokeLine(l.x1,l.y1,l.x2,l.y2);
   }

    }

    void drawArrow(double x, double y, double x1, double y1) {

        gc.setFill(Color.BLACK);

        double dx = x1 - x, dy = y1 - y;

        double angle = Math.atan2(dy, dx);

        int length = (int) Math.sqrt(dx * dx + dy * dy);


        Transform transform = Transform.translate(x, y);

        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));

        gc.setTransform(new Affine(transform));


        gc.strokeLine(0, 0, length, 0);

        gc.fillPolygon(new double[]{length, length - 8, length - 8, length}, new double[]{0, -8, 8, 0},
                4);
    }



    public void setModel(SMModel m){
        model = m;



    }

    public void setIModel(InteractionModel im){
        imodel = im;
        imodel.setViewWidth(viewWidth);
        imodel.setViewHeight(viewHeight);
        draw();


    }

    public void setController( AppController controller){
        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseReleased(controller::handleReleased);
        myCanvas.setOnMouseDragged(controller::handleDragged);


    }

    @Override
    public void modelChanged() {
     draw();
     drawline();
    }

    @Override
    public void iModelChanged() {
     if(imodel.getSelectedTool() == InteractionModel.Tool.POINTER){
         myCanvas.setCursor(Cursor.DEFAULT);
     }
     else if(imodel.getSelectedTool() == InteractionModel.Tool.LINK){
         myCanvas.setCursor(Cursor.CROSSHAIR);

        }

     else if(imodel.getSelectedTool() == InteractionModel.Tool.MOVE){
        myCanvas.setCursor(Cursor.HAND);
     }
     draw();
    }
}
