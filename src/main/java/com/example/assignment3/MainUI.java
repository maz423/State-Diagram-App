package com.example.assignment3;

import javafx.scene.layout.BorderPane;

public class MainUI extends BorderPane implements IModelListener{
    InteractionModel imodel;
    NodePropertiesView right;
    LinkPropertiesView rright;
    public MainUI(){
        ToolPalette left = new ToolPalette();
        DiagramView centre = new DiagramView(800,800);
        right = new NodePropertiesView();
        rright = new LinkPropertiesView();
        this.setLeft(left);
        this.setCenter(centre);
        this.setRight(right);


        SMModel model = new SMModel();
        AppController controller = new AppController();
        imodel = new InteractionModel();
        model.setIModel(imodel);

        controller.setModel(model);
        centre.setModel(model);
        controller.setIModel(imodel);
        centre.setIModel(imodel);
        model.addSubscriber(centre);
        imodel.addSubscriber(left);
        imodel.addSubscriber(centre);
        left.setiModel(imodel);
        left.setController(controller);
        right.setController(controller);
        rright.setController(controller);

        imodel.addSubscriber(this);
        imodel.setWorldWidth(1600);
        imodel.addSubscriber(model);


        centre.setController(controller);
        this.setOnKeyPressed(controller::handleKeyPress);


    }


    @Override
    public void iModelChanged() { //change right palette depending on node.
        SMRect LinkProp = new SMRect(100,100,100,100);
        SMStateNode Node = new SMStateNode(100,100,100,100);

        if(imodel.selectedNode != null){
       if(imodel.getSelectedNode().getClass() == LinkProp.getClass()){
           this.setRight(rright);
       }
       else if(imodel.getSelectedNode().getClass() == Node.getClass()){
           this.setRight(right);
       }
    }
    }
}
