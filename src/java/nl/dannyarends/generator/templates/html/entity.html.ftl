<html>
  <head>
    <title>${entity.namespace}${entity.name}</title>
  </head>
  <body>
    <h1>${entity.name}</h1>
    File: ${file} from template ${template}<br>
    Date: ${date}<br>
    Namespace: ${entity.namespace}<br>
    FQN: ${entity.namespace}${entity.name}<br>
    <#if entity.myparent?exists>
    Parent: <a href='/${entity.myparent.namespace?replace(".","/")}${entity.myparent.name}.html'> ${entity.myparent.namespace}${entity.myparent.name}</a><br>
    </#if>
    Description: ${entity.description}<br>
    <#foreach attribute in entity.getAttributes()>
    <#if !attribute.nillable><b></#if>
    Attribute: ${entity.namespace}${entity.name}.${attribute.name} - ${attribute.cpptype} ${attribute.javatype} ${attribute.hsqltype}<br>
    <#if !attribute.nillable></b></#if>
    </#foreach>
  </body>
</html>