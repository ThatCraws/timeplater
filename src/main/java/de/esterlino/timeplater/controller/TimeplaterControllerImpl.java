/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.controller;

import de.esterlino.timeplater.view.TimeplaterView;
import de.esterlino.timeplater.worktimes.model.TimeplaterModel;
import de.esterlino.timeplater.worktimes.model.TimeplaterModelListener;
import de.esterlino.timeplater.worktimes.model.WorkWeek;
import de.esterlino.timeplater.worktimes.ouputter.TemplateStringWorkWeekOutputter;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkWeekSupplier;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkbookSupplier;
import de.esterlino.timeplater.worktimes.supplier.FileExcelWorkbookSupplier;
import de.esterlino.timeplater.worktimes.supplier.WorkWeekSupplier;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public class TimeplaterControllerImpl implements TimeplaterController, TimeplaterModelListener {

    private TimeplaterModel model = null;
    private final List<TimeplaterView> managedViews = new ArrayList<>();

    private WorkWeekSupplier workWeekSupplier = null;

    public TimeplaterControllerImpl(final TimeplaterModel model, final TimeplaterView... managedViews) {
        this(model);
        
        for (TimeplaterView managedView : managedViews) {
            this.managedViews.add(managedView);
            managedView.setController(this);
        }
    }

    public TimeplaterControllerImpl(final TimeplaterModel model) {
        this.model = model;

        if (model != null) {
            model.addListener(this);
        }
    }

//    public TimeplaterControllerImpl() {
//        this(null);
//    }
    public void setModel(final TimeplaterModel model) {
        if (model == this.model) {
            return;
        }

        this.model.removeListener(this);
        this.model = model;

        if (this.model == null) {
            return;
        }

        this.model.addListener(this);
    }

    // --- View ---
    @Override
    public void workbookFileChanged(File workbookFile) {
        ExcelWorkbookSupplier workbookSupplier = new FileExcelWorkbookSupplier(workbookFile);
        workWeekSupplier = new ExcelWorkWeekSupplier(workbookSupplier);

        if (model == null) {
            return;
        }

        List<WorkWeek> loadedWorkWeeks = Arrays.asList(workWeekSupplier.getAllWorkWeeks());
        WorkWeek selectedWorkWeek = workWeekSupplier.supplyWorkWeek(workWeekSupplier.getFirstCalendarWeek());

        if (loadedWorkWeeks.equals(model.getLoadedWorkWeeks())) {
            return;
        }

        model.setLoadedWorkWeeks(loadedWorkWeeks);
        model.setSelectedWorkWeek(selectedWorkWeek);
    }

    @Override
    public void workweekChanged(WorkWeek workWeek) {
        if (model == null) {
            return;
        }

        model.setSelectedWorkWeek(workWeek);
    }

    @Override
    public void outputTriggered(final WorkWeek toOutput) {
        TemplateStringWorkWeekOutputter outputter = new TemplateStringWorkWeekOutputter();
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] {
                    DataFlavor.stringFlavor,};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor == DataFlavor.getTextPlainUnicodeFlavor();
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                if (toOutput != null) {
                    return outputter.createOutput(toOutput);
                } else {
                    return "";
                }
            }
        }, (Clipboard clipboard, Transferable contents) -> {
        });
    }

    // --- Model ---
    @Override
    public void loadedWorkWeeksChanged(List<WorkWeek> workWeeks) {
        for (TimeplaterView view : managedViews) {
            view.updateLoadedWorkWeeks(workWeeks);
        }

    }

    @Override
    public void selectedWorkWeekChanged(WorkWeek workWeek) {
        for (TimeplaterView view : managedViews) {
            view.updateSelectedWorkWeek(workWeek);
        }
    }
}
