/* \file ${file}
 * \copyright Danny Arends 2011-${year?c}, all rights reserved
 * \date ${date}
 * \brief Generated file from template ${template}
 */
<#if entity.javanamespace != " ">
package ${entity.javanamespace};
</#if>
import nl.dannyarends.db.Database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
<#if entity.hasCollection()>
import nl.dannyarends.generator.model.Attribute;
import java.util.ArrayList;
</#if>

class ${entity.name}{
  int 	  id;
  String  name = "${entity.name}";
  String  description = "${entity.description}";
  <#foreach attribute in entity.getAttributes()>
  ${attribute.javatype} ${attribute.name};
  </#foreach>
  Database database;
	 
  ${entity.name}(Database db){
    database=db;
  }
	 
  public boolean update(){ 	
    Connection c = database.getDatabase();
    try{
      Statement stmt = c.createStatement();
      String sql = "INSERT INTO ${entity.name}";
      sql += "(id, name, description<#foreach attribute in entity.getAttributes()>, ${attribute.name}</#foreach>)";
      sql += "VALUES('"+id+"','"+name+"', '"+description+"'<#foreach attribute in entity.getAttributes()>, '"+${attribute.name}+"'</#foreach>);";	 
      return stmt.execute(sql);
    }catch(SQLException e){
      e.printStackTrace();
      return false;
    }
  }
	 
  public int getId(){
    return id;
  }
	 
  public boolean setName(String n){
    name=n;
    return update();
  }
	 
  public String getName(){
    return name;
  }
	 
  <#foreach attribute in entity.getAttributes()>
  public ${attribute.javatype} get${attribute.name}(){
    return this.${attribute.name};
  }
  
  public boolean set${attribute.name}(${attribute.javatype} x){
    this.${attribute.name} = x;
	return update();
  } 

  </#foreach>
}
