package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.SupplierDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO supplier VALUES(?,?,?,?)",
                    supplierDto.getSupplierId(),
                    supplierDto.getSupplierName(),
                    supplierDto.getSupplierContact(),
                    supplierDto.getSupplierAddress()
                );
    }
    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("UPDATE supplier SET supplier_name = ? , supplier_contact = ? , supplier_address = ? WHERE supplier_id = ?",
                supplierDto.getSupplierName(),
                supplierDto.getSupplierContact(),
                supplierDto.getSupplierAddress(),
                supplierDto.getSupplierId()
                );
    }
    public boolean deleteSupplier(String supplierId) throws SQLException {
        return CrudUtil.execute("DELETE FROM supplier WHERE supplier_id = ?",
                supplierId);
    }
    public SupplierDto searchSupplier(String supplierId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier",
                supplierId);
        if (resultSet.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    resultSet.getString("supplierId"),
                    resultSet.getString("supplierName"),
                    resultSet.getString("supplierContact"),
                    resultSet.getString("supplierAddress")

            );
            return supplierDto;
        }
        return null;
    }
    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier");
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        while (resultSet.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }
    public String getNextSupplierId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1");
        char tableChartacter = 'S';

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChartacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableChartacter + "001";
    }

    public ArrayList<SupplierDto> getSupplierDetailsFromContact(String phoneNum) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM supplier WHERE supplier_contact = ?", phoneNum);
        ArrayList<SupplierDto> dtos = new ArrayList<>();
        if (rst.next()) {
            dtos.add(new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)));
        }
        return dtos;
    }

}
