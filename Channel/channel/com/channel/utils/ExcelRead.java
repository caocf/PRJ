package com.channel.utils;

import com.channel.bean.TableCell;
import com.common.dao.impl.StringExpression;
import com.common.utils.DateTimeUtil;
import javafx.scene.control.Tab;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 25019 on 2015/11/12.
 */
public class ExcelRead {
    private XSSFRow getRow(XSSFSheet sheet, int row) {
        XSSFRow rw = null;
        rw = sheet.getRow(row);
        return rw;
    }

    private XSSFCell getCell(XSSFRow row, int col) {
        XSSFCell cell = null;
        cell = row.getCell(col);
        return cell;
    }

    private XSSFCell getCell(XSSFSheet sheet, int row, int col) {
        XSSFRow xssfRow = getRow(sheet, row);
        if (xssfRow != null) {
            return getCell(xssfRow, col);
        }
        return null;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     */
    public String getCellString(Cell cell) {
        String strCell = "";
        if (cell == null)
            return null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                strCell = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    strCell = DateTimeUtil.getTimeFmt(date);
                } else {
                    strCell = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    strCell = DateTimeUtil.getTimeFmt(date);
                } else {
                    strCell = "";
                }
                break;
            case Cell.CELL_TYPE_ERROR:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        return strCell;
    }

    private String getCellValue(XSSFSheet sheet, int row, int col) {
        XSSFRow xssfRow = getRow(sheet, row);
        if (xssfRow != null) {
            XSSFCell cell = getCell(xssfRow, col);
            if (cell != null) {
                String val = getCellString(cell);
                if (val != null)
                    return val.trim();
            }
        }
        return null;
    }

    /**
     * @param excelpath
     * @param bottom    总共有几行
     * @param right     总共有几列
     * @return
     */
    public List<List<TableCell>> readExcel(String excelpath, int bottom, int right) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                new File(excelpath)));
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<List<TableCell>> data = new ArrayList<>();

        List<ReadCell> cells = new ArrayList<>();
        //先读取所有的单元格
        for (int row = 0; row < bottom; row++) {
            List<TableCell> x = new ArrayList<>();
            data.add(x);
            for (int col = 0; col < right; col++) {
                x.add(new TableCell(null));
                String value = getCellValue(sheet, row, col);
                if (value != null) {
                    cells.add(new ReadCell(value, row, col));
                }
            }
        }

        //获取合并的单元格信息
        List<CellRangeAddress> list = new ArrayList<>();
        // 获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        // 遍历合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            // 获得合并单元格加入list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }

        //剔除被合并的单元格信息
        for (CellRangeAddress cellrange : list) {
            int tp = cellrange.getFirstRow();
            int lt = cellrange.getFirstColumn();
            int btm = cellrange.getLastRow();
            int rt = cellrange.getLastColumn();

            ReadCell cell = getCellFromList(cells, tp, lt);
            if (cell == null) {
                System.err.println(new StringExpression("行?列?虽然已合并，但未读取出数据", tp, lt).toString());
            } else {
                //设置该行该列的合并信息
                cell.setCspan(rt - lt + 1);
                cell.setRspan(btm - tp + 1);

                //删除被合并的行
                for (int k = tp; k <= btm; k++) {
                    for (int p = lt; p <= rt; p++) {
                        if (k != cell.getRow() || p != cell.getCol()) {
                            delCellFromList(cells, k, p);
                        }
                    }
                }
            }
        }


        //将cells同步到data中
        for (ReadCell cell : cells) {
            List<TableCell> rowtc = data.get(cell.getRow());
            rowtc.set(cell.getCol(), new TableCell(cell.getCellvalue(), cell.getRspan(), cell.getCspan(), cell.getRow(), cell.getCol()));
        }


        for (int row = 0; row < bottom; row++) {
            List<TableCell> x = data.get(row);
            for (int col = 0; col < right; col++) {
                TableCell cell = x.get(col);
                if (cell.getCellvalue() != null && !cell.getCellvalue().equals(""))
                    System.out.print(cell.getCellvalue().replace("\r\n", "") + "(" + cell.getRspan() + "/" + cell.getCspan() + ") ");
            }
            deleteNullCellFromList(x);
            System.out.println();
        }

        return data;
    }

    public ReadCell getCellFromList(List<ReadCell> list, int row, int col) {
        for (ReadCell cell : list) {
            if (cell.getRow() == row && cell.getCol() == col) {
                return cell;
            }
        }
        return null;
    }

    public void delCellFromList(List<ReadCell> list, int row, int col) {
        for (int i = 0; i < list.size(); i++) {
            ReadCell cell = list.get(i);
            if (cell.getRow() == row && cell.getCol() == col) {
                list.remove(i);
                return;
            }
        }
    }

    public void deleteNullCellFromList(List<TableCell> list) {
        Iterator<TableCell> it = list.iterator();
        while (it.hasNext()) {
            TableCell cell = it.next();
            if (cell == null || cell.getCellvalue() == null) {
                it.remove();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ExcelRead().readExcel("d:\\a.xlsx", 39, 24);
    }
}
