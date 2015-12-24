package main.java.ibatis3.my.codegen;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * iBatis3，代码生成器
 * 基于Velocity引擎
 * 
 * @author yangxt
 *
 */
public class Generator {
	
	private static Logger log = Logger.getLogger(Generator.class);
	
	private static String schemaName = "test";
	private static String userName = "root";
	private static String password = "123456";
	private static String sid = "test";
	private static String dbServerIP = "127.0.0.1";
	
	/**
	 * 运行
	 */
	public static void main(String[] args) {
		try {

//			表名
//			String[] table_names = {"AC_TRADE_ACCOUNT_SECURITY"};
//			域名
//			String domain_name = "TradeAccountSecurity";
//			包名
//			String package_name = "account";
//			//主键是否自动生成，true：自动生成，false：不需要自动生成
//			boolean needSequence = false;		
//			gen(domain_name, table_names,package_name,needSequence);
			
//			gen(域名-不需要写PO，数据表名字，包名，主键是否需要自动生成);
		//	gen("City", new String[] { "sys_city"}, "cn.club.biz.sys", false);
			//gen("Province", new String[] { "sys_province"}, "cn.club.biz.sys", false);
			//gen("District", new String[] { "sys_district"}, "cn.club.biz.sys", false);
			/*	
			 * 		可以同时为多个表同时生成代码
		    gen("ChannelRelation", new String[] { "CA_CHANNEL_RELATION" }, "capital", false);
		    gen("ChannelRelation", new String[] { "CA_CHANNEL_RELATION" }, "capital", false);
		    gen("ChannelRelation", new String[] { "CA_CHANNEL_RELATION" }, "capital", false);
		    */
			/*gen("Brand", new String[] { "brand" }, "cn.synny.common.product", false);
			gen("BigCategory", new String[] { "bigcategory" }, "cn.synny.common.product", false);
			gen("Category", new String[] { "category" }, "cn.synny.common.product", false);
			gen("Product", new String[] { "product" }, "cn.synny.common.product", false);*/

//			gen(域名-不需要写PO，数据表名字，包名，主键是否需要自动生成);
			gen("Shopping", new String[] { "shopping" }, "com.tanlijuan", true);

//			generate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据配置文件生成所有的PO对象映射文件。
	 */
	public static void generate() {
		Workbook workbook = null;
		Sheet sheet = null;
		
		int nRow = -1;
		String tableName = null;
		String className = null;
		String moduleName = null;
		String autoKeyGen = null;

		try {
			workbook = Workbook.getWorkbook(new FileInputStream("table_info.xls"));
			sheet = workbook.getSheet(0);
			
			nRow = 1;
			tableName = sheet.getCell(0, nRow).getContents();
			
			while( tableName != null && !tableName.equals("") ) {
				
				className = sheet.getCell(1, nRow).getContents();
				moduleName = sheet.getCell(2, nRow).getContents();
				autoKeyGen = sheet.getCell(3, nRow).getContents();

				gen(className, new String[]{tableName}, moduleName, "Y".equals(autoKeyGen)? true : false);
				
				tableName = sheet.getCell(0, ++nRow).getContents();
			}
			
			workbook.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 生成
	 */
	private static void gen(String domain_name, String[] table_names,String package_name, boolean needSequence) throws Exception {
		
		for(String table_name : table_names){
			
			log.debug("processing ... " + package_name + "." + domain_name + "[" + table_name + "]");
			
			VelocityContext context = new VelocityContext();
			
			context.put("dateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			context.put("packagename", package_name);
			context.put("domainname", domain_name);
			context.put("doname", domain_name);
			context.put("doname_uncapital", org.apache.commons.lang.StringUtils.uncapitalize(domain_name));
			context.put("url", "/"+package_name.replace('.', '/')+"/"+domain_name.toLowerCase());
//			context.put("author", author);
			
			context.put("tablename", table_name);
			List<String> pks = getPKs(getDBMetadata(), schemaName, table_name);
			context.put("needSequence", needSequence);
			context.put("pks", pks);
			context.put("columns", getColumns(getTableMetadata(table_name),pks));
			context.put("columns1", getColumns1(getTableMetadata(table_name),pks));
			context.put("columns2", getColumns2(getTableMetadata(table_name),pks));
			context.put("types", getTypes(getDBMetadata(), getTableMetadata(table_name)));
			context.put("types1", getTypes1(getDBMetadata(), getTableMetadata(table_name)));
			context.put("types2", getTypes2(getDBMetadata(), getTableMetadata(table_name)));
			context.put("properties", getProperties(getDBMetadata(), getTableMetadata(table_name)));
			context.put("properties2", getProperties2(getDBMetadata(), getTableMetadata(table_name)));
			context.put("comments", getComments(getDBMetadata(), getTableMetadata(table_name), table_name));
		
			VelocityEngine engine = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(Velocity.RESOURCE_LOADER, "file");
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "vm");
			engine.init(properties);
			
			String[] vms = {"pojo.vm","vo.vm","dao.vm","sqlmap.vm","service.vm","serviceImpl.vm","controller.vm","serviceTest.vm"};
			String filePathRoot = "target/";
			String filePath = "";
			
			for(String vm : vms){
				if(vm.equals("pojo.vm")){
					Template template = engine.getTemplate(vm, "UTF-8");
					
					filePath = filePathRoot  + package_name + "/po/";
					createDirectory(filePath);
					
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath + convertAs(table_name, vm, domain_name))), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();					
				}else if (vm.equals("vo.vm")){
					Template template = engine.getTemplate(vm, "UTF-8");
					
					filePath = filePathRoot  + package_name + "/vo/";
					createDirectory(filePath);
					
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath + convertAs(table_name, vm, domain_name))), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();		
				}else if(vm.equals("sqlmap.vm") || vm.equals("dao.vm")||vm.equals("basedao.vm")){
					
					filePath = filePathRoot  + package_name + "/dao/";
					createDirectory(filePath);
					
					String filename = filePath + convertAs(table_name, vm, domain_name);
					
					Template template = engine.getTemplate(vm, "UTF-8");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename)), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();
					
					
				}else if (vm.equals("service.vm")){
					Template template = engine.getTemplate(vm, "UTF-8");
					
					filePath = filePathRoot  + package_name + "/service/";
					createDirectory(filePath);
					
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath + convertAs(table_name, vm, domain_name))), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();		
					
				}else if (vm.equals("serviceImpl.vm")){
					Template template = engine.getTemplate(vm, "UTF-8");
					filePath = filePathRoot  + package_name + "/service/impl/";
					createDirectory(filePath);
					
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath + convertAs(table_name, vm, domain_name))), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();	
				}else if(vm.equals("support.vm") || vm.equals("controller.vm")){
					
					filePath = filePathRoot  + package_name + "/controller/";
					createDirectory(filePath);
					
					String filename = filePath + convertAs(table_name, vm, domain_name);
					
					Template template = engine.getTemplate(vm, "UTF-8");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename)), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();
					
					
				}else if(vm.equals("serviceTest.vm")){
					filePath = filePathRoot  + "cn/club/test/";
					createDirectory(filePath);
					
					String filename = filePath + convertAs(table_name, vm, domain_name);
					
					Template template = engine.getTemplate(vm, "UTF-8");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename)), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();
					
					
				}
			}
			
			log.debug("end ... " + package_name + "." + domain_name + "[" + table_name + "]");
		}
	}
	
	private static void createDirectory(String directory) {
		File f = new File(directory);
		f.mkdirs();
	}
	
	/** 数据库连接 **/
	private static Connection conn;
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String jdbcUrl = "jdbc:mysql://"+dbServerIP+":3306/"+sid;
			conn = DriverManager.getConnection(jdbcUrl, userName, password);
