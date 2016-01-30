package ro.gebs.demo.view.beans;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.jbo.RowSetIterator;

import org.apache.myfaces.trinidad.change.AddChildComponentChange;
import org.apache.myfaces.trinidad.change.ChangeManager;
import org.apache.myfaces.trinidad.change.MoveChildComponentChange;
import org.apache.myfaces.trinidad.change.RemoveChildComponentChange;
import org.apache.myfaces.trinidad.context.RequestContext;

public class Business {


  //  public DnDAction handleDnDEventBetaVersion(DropEvent dropEvent) {
  //    FacesContext fctx = FacesContext.getCurrentInstance();
  //    Transferable transferable = dropEvent.getTransferable();
  //    UIComponent dragComponent =
  //      transferable.getData(DataFlavor.UICOMPONENT_FLAVOR);
  //    UIComponent dropTarget = dropEvent.getDropComponent();
  //    UIComponent dropTargetParent = dropTarget.getParent();
  //    if (dragComponent != null) {
  //      UIComponent dragParent = dragComponent.getParent();
  //
  //      AddChildComponentChange add =
  //        new AddChildComponentChange(dropTarget.getChildren().get(0).getChildren().get(0).getId(),
  //                                    dragComponent);
  //      add.changeComponent(dropTarget.getChildren().get(0));
  //
  //      RemoveChildComponentChange remove =
  //        new RemoveChildComponentChange(dragComponent.getId());
  //      remove.changeComponent(dragParent);
  //
  //      ChangeManager cm = null;
  //      cm = RequestContext.getCurrentInstance().getChangeManager();
  //
  //      cm.addComponentChange(FacesContext.getCurrentInstance(),
  //                            dropTarget.getChildren().get(0), add);
  //      cm.addComponentChange(FacesContext.getCurrentInstance(), dragParent,
  //                            remove);
  //      AdfFacesContext.getCurrentInstance().addPartialTarget(dropTarget);
  //      AdfFacesContext.getCurrentInstance().addPartialTarget(dragParent);
  //
  //      return DnDAction.MOVE;
  //    }
  //    return DnDAction.NONE;
  //  }


  public DnDAction handleDnDEvent(DropEvent dropEvent) {
    Transferable transferable = dropEvent.getTransferable();
    UIComponent dragComponent = null;
    dragComponent = transferable.getData(DataFlavor.UICOMPONENT_FLAVOR);
    UIComponent dropTarget = dropEvent.getDropComponent();
    if (dragComponent != null) {
      UIComponent dragComponentParent = dragComponent.getParent();
      if (dropTarget.equals(dragComponentParent.getParent())) {
        return DnDAction.NONE;
      } else {
        MoveChildComponentChange move = null;
        if (dropTarget.getChildren().get(0).getChildCount() > 0) {
          move =
              new MoveChildComponentChange(dragComponent, dropTarget.getChildren().get(0),
                                           dropTarget.getChildren().get(0).getChildren().get(0));
        } else {
          move =
              new MoveChildComponentChange(dragComponent, dropTarget.getChildren().get(0));
        }
        ChangeManager cm =
          RequestContext.getCurrentInstance().getChangeManager();
        UIComponent commonComponentParent =
          move.add(FacesContext.getCurrentInstance(), cm);
        move.changeComponent(commonComponentParent);
        AdfFacesContext.getCurrentInstance().addPartialTarget(commonComponentParent);

        return DnDAction.MOVE;
      }
    }
    return DnDAction.NONE;
  }

}
