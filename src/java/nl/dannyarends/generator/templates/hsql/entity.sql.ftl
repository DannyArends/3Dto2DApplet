/* \file ${file}
 * \copyright Danny Arends 2011-${year?c}, all rights reserved
 * \date ${date}
 * \brief Generated file from template ${template}
 */
DROP TABLE ${entity.name} IF EXISTS CASCADE;
CREATE CACHED TABLE ${entity.name} (
  id INTEGER
  ,name TEXT
  ,description TEXT
<#list entity.getAttributes() as attribute>
  ,${attribute.name} ${attribute.hsqltype}<#if !attribute.nillable> NOT NULL</#if>
</#list>
)