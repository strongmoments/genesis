package com.genesis.genesisapi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ReportsRepo {

	 @Autowired
	    JdbcTemplate jdbcTemplate;
	 
	 
	 public List<Map<String, Object>> getwsoReports(){
		 
		 List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
		 
		 String sqlString = "select   w.wso_no as wso, w.invoice_rate as invoicerate, w.first_bill_date as startdate, w.total_wso_weight as totalWeight, count(l.lot_id) as totalLot , sum(l.unit_gross_weight_in_kg) as totalLotWeight from wso_info  as w  left outer  JOIN  lot_info as l on l.wso_info_wso_id = w.wso_id group by w.wso_no";
		 jdbcTemplate.query(sqlString, new RowCallbackHandler() {
		        public void processRow(ResultSet rs) throws SQLException {
		        	 Map<String, Object> columns = new HashMap<String, Object>();
		            String columnName = rs.getString("wso");
		            String startdate = rs.getString("startdate");
		            startdate =  (startdate == null || startdate =="null") ? "" :startdate;
		            
		            String totalWeight = rs.getString("totalWeight");
		            totalWeight =  (totalWeight == null || totalWeight =="null") ? "" :totalWeight;
		            
		            String totalLot = rs.getString("totalLot");
		            String totalLotWeight = rs.getString("totalLotWeight");
		            totalLotWeight =  (totalLotWeight == null || totalLotWeight =="null") ? "0" :totalLotWeight;
		            String invoicerate = rs.getString("invoicerate"); 
		            columns.put("wso", columnName);
		            columns.put("startdate", startdate);
		            columns.put("totalWeight", totalWeight);
		            columns.put("totalLot", totalLot);
		            columns.put("totalLotWeight", totalLotWeight);
		            columns.put("invoicerate", invoicerate);
		            datalist.add(columns);
		        }
		    });
		 return datalist;
	 }
	 
	 
	 
	 public List<Map<String, Object>> getBillsreports(){
		 List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
		 String sqlString = "select w.wso_no as wsoNo , b.from_date as startDate , b.to_date  as endDate,  b.net_amount as totalAmount, b.billing_weight as wight, b.billing_rate as rate from billing as b left join wso_info w on w.wso_id=b.wso_info_wso_id";
		 jdbcTemplate.query(sqlString, new RowCallbackHandler() {
		        public void processRow(ResultSet rs) throws SQLException {
		        	 Map<String, Object> columns = new HashMap<String, Object>();
		            String columnName = rs.getString("wsoNo");
		            String startdate = rs.getString("startDate");
		            startdate =  (startdate == null || startdate =="null") ? "" :startdate;
		            String endDate = rs.getString("endDate");
		            endDate =  (endDate == null || endDate =="null") ? "" :endDate;
		            String totalAmount = rs.getString("totalAmount");
		            totalAmount =  (totalAmount == null || totalAmount =="null") ? "" :totalAmount;
		            String wight = rs.getString("wight");
		            wight =  (wight == null || wight =="null") ? "0" :wight;
		            String invoicerate = rs.getString("rate"); 
		            columns.put("wso", columnName);
		            columns.put("startdate", startdate);
		            columns.put("endDate", endDate);
		            columns.put("totalAmount", totalAmount);
		            columns.put("wight", wight);
		            columns.put("invoicerate", invoicerate);
		            datalist.add(columns);
		        }
		    });
		 return datalist;
	 }
	 
	 
	 public List<Map<String, Object>> loadBillByClientAndBillType(List<Long> storateTypeId, List<Long> tallySheetList){
		
		List<Object> param = new ArrayList<Object>();
		param.add(storateTypeId);
		param.add(tallySheetList);
		List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
		 String sqlString = "SELECT  C.client_title, B.from_date, B.to_date, B.invoice_no,  sum(B.net_amount)  FROM   billing AS B  left JOIN  tallysheet as T ON  T.tallysheet_id = B.tallysheet_tallysheet_id  left JOIN client_info AS C ON T.client_info_client_info_id = C.client_info_id  WHERE B.storage_class_storage_type_id IN ?  AND B.tallysheet_tallysheet_id IN ?  GROUP BY B.invoice_no  ";
		 
		 //jdbcTemplate.query
		 
		 jdbcTemplate.query(sqlString, new RowCallbackHandler() {
		        public void processRow(ResultSet rs) throws SQLException {
		        	 Map<String, Object> columns = new HashMap<String, Object>();
		            String clientName = rs.getString("client_title");
		            columns.put("clientName", clientName);
		            datalist.add(columns);
		        }
		    } ,storateTypeId, tallySheetList );
		 return datalist;
	 }
	 
}

