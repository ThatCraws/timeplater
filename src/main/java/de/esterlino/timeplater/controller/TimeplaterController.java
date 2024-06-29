/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.controller;

import de.esterlino.timeplater.view.TimeplaterView;
import de.esterlino.timeplater.view.TimeplaterViewListener;
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
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public class TimeplaterController implements TimeplaterViewListener, TimeplaterModelListener {

    private TimeplaterModel model = null;
    private TimeplaterView view = null;

    private WorkWeekSupplier workWeekSupplier = null;

    public TimeplaterController(final TimeplaterModel model, final TimeplaterView view) {
        this.model = model;
        this.view = view;
        
        model.addListener(this);
        view.addListener(this);
    }
    
    public TimeplaterController(final TimeplaterView view) {
        this(null, view);
    }
    
    public TimeplaterController() {
        this(null);
    }
    
    public void setModel(final TimeplaterModel model) {
        if (model != this.model) {
            this.model = model;
        }
    }

    // --- View ---
    @Override
    public void workbookFileChanged(File workbookFile) {
        ExcelWorkbookSupplier workbookSupplier = new FileExcelWorkbookSupplier(workbookFile);
        workWeekSupplier = new ExcelWorkWeekSupplier(workbookSupplier);
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
        view.updateLoadedWorkWeeks(workWeeks);

    }

    @Override
    public void selectedWorkWeekChanged(WorkWeek workWeek) {
        view.updateSelectedWorkWeek(workWeek);
    }

}