//			((MySQLConnection)conn).setRemarksReporting(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取库元数据
	 */
	private static DatabaseMetaData getDBMetadata() throws Exception {
		return conn.getMetaData();
	}
	
	/**
	 * 获取表元数据
	 */
	private static ResultSetMetaData getTableMetadata(String table_name) throws Exception {
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select * from " + table_name);
		ResultSetMetaData metaData = rs.getMetaData();
		rs.close();
		return metaData;
	}
	
	/**
	 * 获取表主键名称
	 */
	private static List<String> getPKs(DatabaseMetaData dbmetadata, String dbschema, String table_name) throws Exception {
		ResultSet rs_pk = dbmetadata.getPrimaryKeys(null, dbschema, table_name);
		List<String> pks = new ArrayList<String>();
		while (rs_pk.next()) 
			pks.add(rs_pk.getString(4).trim());
		return pks;
	}
	
	/**
	 * 获取表除主键外的其他字段的名称
	 */
	private static List<String> getColumns(ResultSetMetaData tablemetadata, List<String> pks) throws Exception {
		List<String> columns = new ArrayList<String>();
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) {
			String column_name = tablemetadata.getColumnName(j).trim();
			boolean flag = false;
			for (int i = 0; i < pks.size(); i++) 
				if(column_name.equals(pks.get(i)))
					flag = true;
			if(!flag)
				columns.add(column_name);
		}
		
		return columns;
	}

	/**
	 * 获取所有字段的名称
	 */
	private static List<String> getColumns1(ResultSetMetaData tablemetadata, List<String> pks) throws Exception {
		List<String> columns = new ArrayList<String>();
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) 
			columns.add(tablemetadata.getColumnName(j).trim());
		return columns;
	}
	
	/**
	 * 生成所有字段名的拼接，逗号间隔
	 */
	private static String getColumns2(ResultSetMetaData tablemetadata, List<String> pks) throws Exception {
		String columns = "";
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) {
			String column_name = tablemetadata.getColumnName(j).trim();
			columns += column_name + ",";
		}
		return columns.substring(0,columns.length()-1);
	}
	
	/**
	 * 获取所有字段的类型
	 */
	private static Map<String, String> getTypes(DatabaseMetaData dbmetadata, ResultSetMetaData tablemetadata) throws Exception {
		Map<String, String> types = new HashMap<String, String>();
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) {
			String column_name = tablemetadata.getColumnName(j).trim();
			String column_type = tablemetadata.getColumnTypeName(j).trim();
			int column_scale = tablemetadata.getScale(j);
			if(column_type.equalsIgnoreCase("VARCHAR2") || column_type.equalsIgnoreCase("NVARCHAR2"))
				column_type = "VARCHAR";
			if(column_type.equalsIgnoreCase("NUMBER")) {
				if (column_scale == 0) {
					column_type = "NUMERIC";
				} else {
					column_type = "NUMERIC_1";
				}
			}
			types.put(column_name, column_type);
		}
		return types;
	}
	
	/**
	 * 获取所有字段类型对应的Java属性类型
	 */
	private static Map<String, String> getTypes1(DatabaseMetaData dbmetadata, ResultSetMetaData tablemetadata) throws Exception {
		Map<String, String> types = new HashMap<String, String>();
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) {
			String column_type = tablemetadata.getColumnTypeName(j).trim();
			int column_precision = tablemetadata.getPrecision(j);
			int column_scale = tablemetadata.getScale(j);
			
			if(column_type.equalsIgnoreCase("VARCHAR2") || column_type.equalsIgnoreCase("NVARCHAR2"))
				column_type = "VARCHAR";
			if(column_type.equalsIgnoreCase("NUMBER")) {
				if (column_scale == 0) {
					column_type = "NUMERIC";
				} else {
					column_type = "NUMERIC_1";
				}
			}
			types.put(column_type, convertBy(column_type, column_precision, column_scale));
		}
		return types;
	}	
	
	private static Map<String, String> getTypes2(DatabaseMetaData dbmetadata, ResultSetMetaData tablemetadata) throws Exception {
		Map<String, String> types = new HashMap<String, String>();
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) {
			String column_name = tablemetadata.getColumnName(j).trim();
			String column_type = tablemetadata.getColumnTypeName(j).trim();
			if(column_type.equalsIgnoreCase("VARCHAR2") || column_type.equalsIgnoreCase("NVARCHAR2"))
				column_type = "VARCHAR";
			if(column_type.equalsIgnoreCase("NUMBER"))
				column_type = "NUMERIC";
			if(column_type.equalsIgnoreCase("DATE"))
				column_type = "TIMESTAMP";
			if(column_type.equalsIgnoreCase("DATETIME"))
				column_type = "TIMESTAMP";
			if(column_type.equalsIgnoreCase("INT"))
				column_type = "INTEGER";
			types.put(column_name, column_type);
		}
		return types;
	}

	/**
	 * 获取所有字段的注释
	 */
	private static Map<String, String> getComments(DatabaseMetaData dbmetadata, ResultSetMetaData tablemetadata, String table_name) throws Exception {
		Map<String, String> comments = new HashMap<String, String>();
		
		ResultSet rs_table = dbmetadata.getTables(null, null, table_name, null);
		rs_table.next();
		comments.put(table_name, rs_table.getString(5));
		
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) {
			String column_name = tablemetadata.getColumnName(j).trim();
			ResultSet rs_column = dbmetadata.getColumns(null, null, table_name, column_name);
			rs_column.next();
			
			String column_comment = "";
			if(null != rs_column.getString(12)){
				column_comment = new String(rs_column.getString(12).getBytes(),"UTF-8");
			}
			
			if (column_comment != null){
				column_comment = column_comment.replaceAll("<<自动注释>>", "");
			}
			
			comments.put(column_name, column_comment);
			rs_column.close();
		}
		
		rs_table.close();
		return comments;
	}
	
	/**
	 * 转换所有字段的为对应的Java变量名称，首字母小写
	 */
	private static Map<String, String> getProperties(DatabaseMetaData dbmetadata, ResultSetMetaData tablemetadata) throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) {
			String column_name = tablemetadata.getColumnName(j).trim();
			properties.put(column_name, convertTo(column_name));
		}
		return properties;
	}
	
	/**
	 * 转换所有字段的为对应的Java变量名称，首字母大写
	 */
	private static Map<String, String> getProperties2(DatabaseMetaData dbmetadata, ResultSetMetaData tablemetadata) throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		for (int j = 1; j <= tablemetadata.getColumnCount(); j++) {
			String column_name = tablemetadata.getColumnName(j).trim();
			properties.put(column_name, convertTo2(column_name));
		}
		return properties;
	}
	
	/**
	 * 表名转换，首字母大写
	 */
	private static String convert(String table_name) throws Exception {
		String[] parts = table_name.toLowerCase().split("_");
		String doname = "";
		for(int i=1; i<parts.length; i++)
			doname += parts[i].substring(0,1).toUpperCase() + parts[i].substring(1); 
		return doname;
	}
	
	/**
	 * 生成文件名，首字母大写
	 */
	private static String convertAs(String table_name, String vm_name, String doname) throws Exception {
		String[] parts = table_name.toLowerCase().split("_");
		if (doname == null) {
			doname = "";
			for (int i = 1; i < parts.length; i++)
				doname += parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
		}
		
		if(vm_name.equals("pojo.vm"))
			return doname + "PO.java";
		else if(vm_name.equals("vo.vm"))
			return doname + "VO.java";
		else if(vm_name.equals("dao.vm"))
			return doname + "Dao.java";
		else if(vm_name.equals("sqlmap.vm"))
			return doname + "Dao.xml";
		else if(vm_name.equals("service.vm"))
			return doname + "Service.java";
		else if(vm_name.equals("serviceImpl.vm"))
			return doname + "ServiceImpl.java";
		else if(vm_name.equals("support.vm"))
			return doname + "ControllerSupport.java";
		else if(vm_name.equals("controller.vm"))
			return doname + "Controller.java";
		else if(vm_name.equals("basedao.vm")){
			return "BaseDao.java";
		}else if(vm_name.equals("serviceTest.vm")){
			return doname + "ServiceTest.java";
		}
		else
			throw new RuntimeException("God knows");
	}
	
	/**
	 * 生成临时文件名，首字母大写
	 */
	private static String convertAsTmp(String table_name, String vm_name, String doname) throws Exception {
		String[] parts = table_name.toLowerCase().split("_");
		if (doname == null) {
			doname = "";
			for (int i = 1; i < parts.length; i++)
				doname += parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
		}
		
		if(vm_name.equals("pojo.vm"))
			return doname + ".java.tmp";
		else if(vm_name.equals("dao.vm"))
			return doname + "Dao.java.tmp";
		else if(vm_name.equals("sqlmap.vm"))
			return doname + ".xml.tmp";
		else
			throw new RuntimeException("God knows");
	}	
	
	/**
	 * 字段名转换属性名，首字母小写
	 */
	private static String convertTo(String column_name) throws Exception {
		String[] parts = column_name.toLowerCase().split("_");
		String result = parts[0];
		for(int i=1; i<parts.length; i++)
			result += parts[i].substring(0,1).toUpperCase() + parts[i].substring(1); 
		return result;
	}	
	
	
	/**
	 * 字段名转换属性名，首字母大写
	 */
	private static String convertTo2(String column_name) throws Exception {
		String[] parts = column_name.toLowerCase().split("_");
		String result = ""; 
		for(int i=0; i<parts.length; i++)
			result += parts[i].substring(0,1).toUpperCase() + parts[i].substring(1); 
		return result;
	}	
	
	/**
	 * 字段类型转换属性类型，
	 * @param column_scale 
	 * @param column_precision 
	 */
	private static String convertBy(String column_type, int column_precision, int column_scale) throws Exception {
		if(column_type.equalsIgnoreCase("NVARCHAR2"))
			return "String";
		else if(column_type.equalsIgnoreCase("VARCHAR2"))
			return "String";
		else if(column_type.equalsIgnoreCase("VARCHAR"))
			return "String";
		else if(column_type.equalsIgnoreCase("BOOLEAN"))
			return "String";
		else if(column_type.equalsIgnoreCase("TEXT"))
			return "String";
		else if(column_type.equalsIgnoreCase("INT"))
			return "int";
		else if(column_type.equalsIgnoreCase("INT UNSIGNED"))
			return "int";
		else if(column_type.equalsIgnoreCase("SMALLINT"))
			return "int";
		else if(column_type.equalsIgnoreCase("INTEGER"))
			return "int";
		else if(column_type.equalsIgnoreCase("NUMBER"))
			return "long";
		else if(column_type.equalsIgnoreCase("LONG"))
			return "long";
		else if(column_type.equalsIgnoreCase("BIGINT"))
			return "long";
		else if(column_type.equalsIgnoreCase("REAL"))
			return "long";
		else if(column_type.equalsIgnoreCase("FLOAT"))
			return "float";
		else if(column_type.equalsIgnoreCase("DOUBLE"))
			return "double";
		else if(column_type.equalsIgnoreCase("DECIMAL"))
			return "double";
		else if(column_type.equalsIgnoreCase("DATE"))
			return "Date";
		else if(column_type.equalsIgnoreCase("DATETIME"))
			return "Date";
		else if(column_type.equalsIgnoreCase("TIME"))
			return "Date";
		else if(column_type.equalsIgnoreCase("TIMESTAMP"))
			return "Date";
		else if(column_type.equalsIgnoreCase("TINYINT"))
			return "boolean";
		else if(column_type.equalsIgnoreCase("BIT"))
			return "boolean";
		else if(column_type.equalsIgnoreCase("CHAR"))
			return "String";
		else if(column_type.equalsIgnoreCase("BLOB"))
			return "Blob";
		else if(column_type.equalsIgnoreCase("CLOB"))
			return "Clob";
		else if(column_type.equalsIgnoreCase("NUMERIC"))
			return "long";
		else if(column_type.equalsIgnoreCase("NUMERIC_1"))
			return "double";
		else 
			throw new RuntimeException("God knows");
	}

	
}
