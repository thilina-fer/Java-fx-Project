package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.PurchaseReportDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseReportModel {
    public boolean saveReport(PurchaseReportDto purchaseReportDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Purchase_Report VALUES(?,?,?)",
                purchaseReportDto.getReportId(),
                purchaseReportDto.getOrderId(),
                purchaseReportDto.getDescription()
        );
    }

    public boolean updateReport(PurchaseReportDto purchaseReportDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Purchase_Report SET order_id = ?, description = ? WHERE report_id = ?",
                purchaseReportDto.getOrderId(),
                purchaseReportDto.getDescription(),
                purchaseReportDto.getReportId()
        );
    }

    public boolean deleteReport(String reportId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Purchase_Report WHERE report_id = ?",
                reportId);
    }

    public ArrayList<PurchaseReportDto> getAllReports() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Purchase_Report");
        ArrayList<PurchaseReportDto> purchaseReportDtoArrayList = new ArrayList<>();

        while (resultSet.next()) {
            PurchaseReportDto dto = new PurchaseReportDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
            purchaseReportDtoArrayList.add(dto);
        }
        return purchaseReportDtoArrayList;
    }

    public String getNextReportId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT item_id FROM Item ORDER BY item_id DESC LIMIT 1");
        char tableChartacter = 'R';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChartacter + "%03d", nextIdNumber);

            return nextIdString;
        }
        return tableChartacter + "001";
    }

    public ArrayList<PurchaseReportDto> searchReport(String searchText) throws SQLException {
        ArrayList<PurchaseReportDto> dtos = new ArrayList<>();
        String sql = "SELECT * FROM purchase_report WHERE report_id LIKE ? OR order_id LIKE ? OR report_description LIKE ? ";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern, pattern, pattern);

        while (resultSet.next()) {
            PurchaseReportDto dto = new PurchaseReportDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
           dtos.add(dto);
        }
        return dtos;
    }
}